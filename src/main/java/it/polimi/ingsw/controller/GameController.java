package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class game controller and subclasses handles the evolution of the game based on
 * the messages from client
 * @author Alice Cariboni
 */
public abstract class GameController {
    protected Game game;


    protected Map<String, VirtualView> connectedClients;
    protected boolean isStarted;
    protected TurnController turnController;
    protected ArrayList<String> players;
    protected InputChecker inputChecker;
    protected GamePhase gamePhase;
    private TurnPhase turnPhase;

    protected int numberOfPlayers;

    public GameController(){
        this.connectedClients = new HashMap<>();
        isStarted = false;
        numberOfPlayers = 0;
        gamePhase = GamePhase.INIT;
        players = new ArrayList<>();
    }

    public abstract void startGame();


    public void onMessageReceived(Message message, String nickname){
        VirtualView virtualView = connectedClients.get(nickname);
        if (!turnController.getActivePlayer().equals(nickname)) {
            virtualView.update(new ErrorMessage("It's not your turn"));
        } else {
            switch (gamePhase) {
                case FIRST_ROUND:
                    firstRoundHandler(message, virtualView);
                    break;
                case IN_GAME:
                    inGameHandler(message, virtualView);
            }
        }
    }
    public Map<String, VirtualView> getConnectedClients() {
        return connectedClients;
    }

    public void addConnectedClient(String nickname, VirtualView virtualView) {
        this.connectedClients.put(nickname,virtualView);
    }

    public void removeConnectedClient(String nickname){
        this.connectedClients.remove(nickname);
    }
    public void addAllConnectedClients(Map<String, VirtualView> clients){
        this.connectedClients.putAll(clients);
    }

    public void sendAll(Message message){
        for(VirtualView vv: connectedClients.values()){
            vv.update(message);
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public VirtualView getVirtualView(String name){
        return connectedClients.get(name);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public VirtualView getVirtualViewByNickname(String nickname){
        return connectedClients.get(nickname);
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


    public abstract void activateLeaderCard(int id) throws NullCardException;

    public abstract void discardLeaderCards(int[] id) throws NullCardException;

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public String getPlayerByPosition(int pos){
        return players.get(pos);
    }

    public ArrayList<String> getPlayers(){
        return players;
    }
    public void addPlayer(String player) {
        players.add(player);
    }



    public Game getGame(){return  game;}

    /**
     * sends a generic messages to all clients except one
     * @param message message to be sent
     * @param virtualView virtual view excluded
     */
    public void sendAllExcept(Message message, VirtualView virtualView){
        for(VirtualView vv: connectedClients.values()){
            if(vv.getNickname().equals(virtualView.getNickname())) {
                vv.update(message);
            }
        }
    }

    /**
     * handels the actions in the game
     * @param message
     * @param virtualView
     */

    public void inGameHandler(Message message, VirtualView virtualView){
        Gson gson = new Gson();
        if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
            switch (message.getCode()) {
                case BUY_DEV:
                    int[] dim = gson.fromJson(message.getPayload(), int[].class);
                    buyDevelopment(dim[0], dim[1]);
                    break;
                case SLOT_CHOICE:
                    int slot = gson.fromJson(message.getPayload(), int.class);
                    placeCard(slot);
                    break;
                case RESOURCE_PAYMENT:
                    int[] ids = gson.fromJson(message.getPayload(), int[].class);
                    payCard(ids);
                    break;


            }
        }else{
            virtualView.sendInvalidActionMessage();
        }
    }

    /**
     * handles the first round of the game where the players can only discard 2 leadercards and choose initial the resources
     * @param message
     * @param virtualView
     */
    public void firstRoundHandler(Message message, VirtualView virtualView){
        Gson gson = new Gson();
        if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {

            switch (message.getCode()) {
                case DISCARD_LEADER:
                    try {
                        discardLeaderCards(gson.fromJson(message.getPayload(), int[].class));
                    } catch (NullCardException e) {
                        virtualView.update(new ErrorMessage(e.getMessage()));
                    }
                    break;
                case CHOOSE_RESOURCES:
                    ResourceType[] resources = gson.fromJson(message.getPayload(), ResourceType[].class);
                    for (ResourceType resourceType : resources) {
                        Resource resource = new Resource(resourceType);
                        virtualView.addFreeResource(resource);
                    }
                    placeResources();
                    break;
                case PLACE_RESOURCE_WAREHOUSE:
                    boolean success;
                    int[] id = gson.fromJson(message.getPayload(), int[].class);
                    try {
                        putResource(id);
                        success = true;
                    } catch (NotPossibleToAdd notPossibleToAdd) {
                        virtualView.update(new ErrorMessage(notPossibleToAdd.getMessage()));
                        success = false;
                    }
                    if (success) {
                        virtualView.removeFreeResources(0);
                    }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
            }
        }else{
            virtualView.sendInvalidActionMessage();
        }
    }

    /**
     * replies to the request to buy a development card
     * @param rig row in the matrix of developments
     * @param col column in the matrix of developments
     */
   public void buyDevelopment(int rig, int col){
       turnController.doneGameAction();
       String name = game.getCurrentPlayer().getNickName();
       VirtualView virtualView = getConnectedClients().get(name);
        if(game.getDeckDevelopment()[rig][col].getCard().isBuyable(game.getCurrentPlayer())){
            virtualView.addFreeDevelopment(game.getDeckDevelopment()[rig][col].popCard());
        }else{
            virtualView.update(new ErrorMessage("You don't have enough resources to buy this card, choose another one"));
        }
   }


    /**
     * place the payed card in the chosen slot
     * @param slot
     */
   public void placeCard(int slot){
       String name = game.getCurrentPlayer().getNickName();
       VirtualView virtualView = getConnectedClients().get(name);
       boolean success;
       try{
           game.getCurrentPlayer().getPlayerBoard().addDevCard(virtualView.getFreeDevelopment().get(0),slot);
           success = true;
       } catch (CannotAdd cannotAdd) {
           virtualView.update(new ErrorMessage(cannotAdd.getMessage()));
           success = false;
       }
       if(success){
           virtualView.update(new OkMessage("Card has been palced successfully"));
           virtualView.removeFreeDevelopment(0);
       }

   }

    /**
     * removes from warehouse and strongbox all the resources used to pay a development card
     * @param ids ids of the depot where he need to take the resources, -1 if it's the strongbox
     */
   public void payCard(int[] ids){
        String name = game.getCurrentPlayer().getNickName();
       VirtualView virtualView = getConnectedClients().get(name);
       ArrayList<Resource> cost = virtualView.getFreeDevelopment().get(0).getCost();

       for(int j: ids){
           if(j != -1) {
               cost.remove(game.getCurrentPlayer().getDepotById(j).getDepot().get(0));
               game.getCurrentPlayer().getDepotById(j).removeResource();
           }
       }

       for(Resource resource: cost){
           game.getCurrentPlayer().getPlayerBoard().getStrongBox().removeResources(resource);
       }

       virtualView.update(new OkMessage("Card payed successfully !"));
   }

    /**
     * if the player has some resources that has not be placed yet , he have to choose were to put them
     * before proceeding with the turn
     */
    public void placeResources(){
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        while(!virtualView.getFreeResources().isEmpty()) {
            if (gamePhase == GamePhase.FIRST_ROUND || turnPhase == TurnPhase.BUY_MARKET) {
                virtualView.update(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(virtualView.getFreeResources().get(0))));
            } else {
                virtualView.update(new Message(MessageType.PLACE_RESOURCE_WHEREVER, gson.toJson(virtualView.getFreeResources().get(0))));
            }
        }
        if(gamePhase == GamePhase.FIRST_ROUND){
            game.nextPlayer();
            turnController.nextTurn();
        }

    }


    /**
     * tries to put the resources in the required place in the warehouse or strong box or discard the resource
     * @param id of the depot or strongbox if it's 0
     */
    public void putResource(int[] id) throws NotPossibleToAdd {
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for(int j: id)
            if (j == 0) {
                game.getCurrentPlayer().getPlayerBoard().getStrongBox().addResources(virtualView.getFreeResources().get(0));
            } else {
                if (j == -1) {
                    for (Player player : game.getPlayers()) {
                        if (!player.getNickName().equals(game.getCurrentPlayer().getNickName())) {
                            player.getPlayerBoard().moveFaithMarker(1);
                        }
                    }
                    if (game.checkPopeSpace()) {
                        sendAll(new Message(MessageType.FAITH_TRACK, gson.toJson(game.getFaithTrack())));
                    }
                    sendAllExcept(new Message(MessageType.FAITH_MOVE, "1"), virtualView);
                } else {
                    game.getCurrentPlayer().getDepotById(j).addResource(virtualView.getFreeResources().get(0));
                }
            }
        }


    }



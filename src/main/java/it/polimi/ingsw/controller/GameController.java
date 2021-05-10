package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
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

    protected GamePhase gamePhase;

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

    public abstract void placeResources(MessageType code);

    public abstract void putResource(int id) throws NotPossibleToAdd;

    public Game getGame(){return  game;}

    public void sendAllExcept(Message message, VirtualView virtualView){
        for(VirtualView vv: connectedClients.values()){
            if(vv.getNickname().equals(virtualView.getNickname())) {
                vv.update(message);
            }
        }
    }

    public void inGameHandler(Message message, VirtualView virtualView){
        Gson gson = new Gson();

        switch (message.getCode()){
            case BUY_DEV:
                int[] dim = gson.fromJson(message.getPayload(), int[].class);
                buyDevelopment(dim[0], dim[1]);
                break;
            case SLOT_CHOICE:
                int slot = gson.fromJson(message.getPayload(), int.class);
                placeCard(slot);
        }
    }

    public void firstRoundHandler(Message message, VirtualView virtualView){
        Gson gson = new Gson();
        switch(message.getCode()){
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
                placeResources(message.getCode());
                break;
            case PLACE_RESOURCE_WAREHOUSE:
                boolean success;
                int id = gson.fromJson(message.getPayload(), int.class);
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
    }

   public void buyDevelopment(int rig, int col){
       String name = game.getCurrentPlayer().getNickName();
       VirtualView virtualView = getConnectedClients().get(name);
        if(game.getDeckDevelopment()[rig][col].getCard().isBuyable(game.getCurrentPlayer())){
            virtualView.update(new Message(MessageType.SLOT_CHOICE,""));
            virtualView.addFreeDevelopment(game.getDeckDevelopment()[rig][col].popCard());
        }else{
            virtualView.update(new ErrorMessage("You don't have enough resources to buy this card, choose another one"));
        }
   }


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
           virtualView.update(new Message(MessageType.NEW_TURN_PHASE,""));
           virtualView.removeFreeDevelopment(0);
           turnController.changeTurnPhase();
       }

   }
}

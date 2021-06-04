package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.client.DummyModel.DummyStrongbox;
import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.InvalidNickname;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.WarehouseConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
//TODO riconnessione client con gestione del turno da finire
//TODO scegliere cosa fare dei client quando finisce la partita
/**
 * class game controller and subclasses handles the evolution of the game based on
 * the messages from client
 * @author Alice Cariboni
 */
public class GameController implements Serializable {
    protected Game game;
    private static final long serialVersionUID = -4658746997554128681L;
    transient Gson gson ;


    protected transient Map<String, VirtualView> connectedClients;



    protected ArrayList<String> disconnectedClients;
    protected boolean isStarted;
    protected TurnController turnController;
    protected ArrayList<String> players;
    protected InputChecker inputChecker;
    protected GamePhase gamePhase;
    protected TurnPhase turnPhase;
    protected boolean startedAction;
    protected int numberOfPlayers;

    public GameController(){
        gson = new Gson();
        connectedClients = Collections.synchronizedMap(new HashMap<>());
        disconnectedClients = new ArrayList<>();
        isStarted = false;
        numberOfPlayers = 0;
        gamePhase = GamePhase.INIT;
        players = new ArrayList<>();
        startedAction = false;
    }




    public void onMessageReceived(Message message, String nickname) {
        VirtualView virtualView = connectedClients.get(nickname);
        if (!turnController.getActivePlayer().equals(nickname)) {
            virtualView.update(new ErrorMessage("It's not your turn"));
        } else {
            switch (this.gamePhase) {
                case FIRST_ROUND -> firstRoundHandler(message, virtualView);
                case IN_GAME, LAST_ROUND -> actionHandler(message, virtualView);
                default-> virtualView.update(new ErrorMessage("not possible"));
            }
        }
    }

    public ArrayList<String> getDisconnectedClients() {
        return disconnectedClients;
    }

    public Map<String, VirtualView> getConnectedClients() {
        return connectedClients;
    }

    public void addConnectedClient(String nickname, VirtualView virtualView) {
        connectedClients.put(nickname,virtualView);
    }

    public void addDisconnectedClient(String nickname){
        connectedClients.remove(nickname);
        disconnectedClients.add(nickname);
    }

    /**
     * sends the depots new depots to the current player, regular depot and extra depots
     */
    public void sendDepots(){
        connectedClients.get(turnController.getActivePlayer()).update(new Message(MessageType.DEPOTS,gson.toJson(game.getCurrentPlayer().getPlayerBoard().getWareHouse().getDummy())));
    }


    /**
     *sends the new strongbox to the current player
     */
    public void sendStrongBox(){

        DummyStrongbox dummyStrongbox = game.getCurrentPlayer().getPlayerBoard().getStrongBox().getDummy();
        connectedClients.get(turnController.getActivePlayer()).update(new Message(MessageType.DUMMY_STRONGBOX, gson.toJson(dummyStrongbox)));
    }

    public void removeConnectedClient(String nickname){
        connectedClients.remove(nickname);
    }
    public void addAllConnectedClients(Map<String, VirtualView> clients){
        connectedClients = Collections.synchronizedMap(new HashMap<>());
        connectedClients.putAll(clients);
    }

    /**
     * sends a generic message to all clients
     * @param message
     */
    public void sendAll(Message message){
        for(VirtualView vv: connectedClients.values()){
            vv.update(message);
        }
    }

    /**
     * sends the new dummy devs to the player
     */
    public void sendDummyDevs(){
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);

        DummyDev[] dummyDevs = new DummyDev[3];
        DevelopmentCard[] developmentCards = game.getCurrentPlayer().getPlayerBoard().getDevCardSlots();
        for(int i = 0; i < Constants.DEV_SLOTS; i++){
            if(developmentCards[i] != null) {
                dummyDevs[i] = developmentCards[i].getDummy();
            }else{
                dummyDevs[i] = null;
            }
        }
        virtualView.update(new Message(MessageType.DUMMY_DEVS,gson.toJson(dummyDevs)));
    }

    /**
     * sends the updated dummy leadercards to the player
     */
    public void sendDummyLead(){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);

        ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards = game.getCurrentPlayer().getLeadercards();
        for(LeaderCard lc: leaderCards){
            dummyLeaderCards.add(lc.getDummy());
        }
        virtualView.update(new Message(MessageType.DUMMY_LEADER_CARD,gson.toJson(dummyLeaderCards)));
    }

    /**
     * sends all the structures of the saved game to the players
     */
    public void sendRestoredGame(){
        gson = new Gson();
        try{
            sendUpdateMarketDev();
            sendUpdateMarketTray();
            sendUpdateFaithTrack();
            sendAllUpdateFaith();
            for(VirtualView virtualView : connectedClients.values()){
                ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
                ArrayList<LeaderCard> leaderCards = null;
                    leaderCards = game.getPlayerByNickname(virtualView.getNickname()).getLeadercards();

                for(LeaderCard lc: leaderCards){
                    dummyLeaderCards.add(lc.getDummy());
                }
                virtualView.update(new Message(MessageType.DUMMY_LEADER_CARD,gson.toJson(dummyLeaderCards)));

                DummyDev[] dummyDevs = new DummyDev[3];
                DevelopmentCard[] developmentCards = game.getPlayerByNickname(virtualView.getNickname()).getPlayerBoard().getDevCardSlots();
                for(int i = 0; i < Constants.DEV_SLOTS; i++){
                    if(developmentCards[i] != null) {
                        dummyDevs[i] = developmentCards[i].getDummy();
                    }else{
                        dummyDevs[i] = null;
                    }
                }
                virtualView.update(new Message(MessageType.DUMMY_DEVS,gson.toJson(dummyDevs)));

                virtualView.update(new Message(MessageType.DEPOTS,gson.toJson(game.getPlayerByNickname(virtualView.getNickname()).getPlayerBoard().getWareHouse().getDummy())));


                DummyStrongbox dummyStrongbox = game.getPlayerByNickname(virtualView.getNickname()).getPlayerBoard().getStrongBox().getDummy();
                virtualView.update(new Message(MessageType.DUMMY_STRONGBOX, gson.toJson(dummyStrongbox)));
            }
            getVirtualView(turnController.getActivePlayer()).update(new Message(MessageType.NOTIFY_TURN, ""));
            sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's " + turnController.getActivePlayer() + " turn, wait"), getVirtualView(turnController.getActivePlayer()));
        } catch (InvalidNickname invalidNickname) {
            invalidNickname.printStackTrace();
        }
    }

    public void sendUpdateMove(PlayerMove playerMove){
        sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "ACTION: " + playerMove.name() + " PLAYER: " + this.game.getCurrentPlayer().getNickName()), this.getVirtualView(this.game.getCurrentPlayer().getNickName()));
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


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * sends the updated market tray to all players
     */
    public void sendUpdateMarketTray(){
        Gson gson = new Gson();
        DummyMarket dummyMarket = game.getMarketTray().getDummy();

            sendAll(new Message(MessageType.MARKET_TRAY, gson.toJson(dummyMarket)));

    }

    /**
     * sends the updated faith track to all players
     */
    public void sendUpdateFaithTrack(){
        Gson gson = new Gson();
        FaithTrack dummyFaithTrack = game.getFaithTrack();
        sendAll(new Message(MessageType.FAITH_TRACK, gson.toJson(dummyFaithTrack)));

    }

    /**
     * sends the update development card market to all players
     */
    public void sendUpdateMarketDev(){
        Gson gson = new Gson();
       DummyDev[][] dummyDevs = new DummyDev[Constants.rows][Constants.cols];
       for(int r = 0; r < Constants.rows; r++){
           for(int c = 0; c < Constants.cols; c++){
               if(!game.getDeckDevelopment()[r][c].isEmpty()) {
                   dummyDevs[r][c] = game.getDeckDevelopment()[r][c].getCard().getDummy();
               }else{
                   dummyDevs[r][c] = null;
               }
           }
       }
           sendAll(new Message(MessageType.DEVELOPMENT_MARKET, gson.toJson(dummyDevs)));

   }



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
            if(!(vv.getNickname().equals(virtualView.getNickname()))) {
                vv.update(message);
            }
        }
    }

    public void sendResourcesToPlace(){
        ArrayList<String> names = new ArrayList<>();
        VirtualView virtualView = getVirtualView(game.getCurrentPlayer().getNickName());
        for (Resource res : game.getCurrentPlayer().getPlayerBoard().getUnplacedResources()) {
            names.add(res.getResourceType().name());
        }
        virtualView.update(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(names)));
    }

    /**
     * sends the updated faithmarker, if there is a vatican report it sends the new faith track to all players
     */
    public void updateFaith(){
         VirtualView virtualView = connectedClients.get(turnController.getActivePlayer());
        virtualView.update(new Message(MessageType.FAITH_MOVE,gson.toJson(game.getCurrentPlayer().getPlayerBoard().getFaithMarker())));
         if(game.checkPopeSpace()){
             sendUpdateFaithTrack();
         }
    }

    public void sendAllUpdateFaith(){
        for(VirtualView virtualView : connectedClients.values()) {
            virtualView.update(new Message(MessageType.FAITH_MOVE, gson.toJson(game.getCurrentPlayer().getPlayerBoard().getFaithMarker())));
            if (game.checkPopeSpace()) {
                sendUpdateFaithTrack();
            }
        }
    }

    public void actionHandler(Message message, VirtualView virtualView) {
        switch(turnPhase){
            case FREE:
                inGameHandler(message,virtualView);
                break;
            case BUY_DEV:
                buyDevHandler(message,virtualView);
                break;
            case BUY_MARKET:
                buyMarketHandler(message,virtualView);
                break;
            case ACTIVATE_PRODUCTION:
                productionHandler(message, virtualView);
                break;
        }
    }

    protected  void buyMarketHandler(Message message, VirtualView virtualView) {
        Gson gson = new Gson();
            switch (message.getCode()) {
                case WHITE_MARBLES:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        String[] marb = gson.fromJson(message.getPayload(), String[].class);
                        chooseWhiteMarbleEffect(marb);
                    }else {
                        virtualView.update(new ErrorMessage(""));
                        virtualView.update(new Message(MessageType.WHITE_MARBLES,gson.toJson(virtualView.getFreeMarble().size())));
                    }
                    break;
                case PLACE_RESOURCE_WAREHOUSE:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        int[] id = gson.fromJson(message.getPayload(), int[].class);
                        try {
                            putResource(id);
                            virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                            virtualView.doneGameAction(1);

                        } catch (NotPossibleToAdd notPossibleToAdd) {
                            virtualView.update(new ErrorMessage(notPossibleToAdd.getMessage()));
                            sendResourcesToPlace();
                        }
                    }else{
                        virtualView.sendInvalidActionMessage();
                        sendResourcesToPlace();
                    }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                    break;
            }

    }

    public void productionHandler(Message message, VirtualView virtualView) {
            switch (message.getCode()) {
                    case RESOURCE_PAYMENT:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        int[] ids = gson.fromJson(message.getPayload(), int[].class);
                        pay(ids);

                        virtualView.doneGameAction(1);
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                    }else {
                      virtualView.update(new ErrorMessage(""));
                      sendResourcesToPay();
                    }
                    break;
                case ACTIVATE_PRODUCTION:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        addProductionPower(gson.fromJson(message.getPayload(), int[].class));
                        sendResourcesToPay();
                    }else{
                        turnPhase = TurnPhase.FREE;
                        virtualView.removeAllExtraProduction();
                        virtualView.removeAllResourcesToProduce();
                        virtualView.removeCardsToActivate();
                        virtualView.removeResourcesToPay();
                        virtualView.update(new ErrorMessage(""));
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                    }
                    break;
                case EXTRA_PRODUCTION:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        String[] command = gson.fromJson(message.getPayload(),String[].class);
                        int id = Integer.parseInt(command[0]);
                        Resource resource = new Resource(ResourceType.valueOf(command[1]));
                        addExtraProductionPower(id, resource);
                        sendResourcesToPay();
                    }else{
                        turnPhase = TurnPhase.FREE;
                        virtualView.removeAllExtraProduction();
                        virtualView.removeAllResourcesToProduce();
                        virtualView.removeCardsToActivate();
                        virtualView.removeResourcesToPay();
                        virtualView.update(new ErrorMessage(""));
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                    }
                    break;
                case BASE_PRODUCTION:
                if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                    String[] command = gson.fromJson(message.getPayload(),String[].class);
                    Resource res1 = new Resource(ResourceType.valueOf(command[0]));
                    Resource res2 = new Resource(ResourceType.valueOf(command[1]));
                    Resource res3 = new Resource(ResourceType.valueOf(command[2]));
                    addBasicProduction(res1,res2,res3);
                    sendResourcesToPay();
                }else{
                    turnPhase = TurnPhase.FREE;
                    virtualView.removeAllExtraProduction();
                    virtualView.removeAllResourcesToProduce();
                    virtualView.removeCardsToActivate();
                    virtualView.removeResourcesToPay();
                    virtualView.update(new ErrorMessage(""));
                    virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                    break;
            }

        }


    private void sendResourcesToPay() {
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        ArrayList<String> names = new ArrayList<>();
        DevelopmentCard developmentCard;
        if(turnPhase == TurnPhase.BUY_DEV){
            for (Resource res : virtualView.getFreeDevelopment().get(0).getCost()) {
                names.add(res.getResourceType().name());
            }
        }
        else{
            for (Resource res : virtualView.getResourcesToPay()) {
                names.add(res.getResourceType().name());
            }
        }

        virtualView.update(new Message(MessageType.RESOURCE_PAYMENT, gson.toJson(names)));
    }

    public  void buyDevHandler(Message message, VirtualView virtualView) {
        Gson gson = new Gson();
            switch (message.getCode()) {
                case RESOURCE_PAYMENT:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        int[] ids = gson.fromJson(message.getPayload(), int[].class);
                        pay(ids);
                        sendDepots();
                        virtualView.doneGameAction(1);
                    }else {
                        virtualView.update(new ErrorMessage("Invalid message for this state"));
                        sendResourcesToPay();
                    }
                    break;
                case SLOT_CHOICE:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        int slot = gson.fromJson(message.getPayload(), int.class);
                        placeCard(slot);
                    }else{
                        virtualView.update(new ErrorMessage("Invalid message for this state"));
                        virtualView.update(new Message(MessageType.SLOT_CHOICE, ""));
                    }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                    break;
            }
        }



    /**
     * handels the actions in the game
     * @param message
     * @param virtualView
     */

    public void inGameHandler(Message message, VirtualView virtualView) {
        Gson gson = new Gson();
        if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
            switch (message.getCode()) {
                case BUY_DEV:
                    int[] dim = gson.fromJson(message.getPayload(), int[].class);
                    buyDevelopment(dim[0], dim[1]);
                    break;
                case BUY_MARKET:
                    turnPhase = TurnPhase.BUY_MARKET;
                    String[] choice = gson.fromJson(message.getPayload(), String[].class);
                    if (choice[0].equalsIgnoreCase("row")) {
                        getFromMarketRow(Integer.parseInt(choice[1]));
                    } else {
                        getFromMarketCol(Integer.parseInt(choice[1]));
                    }
                    break;
                case DEPOTS:
                        Depot[] depots = WarehouseConstructor.parse(message.getPayload());
                        changeDepotsState(depots);
                        virtualView.update(new Message(MessageType.OK, ""));
                        sendDepots();
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                    break;
                case ACTIVATE_PRODUCTION:
                case EXTRA_PRODUCTION:
                case BASE_PRODUCTION:
                    turnPhase = TurnPhase.ACTIVATE_PRODUCTION;
                    productionHandler(message, virtualView);
                    break;
                case ACTIVATE_LEADER_CARD:
                    int id = gson.fromJson(message.getPayload(), int.class);
                    activateLeaderCard(id);
                    break;
                case DISCARD_LEADER:
                        discardLeaderCard(Integer.parseInt(message.getPayload()));
                        break;
                case END_TURN:
                    turnController.nextTurn();
                    break;
                case REMOVE_RESOURCES:
                    id = gson.fromJson(message.getPayload(), int.class);
                    removeResource(id);
                    break;
                case SEE_PLAYERBOARD:
                    String nickname = gson.fromJson(message.getPayload(), String.class);
                    if(numberOfPlayers == 1){
                        sendBlackCross();
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                    }else{
                    try {
                        sendOtherPlayerBoard(nickname);
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                    } catch (InvalidNickname invalidNickname) {
                        virtualView.update(new ErrorMessage("not a player"));
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                    }
                    }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
                    break;
            }
        }else{
            virtualView.sendInvalidActionMessage();
            virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
        }

    }




    /**
     * handles the first round of the game where the players can only discard 2 leadercards and choose initial the resources
     * @param message
     * @param virtualView
     */
    public void firstRoundHandler(Message message, VirtualView virtualView) {
        Gson gson = new Gson();

            switch (message.getCode()) {
                case DISCARD_LEADER:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        discardLeaderCard(Integer.parseInt(message.getPayload()));
                    }else{
                        virtualView.sendInvalidActionMessage();
                        virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                    }
                        break;
                case CHOOSE_RESOURCES:
                        String[] resources = gson.fromJson(message.getPayload(), String[].class);
                        for (String resourceType : resources) {
                            Resource resource = new Resource(ResourceType.valueOf(resourceType));
                            game.getCurrentPlayer().getPlayerBoard().addUnplacedResource(resource);
                        }
                        sendResourcesToPlace();
                    break;
                case PLACE_RESOURCE_WAREHOUSE:
                    if(inputChecker.checkReceivedMessage(message, turnController.getActivePlayer())) {
                        int[] id = gson.fromJson(message.getPayload(), int[].class);
                        try {
                            putResource(id);
                            virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                        } catch (NotPossibleToAdd notPossibleToAdd) {
                            virtualView.update(new ErrorMessage(notPossibleToAdd.getMessage()));
                            sendResourcesToPlace();
                        }
                    }else{
                        virtualView.sendInvalidActionMessage();
                        virtualView.update(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(game.getCurrentPlayer().getPlayerBoard().getUnplacedResources())));
                    }
                    break;
                case END_TURN:
                        turnController.nextTurn();
                        break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));

                    break;
            }
        }


    /**
     * replies to the request to buy a development card
     * @param rig row in the matrix of developments
     * @param col column in the matrix of developments
     */
   public void buyDevelopment( int rig,  int col){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        if(game.getDeckDevelopment()[rig][col].getCard().isBuyable(game.getCurrentPlayer())){
            virtualView.addFreeDevelopment(game.getDeckDevelopment()[rig][col].popCard());
            virtualView.doneGameAction(1);
            turnPhase = TurnPhase.BUY_DEV;
            sendUpdateMarketDev();
            sendResourcesToPay();
            sendUpdateMove(PlayerMove.BUY_DEV_CARD);
        }else{
            virtualView.update(new ErrorMessage("You don't have enough resources to buy this card, choose another one"));
            virtualView.update(new Message(MessageType.NOTIFY_TURN, ""));
        }
   }

   public void sendBlackCross(){
       VirtualView vv = getVirtualView(turnController.getActivePlayer());
       vv.update(new Message(MessageType.BLACK_CROSS, gson.toJson(game.getFakePlayer().getBlackCross())));
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
           sendUpdateMarketDev();
           sendDummyDevs();
           success = true;
       } catch (CannotAdd cannotAdd) {
           virtualView.update(new ErrorMessage(cannotAdd.getMessage()));
           virtualView.update(new Message(MessageType.SLOT_CHOICE, ""));
           success = false;
       }
       if(success){
           turnPhase = TurnPhase.FREE;
           virtualView.update(new OkMessage("Card has been placed successfully"));
           virtualView.update(new Message(MessageType.NOTIFY_TURN, " "));
           virtualView.removeFreeDevelopment(0);
       }

   }

    /**
     * sends to the player the playerboard of another player
     * @param nickname the other player
     */
    public void sendOtherPlayerBoard(String nickname) throws InvalidNickname {
      Player p = game.getPlayerByNickname(nickname);
      VirtualView vv = getVirtualView(game.getCurrentPlayer().getNickName());
      vv.update(new Message(MessageType.OTHER_FAITHMARKER, gson.toJson(p.getPlayerBoard().getFaithMarker())));

        DummyDev[] dummyDevs = new DummyDev[3];
        DevelopmentCard[] developmentCards = p.getPlayerBoard().getDevCardSlots();
        for(int i = 0; i < Constants.DEV_SLOTS; i++){
            if(developmentCards[i] != null) {
                dummyDevs[i] = developmentCards[i].getDummy();
            }else{
                dummyDevs[i] = null;
            }
        }
        vv.update(new Message(MessageType.OTHER_DEV_SLOTS ,gson.toJson(dummyDevs)));

       vv.update(new Message(MessageType.OTHER_WAREHOUSE,gson.toJson(p.getPlayerBoard().getWareHouse().getDummy())));



        DummyStrongbox dummyStrongbox = p.getPlayerBoard().getStrongBox().getDummy();
        vv.update(new Message(MessageType.OTHER_STRONGBOX, gson.toJson(dummyStrongbox)));


        ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards = p.getActiveLeaderCards();
        for(LeaderCard lc: leaderCards){
            dummyLeaderCards.add(lc.getDummy());
        }
        vv.update(new Message(MessageType.OTHER_LEADER,gson.toJson(dummyLeaderCards)));

    }

    /**
     * removes from warehouse and strongbox all the resources used to pay a development card or the production powers
     * @param ids ids of the depot where he need to take the resources, -1 if it's the strongbox
     */
   public void pay(int[] ids) {
       String name = game.getCurrentPlayer().getNickName();
       VirtualView virtualView = getConnectedClients().get(name);
       ArrayList<Resource> cost ;
       if(turnPhase == TurnPhase.BUY_DEV) {
           cost = virtualView.getFreeDevelopment().get(0).getCost();
       }else{
           cost = virtualView.getResourcesToPay();
       }
       for(int j: ids){
           if(j != -1) {
               cost.remove(game.getCurrentPlayer().getDepotById(j).getDepot().get(0));
               game.getCurrentPlayer().getDepotById(j).removeResource();
           }
       }

       for(Resource resource: cost){
           game.getCurrentPlayer().getPlayerBoard().getStrongBox().removeResources(resource);
       }
       if(turnPhase == TurnPhase.ACTIVATE_PRODUCTION){
           startProduction();
           virtualView.removeAllResourcesToProduce();
           turnPhase = TurnPhase.FREE;
       }else{
           virtualView.update(new Message(MessageType.SLOT_CHOICE, ""));
       }
       virtualView.update(new OkMessage("Payed successfully!"));
   }




    /**
     * tries to put the resources in the required place in the warehouse or strong box or discard the resource
     * @param id of the depot or strongbox if it's 0
     */
    public void putResource(int[] id) throws NotPossibleToAdd {
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for (int j : id) {
                if (j == -1) {
                    for (Player player : game.getPlayers()) {
                        if (!player.getNickName().equals(game.getCurrentPlayer().getNickName())) {
                            player.getPlayerBoard().moveFaithMarker(1);
                        }
                    }
                    sendAllUpdateFaith();
                } else {
                    Depot d = game.getCurrentPlayer().getDepotById(j);
                    game.getCurrentPlayer().getPlayerBoard().getWareHouse().addToDepot(game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(0), d);
                }
            game.getCurrentPlayer().getPlayerBoard().removeUnplacedResource(0);
        }
        sendDepots();
        sendStrongBox();
        setTurnPhase(TurnPhase.FREE);
        sendUpdateMove(PlayerMove.BUY_RESOURCES);
    }

    /**
     * get a row from the market tray and put the marbles in the client view waiting for other instructions
     * by the player it transform all the non white marbles in resources
     * @param row the number of the row the player wants
     */
    public void getFromMarketRow(int row){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        ArrayList<Marble> marbles = game.getMarketTray().getRow(row);
        for(Marble marble : marbles){
            if(marble.getMarbleColor() != MarbleColor.WHITE){
                marble.getMarbleEffect().giveResourceTo(game.getCurrentPlayer().getPlayerBoard());
            }
        }
        virtualView.addAllFreeMarbles(marbles);
        updateFaith();
        sendUpdateMarketTray();
        if((!game.getCurrentPlayer().getPossibleWhiteMarbles().isEmpty())&&(!virtualView.getFreeMarble().isEmpty())){
            virtualView.update(new Message(MessageType.WHITE_MARBLES,gson.toJson(virtualView.getFreeMarble().size())));
        }else{
            virtualView.removeAllFreeMarbles();
            sendResourcesToPlace();
        }
    }

    /**
     * get a column from the market tray and put the marbles in the client view waiting for other instructions
     * by the player it transform all the non white marbles in resources
     * @param col the number of the column the player wants
     */
    public void getFromMarketCol(int col){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        ArrayList<Marble> marbles = game.getMarketTray().getCol(col);

        for(Marble marble : marbles){
            if(marble.getMarbleColor() != MarbleColor.WHITE){
                marble.getMarbleEffect().giveResourceTo(game.getCurrentPlayer().getPlayerBoard());
            }
        }
        virtualView.addAllFreeMarbles(marbles);
        sendUpdateMarketDev();

        updateFaith();
        sendUpdateMarketTray();
        if((!game.getCurrentPlayer().getPossibleWhiteMarbles().isEmpty())&&(!virtualView.getFreeMarble().isEmpty())){
            virtualView.update(new Message(MessageType.WHITE_MARBLES,""));
        }else{
            virtualView.removeAllFreeMarbles();
            sendResourcesToPlace();
        }
    }

    /**
     * if the playes has some white marbles and has some white marble effect, the effect is applied
     * @param marb array of marble effect the player wants to apply
     */
    public void chooseWhiteMarbleEffect(String[ ] marb){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for(int i = 0; i < marb.length; i++){
            Resource resource = new Resource(ResourceType.valueOf(marb[i]));
            virtualView.getFreeMarble().get(i).setMarbleEffect(playerBoard -> {
                playerBoard.addUnplacedResource(resource);
            });
            virtualView.getFreeMarble().get(i).getMarbleEffect().giveResourceTo(game.getCurrentPlayer().getPlayerBoard());
        }
        virtualView.removeAllFreeMarbles();
        sendResourcesToPlace();
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    /**
     * rearrange depots as requested by the player
     */
    public void changeDepotsState(Depot[] depots){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for(Depot d: depots){
            Depot toChange = game.getCurrentPlayer().getDepotById(d.getId());
            toChange.setDepot(d.getDepot());
        }
    }

    /**
     * add a production power to the production powers that has to be activated in the virtual view
     * @param ids ids of the development cards
     */
    public void addProductionPower(int [] ids ){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
    for(int j : ids){
        for(DevelopmentCard developmentCard: game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards()){
            if(developmentCard.getId() == j){
                virtualView.addAllResourcesToPay(developmentCard.getProductionPower().getEntryResources());
            }
        }
        virtualView.addCardToActivate(j);
    }
    }

    /**
     * add an extraproduction power to the production powers that has to be activated in the virtual view
     * @param id id of the production power
     */
    public void addExtraProductionPower(int id, Resource resource){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
            for(ExtraProduction extraProduction : game.getCurrentPlayer().getExtraProductionPowers()){
                if(extraProduction.getId() == id){
                    virtualView.addAllResourcesToPay(extraProduction.getEntryResources());
                }
            }
            virtualView.addExtraProductionToActivate(id);
            virtualView.addResourceToProduce(resource);
    }

    /**
     * adds to the virtual model of the player the resources he has to pay and to produce
     * @param res1 first resource to pay
     * @param res2 second resource to pay
     * @param res3 resource to produce
     */
    public void addBasicProduction(Resource res1, Resource res2, Resource res3){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        if(virtualView.getBasicProd() == null) {
            ArrayList<Resource> toPay = new ArrayList<>();
            toPay.add(res1);
            toPay.add(res2);
            virtualView.addAllResourcesToPay(toPay);
            virtualView.setBasicProd(res3);
        }else{
            virtualView.update(new ErrorMessage("You can't activate the basic production more than one time"));
        }
    }

    /**
     * after the player pay , the production start and all the resources are added to strongbox
     * and the faithPoint are added to the fait marker
     */
    public void startProduction()  {
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for(int j : virtualView.getCardsToActivate()){
            for(DevelopmentCard dc: game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards()){
                if(dc.getId() == j){
                    dc.startProduction(game.getCurrentPlayer().getPlayerBoard(), game.getCurrentPlayer());
                }
            }
        }
        virtualView.removeCardsToActivate();
        game.getCurrentPlayer().getPlayerBoard().getStrongBox().addResources(virtualView.getBasicProd());

        for(int j : virtualView.getExtraProductionToActivate()){
            for(ExtraProduction ep: game.getCurrentPlayer().getExtraProductionPowers()){
                if(ep.getId() == j){
                    ep.startProduction(game.getCurrentPlayer().getPlayerBoard(), game.getCurrentPlayer(), virtualView.getResourcesToProduce().get(0));
                    virtualView.removeResourceToProduce(0);
                }
            }
        }
        virtualView.removeAllExtraProduction();
        sendDepots();
        sendStrongBox();
        updateFaith();
    }

    /**
     * activate the required leader card
     * @param id id of the leader card
     */
    public  void activateLeaderCard(int id)  {
        game.getCurrentPlayer().getLeaderCardById(id).active(game.getCurrentPlayer(), game.getCurrentPlayer().getPlayerBoard());
        sendDummyLead();
        sendDepots();
    }

    /**
     * tells the players if they're winners or losers
     */
    public  void endGame(){}


    /**
     * discard the selected leader card, if it's the first round proceed to add resources to
     * the players as in the rules, if it's not the turn of the player continues
     * @param id array of ids of the leadercard to discard form the hand of the player
     * @throws NullCardException if the player has not the card but this really shouldn't happen
     */
    public  void discardLeaderCard(int id)  {
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
            LeaderCard toDiscard = game.getCurrentPlayer().getLeaderCardById(id);
        try {
            game.getCurrentPlayer().discardLeader(toDiscard);
        } catch (NullCardException e) {
            virtualView.update(new ErrorMessage(""));
        }
        sendDummyLead();

        if (game.getCurrentPlayer().getLeadercards().size() < 2) {
                game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
                updateFaith();
            }

        virtualView.update(new OkMessage("Card successfully discarded!"));
        if(gamePhase == GamePhase.FIRST_ROUND){

            switch(players.indexOf(turnController.getActivePlayer())){
                case 0:
                    virtualView.update(new Message(MessageType.NOTIFY_TURN,""));
                    break;
                case 1:
                    if(game.getCurrentPlayer().getLeadercards().size() == 2) {
                        virtualView.update(new Message(MessageType.CHOOSE_RESOURCES, "1"));
                    }else{
                        virtualView.update(new Message(MessageType.NOTIFY_TURN,""));
                    }
                    break;
                case 2:
                    if(game.getCurrentPlayer().getLeadercards().size() == 2) {
                        game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
                        updateFaith();
                        virtualView.update(new Message(MessageType.CHOOSE_RESOURCES, "1"));
                    }else{
                        virtualView.update(new Message(MessageType.NOTIFY_TURN,""));
                    }
                    break;
                case 3:
                    if(game.getCurrentPlayer().getLeadercards().size() == 2) {
                        game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
                        updateFaith();
                        virtualView.update(new Message(MessageType.CHOOSE_RESOURCES, "2"));
                    }else{
                        virtualView.update(new Message(MessageType.NOTIFY_TURN,""));
                    }
                    break;
            }
        }else {
            updateFaith();
        }
    }

    /**
     * remove the one resource from the given depot if the depot is not empty and adds a faih point to all the other players
     *
     * @param id id of the depot
     */
    public void removeResource(int id) {
        Gson gson = new Gson();
        if(game.getCurrentPlayer().getDepotById(id).isEmpty()){
            getVirtualView(turnController.getActivePlayer()).update(new ErrorMessage("This depot is empty"));
        }else{
            game.getCurrentPlayer().getDepotById(id).removeResource();
            for(Player p: game.getPlayers()){
                if(!p.equals(game.getCurrentPlayer())) {
                    p.getPlayerBoard().moveFaithMarker(1);
                }
            }
            sendAllUpdateFaith();
            if(game.checkPopeSpace()){
                sendUpdateFaithTrack();
            }
            sendDepots();
            getVirtualView(turnController.getActivePlayer()).update(new Message(MessageType.NOTIFY_TURN,""));

        }
    }


    /**
     * it's called by the subclasses multigamecontroller and singlegame controller after creating the game
     * it sends the first structures to the player and creates an input checker and a turn controller
     */
    public void startGame()  {
        Server.LOGGER.info("instantiating game");
        this.inputChecker = new InputChecker(this, game);

        for(String name : players) {
            game.addPlayer(new Player(false, name, 0, new PlayerBoard(new WareHouse(), new StrongBox())));
        }

        game.startGame();

        sendUpdateMarketDev();
        sendUpdateFaithTrack();
        sendUpdateMarketTray();
        ArrayList<String> nickNames = new ArrayList<>();
        ArrayList<Player> players = game.getPlayers();

        for (Player player : players) {
            Server.LOGGER.info("giving cards to player " + player.getNickName());
            VirtualView vv = getVirtualView(player.getNickName());
            nickNames.add(player.getNickName());
            ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
            for (LeaderCard leaderCard : player.getLeadercards()) {
                dummyLeaderCards.add(leaderCard.getDummy());
            }
            Message message = new Message(MessageType.DUMMY_LEADER_CARD,gson.toJson(dummyLeaderCards));
            vv.update(message);
        }
        turnController = new TurnController(this, nickNames, game.getCurrentPlayer().getNickName(), this.game);
        setGamePhase(GamePhase.FIRST_ROUND);

        getVirtualView(turnController.getActivePlayer()).update(new Message(MessageType.GENERIC_MESSAGE,"You are the first player, discard 2 leader cards from your hand"));
        getVirtualView(turnController.getActivePlayer()).update(new Message(MessageType.NOTIFY_TURN,""));
        sendAllExcept(new Message(MessageType.END_TURN,""),getVirtualView(game.getCurrentPlayer().getNickName()));

        sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's " + turnController.getActivePlayer() + "'s turn, wait for your turn!"), getVirtualView(turnController.getActivePlayer()));

    }

    public boolean isStartedAction() {
        return startedAction;
    }

    public void setStartedAction(boolean startedAction) {
        this.startedAction = startedAction;
    }


    public void fakePlayerMove(){

    }
}





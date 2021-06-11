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
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.WarehouseConstructor;

import java.io.Serializable;
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
        initGameController();
    }


public void initGameController(){
    gson = new Gson();
    connectedClients = Collections.synchronizedMap(new HashMap<>());
    disconnectedClients = new ArrayList<>();
    isStarted = false;
    numberOfPlayers = 0;
    gamePhase = GamePhase.INIT;
    players = new ArrayList<>();
    startedAction = false;
}

    public void onMessageReceived(String line , String nickname) {
        VirtualView virtualView = connectedClients.get(nickname);
        if (!turnController.getActivePlayer().equals(nickname)) {
            virtualView.update(new ErrorMessage("It's not your turn"));
        } else {
            switch (this.gamePhase) {
                case FIRST_ROUND -> firstRoundHandler(line, virtualView);
                case IN_GAME, LAST_ROUND -> actionHandler(line, virtualView);
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
        sendAll(new Message(MessageType.GENERIC_MESSAGE, "Player " + nickname + " disconnected"));
    }

    /**
     * sends the depots new depots to the current player, regular depot and extra depots
     */
    public void sendDepots(VirtualView vv, String nickname){
        try {
            vv.update(new DepotMessage(game.getPlayerByNickname(nickname).getPlayerBoard().getWareHouse().getDummy()));
        } catch (InvalidNickname invalidNickname) {
            invalidNickname.printStackTrace();
        }
    }
    public void removeAllDisconnectedClients(){
        disconnectedClients.removeAll(disconnectedClients);
    }

    /**
     *sends the new strongbox to the current player
     */
    public void sendStrongBox(VirtualView vv, String nickname){

        DummyStrongbox dummyStrongbox = null;
        try {
            dummyStrongbox = game.getPlayerByNickname(nickname).getPlayerBoard().getStrongBox().getDummy();
        } catch (InvalidNickname invalidNickname) {
            invalidNickname.printStackTrace();
        }
        vv.update(new Message(MessageType.DUMMY_STRONGBOX, gson.toJson(dummyStrongbox)));
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
    public void sendDummyDevs(VirtualView virtualView, String nickname){

        DummyDev[] dummyDevs = new DummyDev[3];
        DevelopmentCard[] developmentCards = new DevelopmentCard[0];
        try {
            developmentCards = game.getPlayerByNickname(nickname).getPlayerBoard().getDevCardSlots();
        } catch (InvalidNickname invalidNickname) {
            invalidNickname.printStackTrace();
        }
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
    public void sendDummyLead(VirtualView virtualView, String nickname) {

        ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards = null;
        try {
            leaderCards = game.getPlayerByNickname(nickname).getLeadercards();
        } catch (InvalidNickname invalidNickname) {
            invalidNickname.printStackTrace();
        }
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
            removeAllDisconnectedClients();
            for(String name : connectedClients.keySet()){
                sendDummyLead(connectedClients.get(name), name);
                sendDummyDevs(connectedClients.get(name), name);

                sendDepots(connectedClients.get(name), name);

                sendStrongBox(connectedClients.get(name), name);

                sendUpdateMarketDev(connectedClients.get(name), name);
                sendUpdateMarketTray(connectedClients.get(name), name);
                sendUpdateFaithTrack(connectedClients.get(name), name);
                updateFaith(connectedClients.get(name) , name);
            }
            sendAll(new Message(MessageType.GENERIC_MESSAGE, "Game restored"));
            getVirtualView(turnController.getActivePlayer()).update( new NotifyTurn());
            sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's " + turnController.getActivePlayer() + " turn, wait"), getVirtualView(turnController.getActivePlayer()));

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
    public void sendUpdateMarketTray(VirtualView virtualView, String nickname){
        DummyMarket dummyMarket = game.getMarketTray().getDummy();

            virtualView.update(new Message(MessageType.MARKET_TRAY, gson.toJson(dummyMarket)));

    }

    /**
     * sends the updated faith track to all players
     */
    public void sendUpdateFaithTrack(VirtualView virtualView, String nickname){
        FaithTrack dummyFaithTrack = game.getFaithTrack();
        virtualView.update(new Message(MessageType.FAITH_TRACK, gson.toJson(dummyFaithTrack)));

    }

    /**
     * sends the update development card market to all players
     */
    public void sendUpdateMarketDev(VirtualView virtualView, String nickname){
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
        virtualView.update(new DevelopmentMarket(dummyDevs));

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


    public void reconnectClient(String nickname,VirtualView virtualView){
        sendAll(new Message(MessageType.GENERIC_MESSAGE, "Player " + nickname + " reconnected"));
        disconnectedClients.remove(nickname);
        connectedClients.put(nickname, virtualView);
        VirtualView vv = getVirtualView(nickname);
        sendStrongBox(vv, nickname);
        sendDepots(vv, nickname);
        sendDummyDevs(vv, nickname);
        sendDummyLead(vv, nickname);
        sendUpdateMarketDev(vv, nickname);
        sendUpdateMarketTray(vv, nickname);
        sendUpdateFaithTrack(vv, nickname);
        updateFaith(vv, nickname);
        try {
            vv.update(new Message(MessageType.FAITH_MOVE, gson.toJson(game.getPlayerByNickname(nickname).getPlayerBoard().getFaithMarker())));
        } catch (InvalidNickname invalidNickname) {
            invalidNickname.printStackTrace();
        }


        connectedClients.get(nickname).update(new EndTurn());
        connectedClients.get(nickname).update(new Message(MessageType.GENERIC_MESSAGE, "It's " + turnController.getActivePlayer() + "'s turn, wait"));
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
    public void updateFaith(VirtualView view, String nickname){
        try {
            view.update(new Message(MessageType.FAITH_MOVE,gson.toJson(game.getPlayerByNickname(nickname).getPlayerBoard().getFaithMarker())));
        } catch (InvalidNickname invalidNickname) {
            invalidNickname.printStackTrace();
        }
        if(game.checkPopeSpace()){
             for(String name : connectedClients.keySet()) {
                 sendUpdateFaithTrack(connectedClients.get(name), name);
             }
         }
    }

    public void sendAllUpdateFaith(){
        if (game.checkPopeSpace()) {
            for(String name : connectedClients.keySet()) {
                sendUpdateFaithTrack(connectedClients.get(name) , name);
            }
        }
        for(VirtualView virtualView : connectedClients.values()) {
            try {
                virtualView.update(new Message(MessageType.FAITH_MOVE, gson.toJson(game.getPlayerByNickname(virtualView.getNickname()).getPlayerBoard().getFaithMarker())));
            } catch (InvalidNickname invalidNickname) {
                invalidNickname.printStackTrace();
            }
        }
    }

    public void actionHandler(String line, VirtualView virtualView) {

        switch(turnPhase){
            case FREE:
                inGameHandler(line,virtualView);
                break;
            case BUY_DEV:
                buyDevHandler(line,virtualView);
                break;
            case BUY_MARKET:
                buyMarketHandler(line,virtualView);
                break;
            case ACTIVATE_PRODUCTION:
                productionHandler(line, virtualView);
                break;
        }
    }

    protected  void buyMarketHandler(String line, VirtualView virtualView) {
        Message message = gson.fromJson(line, Message.class);
            switch (message.getCode()) {
                case WHITE_MARBLES:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        String[] marb = gson.fromJson(message.getPayload(), String[].class);
                        chooseWhiteMarbleEffect(marb);
                    }else {
                        virtualView.update(new ErrorMessage(""));
                        virtualView.update(new Message(MessageType.WHITE_MARBLES,gson.toJson(virtualView.getFreeMarble().size())));
                    }
                    break;
                case PLACE_RESOURCE_WAREHOUSE:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        int[] id = gson.fromJson(message.getPayload(), int[].class);
                        try {
                            putResource(id);
                            virtualView.update(new NotifyTurn());
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
                    virtualView.update(new NotifyTurn());

                    break;
            }

    }

    public void productionHandler(String line, VirtualView virtualView) {
        Message message = gson.fromJson(line, Message.class);

        switch (message.getCode()) {
                    case RESOURCE_PAYMENT:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        int[] ids = gson.fromJson(message.getPayload(), int[].class);
                        pay(ids);

                        virtualView.doneGameAction(1);
                        virtualView.update(new NotifyTurn());
                    }else {
                      virtualView.update(new ErrorMessage(""));
                      sendResourcesToPay();
                    }
                    break;
                case ACTIVATE_PRODUCTION:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        ActivateDevProd activateDevProd = gson.fromJson(line, ActivateDevProd.class);
                        addProductionPower(activateDevProd.getIds());
                        sendResourcesToPay();
                    }else{
                        turnPhase = TurnPhase.FREE;
                        virtualView.removeAllExtraProduction();
                        virtualView.removeAllResourcesToProduce();
                        virtualView.removeCardsToActivate();
                        virtualView.removeResourcesToPay();
                        virtualView.update(new ErrorMessage(""));
                        virtualView.update(new NotifyTurn());
                    }
                    break;
                case EXTRA_PRODUCTION:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
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
                        virtualView.update(new NotifyTurn());
                    }
                    break;
                case BASE_PRODUCTION:
                if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                    ActivateBaseProd activateBaseProd = gson.fromJson(line,ActivateBaseProd.class);
                    String[] command = activateBaseProd.getCommand();
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
                    virtualView.update(new NotifyTurn());
                }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new NotifyTurn());
                    break;
            }

        }


    private void sendResourcesToPay() {
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        ArrayList<String> names = new ArrayList<>();
        DevelopmentCard developmentCard;
        if(turnPhase == TurnPhase.BUY_DEV){
            for (Resource res : game.getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment().getCost()) {
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

    public  void buyDevHandler(String line, VirtualView virtualView) {
        Message message = gson.fromJson(line, Message.class);
            switch (message.getCode()) {
                case RESOURCE_PAYMENT:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        int[] ids = gson.fromJson(message.getPayload(), int[].class);
                        pay(ids);
                        sendDepots(connectedClients.get(turnController.getActivePlayer()), turnController.getActivePlayer());
                        virtualView.doneGameAction(1);
                    }else {
                        virtualView.update(new ErrorMessage("Invalid message for this state"));
                        sendResourcesToPay();
                    }
                    break;
                case SLOT_CHOICE:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        int slot = gson.fromJson(message.getPayload(), int.class);
                        placeCard(slot);
                    }else{
                        virtualView.update(new ErrorMessage("Invalid message for this state"));
                        virtualView.update(new Message(MessageType.SLOT_CHOICE, ""));
                    }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new NotifyTurn());

                    break;
            }
        }



    /**
     * handels the actions in the game
     * @param line
     * @param virtualView
     */

    public void inGameHandler(String line, VirtualView virtualView) {
        Message message = gson.fromJson(line, Message.class);

        Gson gson = new Gson();
        if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
            switch (message.getCode()) {
                case BUY_DEV:
                    BuyDev buyDev = gson.fromJson(line, BuyDev.class);
                    int[] dim = buyDev.getCoordinates();
                    buyDevelopment(dim[0], dim[1]);
                    break;
                case BUY_MARKET:
                    turnPhase = TurnPhase.BUY_MARKET;
                    BuyMarket buyMarket = gson.fromJson(line, BuyMarket.class);
                    String choice = buyMarket.getRoc();
                    if (choice.equalsIgnoreCase("row")) {
                        getFromMarketRow(buyMarket.getNum());
                    } else {
                        getFromMarketCol(buyMarket.getNum());
                    }
                    break;
                case DEPOTS:
                        DepotMessage depotMessage = gson.fromJson(line, DepotMessage.class);
                        changeDepotsState(depotMessage.getWareHouse());
                        virtualView.update(new Message(MessageType.OK, ""));
                        sendDepots(connectedClients.get(turnController.getActivePlayer()) , turnController.getActivePlayer());
                        virtualView.update(new NotifyTurn());

                    break;
                case ACTIVATE_PRODUCTION:
                case EXTRA_PRODUCTION:
                case BASE_PRODUCTION:
                    turnPhase = TurnPhase.ACTIVATE_PRODUCTION;
                    productionHandler(line, virtualView);
                    break;
                case ACTIVATE_LEADER_CARD:
                    ActivateLeader activateLeader = gson.fromJson(line, ActivateLeader.class);
                    activateLeaderCard(activateLeader.getId());
                    break;
                case DISCARD_LEADER:
                    DiscardLeader discardLeader = gson.fromJson(line, DiscardLeader.class);
                    discardLeaderCard(discardLeader.getIds());
                        break;
                case END_TURN:
                    turnController.nextTurn();
                    break;
                case REMOVE_RESOURCES:
                    int id = gson.fromJson(message.getPayload(), int.class);
                    removeResource(id);
                    break;
                case SEE_PLAYERBOARD:
                    String nickname = gson.fromJson(message.getPayload(), String.class);
                    if(numberOfPlayers == 1){
                        sendBlackCross();
                        virtualView.update(new NotifyTurn());

                    }else{
                    try {
                        sendOtherPlayerBoard(nickname);
                        virtualView.update(new NotifyTurn());

                    } catch (InvalidNickname invalidNickname) {
                        virtualView.update(new ErrorMessage("not a player"));
                        virtualView.update(new NotifyTurn());
                    }
                    }
                    break;
                default:
                    virtualView.update(new ErrorMessage("Invalid message for this state"));
                    virtualView.update(new NotifyTurn());
                    break;
            }
        }else{
            virtualView.sendInvalidActionMessage();
            virtualView.update(new NotifyTurn());
        }

    }




    /**
     * handles the first round of the game where the players can only discard 2 leadercards and choose initial the resources
     * @param line
     * @param virtualView
     */
    public void firstRoundHandler(String line, VirtualView virtualView) {
        Gson gson = new Gson();
        Message message = gson.fromJson(line, Message.class);
            switch (message.getCode()) {
                case DISCARD_LEADER:
                    DiscardLeader discardLeader = gson.fromJson(line, DiscardLeader.class);
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        discardLeaderCard(discardLeader.getIds());
                    }else{
                        virtualView.sendInvalidActionMessage();
                        virtualView.update(new NotifyTurn());

                    }
                        break;
                case CHOOSE_RESOURCES:
                    ResourcesReply resourcesReply = gson.fromJson(line, ResourcesReply.class);
                        ArrayList<Resource> resources = resourcesReply.getRes();
                        for (Resource res : resources) {
                            game.getCurrentPlayer().getPlayerBoard().addUnplacedResource(res);
                        }
                        sendResourcesToPlace();
                    break;
                case PLACE_RESOURCE_WAREHOUSE:
                    if(inputChecker.checkReceivedMessage(line, turnController.getActivePlayer())) {
                        int[] id = gson.fromJson(message.getPayload(), int[].class);
                        try {
                            putResource(id);
                            virtualView.update(new NotifyTurn());

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
                    virtualView.update(new NotifyTurn());

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
            game.getCurrentPlayer().getPlayerBoard().setUnplacedDevelopment(game.getDeckDevelopment()[rig][col].popCard());
            virtualView.doneGameAction(1);
            turnPhase = TurnPhase.BUY_DEV;
            sendUpdateMarketDev(virtualView, name);
            sendResourcesToPay();
            sendUpdateMove(PlayerMove.BUY_DEV_CARD);
        }else{
            virtualView.update(new ErrorMessage("You don't have enough resources to buy this card, choose another one"));
            virtualView.update(new NotifyTurn());
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
           game.getCurrentPlayer().getPlayerBoard().addDevCard(game.getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment(),slot);
           sendUpdateMarketDev(virtualView, name);
           sendDummyDevs(connectedClients.get(turnController.getActivePlayer()) , turnController.getActivePlayer());
           success = true;
       } catch (CannotAdd cannotAdd) {
           virtualView.update(new ErrorMessage(cannotAdd.getMessage()));
           virtualView.update(new Message(MessageType.SLOT_CHOICE, ""));
           success = false;
       }
       if(success){
           turnPhase = TurnPhase.FREE;
           virtualView.update(new OkMessage("Card has been placed successfully"));
           virtualView.update(new NotifyTurn());
           game.getCurrentPlayer().getPlayerBoard().setUnplacedDevelopment(null);
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
           cost = game.getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment().getCost();
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
        for (int j : id) {
                if (j == -1) {
                    for (Player player : game.getPlayers()) {
                        if (!player.getNickName().equals(game.getCurrentPlayer().getNickName())) {
                            player.getPlayerBoard().moveFaithMarker(1);
                        }
                    }
                } else {
                    Depot d = game.getCurrentPlayer().getDepotById(j);
                    game.getCurrentPlayer().getPlayerBoard().getWareHouse().addToDepot(this.game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(0), d);
                }
            this.game.getCurrentPlayer().getPlayerBoard().removeUnplacedResource(0);
        }

        sendAllUpdateFaith();
        this.sendDepots(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());
        this.sendStrongBox(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());
        this.setTurnPhase(TurnPhase.FREE);
        this.sendUpdateMove(PlayerMove.BUY_RESOURCES);
    }

    /**
     * get a row from the market tray and put the marbles in the client view waiting for other instructions
     * by the player it transform all the non white marbles in resources
     * @param row the number of the row the player wants
     */
    public void getFromMarketRow(final int row){
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
        final ArrayList<Marble> marbles = this.game.getMarketTray().getRow(row);
        for(final Marble marble : marbles){
            if(marble.getMarbleColor() != MarbleColor.WHITE){
                marble.getMarbleEffect().giveResourceTo(this.game.getCurrentPlayer().getPlayerBoard());
            }
        }
        virtualView.addAllFreeMarbles(marbles);
        this.updateFaith(virtualView, name);
        this.sendUpdateMarketTray(virtualView, name);
        if((!this.game.getCurrentPlayer().getPossibleWhiteMarbles().isEmpty())&&(!virtualView.getFreeMarble().isEmpty())){
            virtualView.update(new Message(MessageType.WHITE_MARBLES, this.gson.toJson(virtualView.getFreeMarble().size())));
        }else{
            virtualView.removeAllFreeMarbles();
            this.sendResourcesToPlace();
        }
    }

    /**
     * get a column from the market tray and put the marbles in the client view waiting for other instructions
     * by the player it transform all the non white marbles in resources
     * @param col the number of the column the player wants
     */
    public void getFromMarketCol(final int col){
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
        final ArrayList<Marble> marbles = this.game.getMarketTray().getCol(col);

        for(final Marble marble : marbles){
            if(marble.getMarbleColor() != MarbleColor.WHITE){
                marble.getMarbleEffect().giveResourceTo(this.game.getCurrentPlayer().getPlayerBoard());
            }
        }
        virtualView.addAllFreeMarbles(marbles);
        this.sendUpdateMarketDev(virtualView, name);

        this.updateFaith(virtualView, name);
        this.sendUpdateMarketTray(virtualView, name);
        if((!this.game.getCurrentPlayer().getPossibleWhiteMarbles().isEmpty())&&(!virtualView.getFreeMarble().isEmpty())){
            virtualView.update(new Message(MessageType.WHITE_MARBLES,""));
        }else{
            virtualView.removeAllFreeMarbles();
            this.sendResourcesToPlace();
        }
    }

    /**
     * if the playes has some white marbles and has some white marble effect, the effect is applied
     * @param marb array of marble effect the player wants to apply
     */
    public void chooseWhiteMarbleEffect(final String[ ] marb){
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
        for(int i = 0; i < marb.length; i++){
            Resource resource = new Resource(ResourceType.valueOf(marb[i]));
            virtualView.getFreeMarble().get(i).setMarbleEffect(playerBoard -> {
                playerBoard.addUnplacedResource(resource);
            });
            virtualView.getFreeMarble().get(i).getMarbleEffect().giveResourceTo(this.game.getCurrentPlayer().getPlayerBoard());
        }
        virtualView.removeAllFreeMarbles();
        this.sendResourcesToPlace();
    }

    public TurnPhase getTurnPhase() {
        return this.turnPhase;
    }

    public void setTurnPhase(final TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    /**
     * rearrange depots as requested by the player
     */
    public void changeDepotsState( Depot[] depots){
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
        for( Depot d: depots){
             Depot toChange = this.game.getCurrentPlayer().getDepotById(d.getId());
            toChange.setDepot(d.getDepot());
        }
    }

    /**
     * add a production power to the production powers that has to be activated in the virtual view
     * @param ids ids of the development cards
     */
    public void addProductionPower(final int [] ids ){
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
    for(final int j : ids){
        for(final DevelopmentCard developmentCard: this.game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards()){
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
    public void addExtraProductionPower(final int id, final Resource resource){
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
            for(final ExtraProduction extraProduction : this.game.getCurrentPlayer().getExtraProductionPowers()){
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
    public void addBasicProduction(final Resource res1, final Resource res2, final Resource res3){
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
        if(virtualView.getBasicProd() == null) {
            final ArrayList<Resource> toPay = new ArrayList<>();
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
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
        for(final int j : virtualView.getCardsToActivate()){
            for(final DevelopmentCard dc: this.game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards()){
                if(dc.getId() == j){
                    dc.startProduction(this.game.getCurrentPlayer().getPlayerBoard(), this.game.getCurrentPlayer());
                }
            }
        }
        virtualView.removeCardsToActivate();
        if(virtualView.getBasicProd() != null) {
            this.game.getCurrentPlayer().getPlayerBoard().getStrongBox().addResources(virtualView.getBasicProd());
        }
        for(final int j : virtualView.getExtraProductionToActivate()){
            for(final ExtraProduction ep: this.game.getCurrentPlayer().getExtraProductionPowers()){
                if(ep.getId() == j){
                    ep.startProduction(this.game.getCurrentPlayer().getPlayerBoard(), this.game.getCurrentPlayer(), virtualView.getResourcesToProduce().get(0));
                    virtualView.removeResourceToProduce(0);
                }
            }
        }
        virtualView.removeAllExtraProduction();
        this.sendDepots(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());
        this.sendStrongBox(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());
        this.updateFaith(virtualView, name);
        virtualView.doneGameAction(1);
        virtualView.update(new NotifyTurn());
    }

    /**
     * activate the required leader card
     * @param id id of the leader card
     */
    public  void activateLeaderCard( int id)  {
        if (game.getCurrentPlayer().getLeaderCardById(id).isActivableBy(game.getCurrentPlayer().getPlayerBoard())) {
            this.game.getCurrentPlayer().getLeaderCardById(id).active(this.game.getCurrentPlayer(), this.game.getCurrentPlayer().getPlayerBoard());
            this.sendDummyLead(this.connectedClients.get(this.turnController.getActivePlayer()), this.turnController.getActivePlayer());
            this.sendDepots(this.connectedClients.get(this.turnController.getActivePlayer()), this.turnController.getActivePlayer());
        }else{
            getVirtualView(game.getCurrentPlayer().getNickName()).update(new ErrorMessage("You don't fit the requirements"));
        }
        getVirtualView(game.getCurrentPlayer().getNickName()).update(new NotifyTurn());
    }

    /**
     * tells the players if they're winners or losers
     */
    public void endGame(){
    }


    /**
     * discard the selected leader card, if it's the first round proceed to add resources to
     * the players as in the rules, if it's not the turn of the player continues
     * @param id array of ids of the leadercard to discard form the hand of the player
     * @throws NullCardException if the player has not the card but this really shouldn't happen
     */
    public  void discardLeaderCard(final int id)  {
        final String name = this.game.getCurrentPlayer().getNickName();
        final VirtualView virtualView = this.getConnectedClients().get(name);
            final LeaderCard toDiscard = this.game.getCurrentPlayer().getLeaderCardById(id);
        try {
            this.game.getCurrentPlayer().discardLeader(toDiscard);
        } catch (final NullCardException e) {
            virtualView.update(new ErrorMessage(""));
        }
        this.sendDummyLead(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());

        if (this.game.getCurrentPlayer().getLeadercards().size() < 2) {
            this.game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
            this.updateFaith(virtualView, name);
            }

        virtualView.update(new OkMessage("Card successfully discarded!"));
        if(this.gamePhase == GamePhase.FIRST_ROUND){

            switch(this.players.indexOf(this.turnController.getActivePlayer())){
                case 0:
                    virtualView.update(new NotifyTurn());
                    break;
                case 1:
                    if(this.game.getCurrentPlayer().getLeadercards().size() == 2) {
                        virtualView.update(new ResourceRequest(1));
                    }else{
                        virtualView.update(new NotifyTurn());
                    }
                    break;
                case 2:
                    if(this.game.getCurrentPlayer().getLeadercards().size() == 2) {
                        this.game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
                        this.updateFaith(virtualView, name);
                        virtualView.update(new ResourceRequest(1));
                    }else{
                        virtualView.update(new NotifyTurn());
                    }
                    break;
                case 3:
                    if(this.game.getCurrentPlayer().getLeadercards().size() == 2) {
                        this.game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
                        this.updateFaith(virtualView, name);
                        virtualView.update(new ResourceRequest(2));
                    }else{
                        virtualView.update(new NotifyTurn());
                    }
                    break;
            }
        }else{
            virtualView.update(new NotifyTurn());
        }
    }

    /**
     * remove the one resource from the given depot if the depot is not empty and adds a faih point to all the other players
     *
     * @param id id of the depot
     */
    public void removeResource(int id) {
        final Gson gson = new Gson();
        if(this.game.getCurrentPlayer().getDepotById(id).isEmpty()){
            this.getVirtualView(this.turnController.getActivePlayer()).update(new ErrorMessage("This depot is empty"));
        }else{
            this.game.getCurrentPlayer().getDepotById(id).removeResource();
            for(Player p: this.game.getPlayers()){
                if(!p.equals(this.game.getCurrentPlayer())) {
                    p.getPlayerBoard().moveFaithMarker(1);
                }
            }
            this.sendAllUpdateFaith();

            this.sendDepots(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());
            this.getVirtualView(this.turnController.getActivePlayer()).update(new NotifyTurn());

        }
    }


    /**
     * it's called by the subclasses multigamecontroller and singlegame controller after creating the game
     * it sends the first structures to the player and creates an input checker and a turn controller
     */
    public void startGame()  {
        Server.LOGGER.info("instantiating game");
        inputChecker = new InputChecker(this, this.game);

        for(final String name : this.players) {
            this.game.addPlayer(new Player(false, name, 0, new PlayerBoard(new WareHouse(), new StrongBox())));
        }

        this.game.startGame();
        for(final String name : this.connectedClients.keySet()) {

            this.sendUpdateMarketDev(this.connectedClients.get(name), name);
            this.sendUpdateFaithTrack(this.connectedClients.get(name), name);
            this.sendUpdateMarketTray(this.connectedClients.get(name), name);
        }
        final ArrayList<String> nickNames = new ArrayList<>();
        final ArrayList<Player> players = this.game.getPlayers();

        for (final Player player : players) {
            Server.LOGGER.info("giving cards to player " + player.getNickName());
            final VirtualView vv = this.getVirtualView(player.getNickName());
            nickNames.add(player.getNickName());
            final ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
            for (final LeaderCard leaderCard : player.getLeadercards()) {
                dummyLeaderCards.add(leaderCard.getDummy());
            }
            final Message message = new Message(MessageType.DUMMY_LEADER_CARD, this.gson.toJson(dummyLeaderCards));
            vv.update(message);
        }
        this.turnController = new TurnController(this, nickNames, this.game.getCurrentPlayer().getNickName(), game);
        this.setGamePhase(GamePhase.FIRST_ROUND);

        this.getVirtualView(this.turnController.getActivePlayer()).update(new Message(MessageType.GENERIC_MESSAGE,"You are the first player, discard 2 leader cards from your hand"));
        this.getVirtualView(this.turnController.getActivePlayer()).update(new NotifyTurn());
        this.sendAllExcept(new EndTurn(), this.getVirtualView(this.game.getCurrentPlayer().getNickName()));

        this.sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's " + this.turnController.getActivePlayer() + "'s turn, wait for your turn!"), this.getVirtualView(this.turnController.getActivePlayer()));

    }

    public boolean isStartedAction() {
        return this.startedAction;
    }

    public void setStartedAction(final boolean startedAction) {
        this.startedAction = startedAction;
    }


    public void fakePlayerMove(){

    }

    public void setGame(Game game) {
        this.game = game;
        this.inputChecker.setGame(game);
    }
}





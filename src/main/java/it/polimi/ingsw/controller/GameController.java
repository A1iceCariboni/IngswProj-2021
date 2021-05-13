package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.client.DummyModel.DummyStrongbox;
import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;
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
    protected TurnPhase turnPhase;

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

    /**
     * sends the depots new depots to the current player, regular depot and extra depots
     */
    public void sendDepots(){
        ArrayList<Depot> depots = game.getCurrentPlayer().getPlayerBoard().getWareHouse().getDepots();
        depots.addAll(game.getCurrentPlayer().getPlayerBoard().getExtraDepots());
        Gson gson = new Gson();
        connectedClients.get(turnController.getActivePlayer()).update(new Message(MessageType.DEPOTS,gson.toJson(depots)));
    }

    /**
     * sends to the player the leader card effect that he owns
     */
    public void sendWhiteMarbles(){
        ArrayList<String> marbles = new ArrayList<>();
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);

        ArrayList<Resource> resources = game.getCurrentPlayer().getPossibleWhiteMarbles();
            for(Resource resource: resources) {
                marbles.add(resource.getResourceType().name());
            }
            virtualView.update(new Message(MessageType.WHITE_MARBLES_POWERS,gson.toJson(marbles)));
    }

    /**
     * sends the player his extraproduction powers given by a leader effect
     */
    public void sendExtraProduction(){
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        ArrayList<ExtraProduction> extraProductions = game.getCurrentPlayer().getExtraProductionPowers();
        virtualView.update(new Message(MessageType.EXTRA_PRODUCTION,gson.toJson(extraProductions)));
    }
    /**
     * sends the player his discounted resource powers given by a leader effect
     */
    /**
     * sends to the player the leader card effect that he owns
     */
    public void sendDiscountedRes(){
        ArrayList<String> res = new ArrayList<>();
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);

        ArrayList<Resource> resources = game.getCurrentPlayer().getDiscountedResource();
        for(Resource resource: resources) {
            res.add(resource.getResourceType().name());
        }
        virtualView.update(new Message(MessageType.DISCOUNTED_RESOURCES,gson.toJson(res)));
    }
    /**
     *sends the new strongbox to the current player
     */
    public void sendStrongBox(){
        Gson gson = new Gson();

        DummyStrongbox dummyStrongbox = game.getCurrentPlayer().getPlayerBoard().getStrongBox().getDummy();
        connectedClients.get(turnController.getActivePlayer()).update(new Message(MessageType.DUMMY_STRONGBOX, gson.toJson(dummyStrongbox)));
    }

    public void removeConnectedClient(String nickname){
        this.connectedClients.remove(nickname);
    }
    public void addAllConnectedClients(Map<String, VirtualView> clients){
        this.connectedClients.putAll(clients);
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
        ArrayList<DevelopmentCard> developmentCards = game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards();
        for(int i = 0; i < Constants.DEV_SLOTS; i++){
            dummyDevs[i] = developmentCards.get(i).getDummy();
        }
        virtualView.update(new Message(MessageType.DUMMY_DEVS,gson.toJson(dummyDevs)));
    }

    /**
     * sends the updated dummy leadercards to the player
     */
    public void sendDummyLead(){
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);

        ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards = game.getCurrentPlayer().getLeadercards();
        for(LeaderCard lc: leaderCards){
            dummyLeaderCards.add(lc.getDummy());
        }
        virtualView.update(new Message(MessageType.DUMMY_LEADER_CARD,gson.toJson(dummyLeaderCards)));
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
               dummyDevs[r][c] = game.getDeckDevelopment()[r][c].getCard().getDummy();
           }
       }
           sendAll(new Message(MessageType.DEVELOPMENT_MARKET, gson.toJson(dummyDevs)));

   }


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
            if(!(vv.getNickname().equals(virtualView.getNickname()))) {
                vv.update(message);
            }
        }
    }

    /**
     * sends the updated faithmarker, if there is a vatican report it sends the new faith track to all players
     */
    public void updateFaith(){
        Gson gson = new Gson();
        VirtualView virtualView = connectedClients.get(turnController.getActivePlayer());
        virtualView.update(new Message(MessageType.FAITH_MOVE,gson.toJson(game.getCurrentPlayer().getPlayerBoard().getFaithMarker())));
         if(game.checkPopeSpace()){
             sendUpdateFaithTrack();
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
                        pay(ids);
                    break;
                case BUY_MARKET:
                    turnPhase = TurnPhase.BUY_MARKET;
                    String[] choice = gson.fromJson(message.getPayload(), String[].class);
                    if(choice[0].equalsIgnoreCase("row")){
                        getFromMarketRow(Integer.parseInt(choice[1]));
                    }else{
                        getFromMarketCol(Integer.parseInt(choice[1]));
                    }
                    break;
                case WHITE_MARBLES:
                    int[] marb = gson.fromJson(message.getPayload(), int[].class);
                    chooseWhiteMarbleEffect(marb);
                    break;
                case DEPOTS:
                case DUMMY_STRONGBOX:
                case MOVE_RESOURCES:
                    moveResourcesHandler(message,virtualView);
                    break;
                case ACTIVATE_PRODUCTION:
                    turnPhase = TurnPhase.ACTIVATE_PRODUCTION;
                    addProductionPower(gson.fromJson(message.getPayload(),int[].class));
                    break;
                case EXTRA_PRODUCTION:
                    String[] command = gson.fromJson(message.getPayload(),String[].class);
                    int id = Integer.parseInt(command[0]);
                    Resource resource = new Resource(ResourceType.valueOf(command[1]));
                    addExtraProductionPower(id, resource);
                    break;
                case BASE_PRODUCTION:
                  command = gson.fromJson(message.getPayload(),String[].class);
                  Resource res1 = new Resource(ResourceType.valueOf(command[0]));
                  Resource res2 = new Resource(ResourceType.valueOf(command[1]));
                  Resource res3 = new Resource(ResourceType.valueOf(command[2]));
                  addBasicProduction(res1,res2,res3);
                  break;

                case ACTIVATE_LEADER_CARD:
                    id = gson.fromJson(message.getPayload(),int.class);
                    activateLeaderCard(id);
                break;
                case END_TURN:

            }
        }else{
            virtualView.sendInvalidActionMessage();
        }
    }

    /**
     * handles the messages for reorganizing depots and strongbox
     * @param message
     * @param virtualView
     */
    public void moveResourcesHandler(Message message, VirtualView virtualView){
        Gson gson = new Gson();
        switch(message.getCode()){
            case DEPOTS:
                virtualView.setTempDepots(new ArrayList<>(Arrays.asList(gson.fromJson(message.getPayload(), Depot[].class))));
                break;
            case DUMMY_STRONGBOX:
                virtualView.setTempStrongBox(gson.fromJson(message.getPayload(),StrongBox.class));
                break;
            case MOVE_RESOURCES:
                 changeDepotsState();
                 sendDepots();
                 sendStrongBox();
                 break;

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
                        game.getCurrentPlayer().getPlayerBoard().addUnplacedResource(resource);
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
                case END_TURN:
                    turnController.nextTurn();
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
            turnController.doneGameAction();
            turnPhase = TurnPhase.BUY_DEV;
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
           sendUpdateMarketDev();
           sendDummyDevs();
           success = true;
       } catch (CannotAdd cannotAdd) {
           virtualView.update(new ErrorMessage(cannotAdd.getMessage()));
           success = false;
       }
       if(success){
           virtualView.update(new OkMessage("Card has been placed successfully"));
           virtualView.removeFreeDevelopment(0);
       }

   }

    /**
     * removes from warehouse and strongbox all the resources used to pay a development card
     * @param ids ids of the depot where he need to take the resources, -1 if it's the strongbox
     */
   public void pay(int[] ids){
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
       }
       sendDepots();
       sendStrongBox();
       virtualView.update(new OkMessage("Payed successfully!"));
   }

    /**
     * if the player has some resources that has not be placed yet , he have to choose were to put them
     * before proceeding with the turn
     */
    public void placeResources(){
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
            if (gamePhase == GamePhase.FIRST_ROUND || turnPhase == TurnPhase.BUY_MARKET) {
                virtualView.update(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(game.getCurrentPlayer().getPlayerBoard().getUnplacedResources())));
            } else {
                virtualView.update(new Message(MessageType.PLACE_RESOURCE_WHEREVER, gson.toJson(game.getCurrentPlayer().getPlayerBoard().getUnplacedResources())));
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
        for (int j : id) {
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
                    game.getCurrentPlayer().getDepotById(j).addResource(game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(0));
                }
            }
            game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().remove(0);
            sendDepots();
            sendStrongBox();
        }
        if(gamePhase == GamePhase.FIRST_ROUND){
            game.nextPlayer();
            turnController.nextTurn();
        }
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
            virtualView.update(new Message(MessageType.WHITE_MARBLES,""));
        }else{
            virtualView.removeAllFreeMarbles();
            placeResources();
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
            placeResources();
        }
    }

    /**
     * if the playes has some white marbles and has some white marble effect, the effect is applied
     * @param marb array of marble effect the player wants to apply
     */
    public void chooseWhiteMarbleEffect(int[ ] marb){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for(int i = 0; i < marb.length; i++){
            Resource resource = game.getCurrentPlayer().getPossibleWhiteMarbles().get(marb[i]);
            virtualView.getFreeMarble().get(i).setMarbleEffect(playerBoard -> {
                playerBoard.addUnplacedResource(resource);
            });
            virtualView.getFreeMarble().get(i).getMarbleEffect().giveResourceTo(game.getCurrentPlayer().getPlayerBoard());
        }
        virtualView.removeAllFreeMarbles();
        placeResources();
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
    public void changeDepotsState(){
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for(Depot d: virtualView.getTempDepots()){
            Depot toChange = game.getCurrentPlayer().getDepotById(d.getId());
            toChange.setDepot(d.getDepot());
        }
        if(!virtualView.getTempStrongBox().getRes().isEmpty()){
            game.getCurrentPlayer().getPlayerBoard().getStrongBox().setStrongbox(virtualView.getTempStrongBox().getRes());
        }
        virtualView.freeStrongBox();
        virtualView.freeTempDepots();
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
        ArrayList<Resource> toPay = new ArrayList<>();
        toPay.add(res1);
        toPay.add(res2);
        virtualView.addAllResourcesToPay(toPay);
        virtualView.setBasicProd(res3);
    }

    /**
     * after the player pay , the production start and all the resources are added to strongbox
     * and the faithPoint are added to the fait marker
     */
    public void startProduction(){
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
    public  void activateLeaderCard(int id) {
        game.getCurrentPlayer().getLeaderCardById(id).active(game.getCurrentPlayer(), game.getCurrentPlayer().getPlayerBoard());
        sendDummyLead();
        sendExtraProduction();
        sendWhiteMarbles();
        sendDepots();
        sendDiscountedRes();
    }

    /**
     * tells the players if they're winners or losers
     */
    public abstract void endGame();
}





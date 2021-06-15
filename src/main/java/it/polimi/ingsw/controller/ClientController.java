package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.observers.ViewObserver;
import it.polimi.ingsw.utility.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO  thread per leggere int e stringhe + metodo per aspettare OK O ERROR


/**
 * on the clients side controls input of the client and send them to the server
 * reads messages from server and pass them to the client
 * @author Alice Cariboni, Sofia Canestraci
 */
public class ClientController implements ViewObserver,Observer {
    private final View view;
    Gson gson = new Gson();
    private SocketClient client;
    private String nickname;
    private final ExecutorService executionQueue;
    private GamePhase gamePhase;


    public ClientController(View view) {
        this.view = view;
        executionQueue = Executors.newSingleThreadExecutor();
    }


    /**
     * tries to create a new connection with server
     *
     * @param ip
     * @param port
     */
    @Override
    public void onConnectionRequest(String ip, int port) {
        client = new SocketClient(ip, port);
        client.addObserver(this);
       // client.enablePing(true);

        try {
            client.readMessage();
        } catch (IOException e) {
            System.out.println("Server disconnected");
        }
        view.askNickname();

        gamePhase = GamePhase.INIT;
    }

    /**
     * sends the new nickname to the server to see if it is an available nickname
     *
     * @param setupMessage
     */
    @Override
    public void onUpdateNickname(SetupMessage setupMessage) {
        this.nickname = setupMessage.getPayload();
        System.out.println("Nickname request sent!..");
        client.sendMessage(setupMessage);
    }


    /**
     * sends a generic message to server
     *
     * @param message
     */
    @Override
    public void onReadyReply(Message message) {
        client.sendMessage(message);
    }

    @Override
    public void setTurnPhase(TurnPhase turnPhase) {
        view.setTurnPhase(turnPhase);
    }


    /**
     * takes decision and makes action based on the message code arrived from server
     *
     * @param line arrived from server
     */
    @Override
    public void update(String line) {
        Message message = gson.fromJson(line, Message.class);
        Gson gson = new Gson();
        switch (message.getCode()) {
            case NUMBER_OF_PLAYERS:
                executionQueue.execute(this.view::askNumberOfPlayers);

                break;
            case GENERIC_MESSAGE:
            case WINNER:
            case LOSER:
                 executionQueue.execute(() -> view.showGenericMessage(message.getPayload()));
                break;

            case INVALID_NICKNAME:
                executionQueue.execute(() -> view.askNickname());
                break;

            case DUMMY_LEADER_CARD:
                DummyLeader dummyLeader = gson.fromJson(line, DummyLeader.class);
                executionQueue.execute(() -> view.dummyLeaderCardIn(dummyLeader.getDummyLeader()));
                break;

            case FAITH_TRACK:
                FaithTrackMessage faithTrackMessage = gson.fromJson(line, FaithTrackMessage.class);
                executionQueue.execute(() -> view.faithTrackNew(faithTrackMessage.getFaithTrack()));
                break;

            case DEVELOPMENT_MARKET:
                DevelopmentMarket developmentMarket = gson.fromJson(line, DevelopmentMarket.class);
                executionQueue.execute(() -> view.devMarketNew(developmentMarket.getDummyDevs()));
                break;

            case MARKET_TRAY:
                 MarketTrayMessage marketTrayMessage = gson.fromJson(line, MarketTrayMessage.class);
                executionQueue.execute(() -> view.marketTrayNew(marketTrayMessage.getMarket()));
                break;

            case NOTIFY_TURN:
                executionQueue.execute(view::yourTurn);
                break;

            case CHOOSE_RESOURCES:
                ResourceRequest resourceRequest = gson.fromJson(line, ResourceRequest.class);
                int quantity = resourceRequest.getNum();
                executionQueue.execute(() -> view.chooseResources(quantity));
                break;

            case OK:
            case ERROR:
                executionQueue.execute(() -> view.showGenericMessage(message.getPayload()));
                break;

            case PLACE_RESOURCE_WAREHOUSE:
                String[] resource = gson.fromJson(message.getPayload(),String[].class);
                executionQueue.execute(() -> view.addResourceToWareHouse(resource));
                break;

            case FAITH_MOVE:
                FaithMove faithMove = gson.fromJson(line, FaithMove.class);
                executionQueue.execute(() -> view.modifyFaithMarker(faithMove.getPos()));
                break;

            case RESOURCE_PAYMENT:
                resource  = gson.fromJson(message.getPayload(), String[].class);
                if(view.getTurnPhase() == TurnPhase.BUY_DEV) {
                    executionQueue.execute(() -> view.payResources(resource));
                }else{
                    executionQueue.execute(() -> view.activateProduction(resource));
                }
                break;

            case SLOT_CHOICE:
                executionQueue.execute(view::slotChoice);
                break;

            case WHITE_MARBLES:
                int numWhite = gson.fromJson(message.getPayload(), int.class);
                executionQueue.execute(() -> view.askWhiteMarble(numWhite));
                break;

            case DUMMY_STRONGBOX:
                DummyStrongBox dummyStrongbox = gson.fromJson(line, DummyStrongBox.class);
                executionQueue.execute(() -> view.newDummyStrongBox(dummyStrongbox.getStrongBox()));
                break;


            case DUMMY_DEVS:
                DevelopmentSlots developmentSlots = gson.fromJson(line, DevelopmentSlots.class);
                executionQueue.execute(() -> view.addDevCards(developmentSlots.getDummyDevs()));
                break;

            case DEPOTS:
                DepotMessage depotMessage = gson.fromJson(line, DepotMessage.class);
                executionQueue.execute(() -> view.wareHouseNew(depotMessage.getDummyWarehouse()));
                break;

            case END_TURN:
                executionQueue.execute(view::waitTurn);
                break;
            case PONG:
                break;

            case OTHER_DEV_SLOTS:
                DummyDev[] devCards = gson.fromJson(message.getPayload(), DummyDev[].class);
                executionQueue.execute(() -> view.otherDevCards(devCards));
                break;

            case OTHER_FAITHMARKER:
               int pos = gson.fromJson(message.getPayload(), int.class);
                executionQueue.execute(() -> view.otherFaithMarker(pos));
                break;

            case OTHER_STRONGBOX:
                DummyStrongbox dummyStrongbox1 = gson.fromJson(message.getPayload(), DummyStrongbox.class);
                executionQueue.execute(() -> view.otherDummyStrongBox(dummyStrongbox1));
                break;

            case OTHER_WAREHOUSE:
                DummyWareHouse dummyWareHouse = DummyWarehouseConstructor.parse(message.getPayload());
                executionQueue.execute(() -> view.otherWarehouseNew(dummyWareHouse));
                break;

            case OTHER_LEADER:
                DummyLeaderCard[] dummyLeaderCards = gson.fromJson(message.getPayload(), DummyLeaderCard[].class);
                executionQueue.execute(() -> view.otherLeaderCardIn(dummyLeaderCards));
                break;

            case BLACK_CROSS:
                int blackCross = gson.fromJson(message.getPayload(), int.class);
                executionQueue.execute( () -> view.showBlackCross(blackCross));
                break;
            case VICTORY_POINTS:
                int victoryPoints = gson.fromJson(message.getPayload(), int.class);
                executionQueue.execute(() -> view.victoryPointsIn(victoryPoints));
                break;

            case OTHER_VICTORY_POINTS:
                OtherVictoryPoints otherVictoryPoints = gson.fromJson(line, OtherVictoryPoints.class);
                executionQueue.execute(() -> view.otherVictoryPointsIn(otherVictoryPoints.getVictory()));
                break;

            default:
                System.out.println("Error reading from server");
                executionQueue.execute(() -> client.disconnect());
                break;

        }

    }

    @Override
    public void update(Message message) {

    }
}


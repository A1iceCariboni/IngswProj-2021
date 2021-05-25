package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.CliObserver;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.utility.DummyWarehouseConstructor;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO  thread per leggere int e stringhe + metodo per aspettare OK O ERROR


/**
 * on the clients side controls input of the client and send them to the server
 * reads messages from server and pass them to the client
 * @author Alice Cariboni, Sofia Canestraci
 */
public class ClientController implements CliObserver,Observer {
    private final Cli cli;

    private SocketClient client;
    private String nickname;
    private final ExecutorService executionQueue;
    private GamePhase gamePhase;


    public ClientController(Cli cli) {
        this.cli = cli;
        this.executionQueue = Executors.newSingleThreadExecutor();
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
        //client.enablePing(true);

        try {
            client.readMessage();
        } catch (IOException e) {
            System.out.println("Server disconnected");
        }
        cli.askNickname();

        this.gamePhase = GamePhase.INIT;
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

    /**
     * prints a generic message from server
     *
     * @param message
     */
    public void printMessage(String message) {
        System.out.println(message);
    }


    /**
     * takes decision and makes action based on the message code arrived from server
     *
     * @param message arrived from server
     */
    @Override
    public void update(Message message) {
        Gson gson = new Gson();

        switch (message.getCode()) {
            case NUMBER_OF_PLAYERS:
                printMessage(message.getPayload());
                executionQueue.execute(cli::askNumberOfPlayers);

                break;
            case GENERIC_MESSAGE:
            case WINNER:
            case LOSER:
                executionQueue.execute(() -> printMessage(message.getPayload()));
                break;

            case INVALID_NICKNAME:
                executionQueue.execute(() -> cli.askNickname());
                break;

            case DUMMY_LEADER_CARD:
                DummyLeaderCard[] dummyLeaderCards = gson.fromJson(message.getPayload(), DummyLeaderCard[].class);
                executionQueue.execute(() -> cli.DummyLeaderCardIn(dummyLeaderCards));
                break;

            case FAITH_TRACK:
                DummyFaithTrack dummyFaithTrack = gson.fromJson(message.getPayload(), DummyFaithTrack.class);
                executionQueue.execute(() -> cli.faithTrackNew(dummyFaithTrack));
                break;

            case DEVELOPMENT_MARKET:
                DummyDev[][] dummyDevs = gson.fromJson(message.getPayload(), DummyDev[][].class);
                executionQueue.execute(() -> cli.devMarketNew(dummyDevs));
                break;

            case MARKET_TRAY:
                DummyMarket dummyMarket = gson.fromJson(message.getPayload(), DummyMarket.class);
                executionQueue.execute(() -> cli.marketTrayNew(dummyMarket));
                break;

            case NOTIFY_TURN:
                cli.setTurnPhase(TurnPhase.FREE);
                executionQueue.execute(cli::yourTurn);
                break;

            case CHOOSE_RESOURCES:
                int quantity = gson.fromJson(message.getPayload(), int.class);
                executionQueue.execute(() -> cli.chooseResources(quantity));
                break;

            case OK:
            case ERROR:
                executionQueue.execute(() -> cli.checkResponse(message.getCode().name()));
                break;

            case PLACE_RESOURCE_WAREHOUSE:
                String[] resource = gson.fromJson(message.getPayload(),String[].class);
                executionQueue.execute(() -> cli.addResourceToWareHouse(resource));
                break;

            case FAITH_MOVE:
                int pos = gson.fromJson(message.getPayload(), int.class);
                executionQueue.execute(() -> cli.modifyFaithMarker(pos));
                break;

            case RESOURCE_PAYMENT:
                resource  = gson.fromJson(message.getPayload(), String[].class);
                if(cli.getTurnPhase() == TurnPhase.BUY_DEV) {
                    executionQueue.execute(() -> cli.payResources(resource));
                }else{
                    executionQueue.execute(() -> cli.activateProduction(resource));
                }
                break;

            case SLOT_CHOICE:
                executionQueue.execute(cli::slotChoice);
                break;

            case WHITE_MARBLES:
                int numWhite = gson.fromJson(message.getPayload(), int.class);
                executionQueue.execute(() -> cli.askWhiteMarble(numWhite));
                break;

            case DUMMY_STRONGBOX:
                DummyStrongbox dummyStrongbox = gson.fromJson(message.getPayload(), DummyStrongbox.class);
                executionQueue.execute(() -> cli.newDummyStrongBox(dummyStrongbox));
                break;


            case DUMMY_DEVS:
                DummyDev[] devCards = gson.fromJson(message.getPayload(), DummyDev[].class);
                executionQueue.execute(() -> cli.addDevCards(devCards));
                break;

            case DEPOTS:
                DummyWareHouse dummyWareHouse = DummyWarehouseConstructor.parse(message.getPayload());
                executionQueue.execute(() -> cli.wareHouseNew(dummyWareHouse));
                break;

            case END_TURN:
                //executionQueue.execute(cli::waitTurn);
                break;

            default:
                System.out.println("Error reading from server");
                executionQueue.execute(() -> client.disconnect());
                break;

        }

    }
}


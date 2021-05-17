package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.CliObserver;
import it.polimi.ingsw.observers.Observer;

//TODO  thread per leggere int e stringhe + metodo per aspettare OK O ERROR

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * on the clients side controls input of the client and send them to the server
 * reads messages from server and pass them to the client
 * @author Alice Cariboni, Sofia Canestraci
 */
public class ClientController implements CliObserver,Observer {
    private final Cli cli;

    private SocketClient client;
    private String nickname;
    private ExecutorService executionQueue;
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
                cli.addResourceToWareHouse(resource);
                break;

            /*





            case ACTIVATE_LEADER_CARD:
                int[] dummyLeaderCardsId = gson.fromJson(message.getPayload(), int[].class);
                cli.activateLeaderCard(dummyLeaderCardsId);
                break;


            case PLACE_RESOURCE_WHEREVER:
                String res = gson.fromJson(message.getPayload(),String.class);
                cli.addResourceWherever(res);//uguale a prima ma puoi aggiungere anche a strongbox, se aggiunge in strongbox
                //rispondi con un messaggio dello stesso tipo ma
                //se mette in strongbox usa come id 0
                //inserire -1 per scartare
                break;
            case FAITH_MOVE:
                int pos = gson.fromJson(message.getPayload(), int.class);
                cli.faithMove(pos);//ti dice di quante posizioni ha spostato la pedina
                break;

            case WHITE_MARBLES:
                int numWhite = gson.fromJson(message.getPayload(), int.class);
                cli.askWhiteMarble(numWhite);//chiede al player di dire quali poteri white marble vuole usare per ogni white marble, deve dare un array di numeri
                //e i numeri devono essere lo stesso numero delle white marble pescate che sono nel payload del messaggio
                //se vuole usare un potere più volte lo deve scrivere più volte
                //la risposta deve avere lo stesso codice WHITE_MARBLE
                break;
            case DEPOTS:
                DummyDepot[] depots = gson.fromJson(message.getPayload(), DummyDepot[].class);
                cli.allDepots(depots);//contiene un arraylist di tutti i depot, sia extra che non
                break;
            case DUMMY_STRONGBOX:
                DummyStrongbox dummyStrongbox = gson.fromJson(message.getPayload(), DummyStrongbox.class);
                cli.newDummyStrongBox(dummyStrongbox);//contiene un dummy strongbox
                break;
            //se il player vuole muovere le risorse (e lo puo fare in qualsiasi momento)
            // manda un messaggio di tipo depot con tutti i depot che vuole modificare e come li vuole modificare
            // poi un messaggio di tipo DUMMY_STRONGBOX se vuole modificare anche strongbox e quando è pronto
            //inivia un messaggio di tipo MOVE_RESOURCES vuoto, tutti i controlli li faccio io ner server
            case WHITE_MARBLES_POWERS:
                String[] whiteMarbles = gson.fromJson(message.getPayload(), String[].class);// è UN ARRAY di stringhe che ti dice quali white marbles
                cli.addWhiteMarblesPower(whiteMarbles);// hai per quando poi devi inviare il messaggio WHITE_MARBLES
                break;//potrebbe essere vuoto se non ne hai
            case EXTRA_PRODUCTION:
                DummyExtraProduction[] extraProd= gson.fromJson(message.getPayload(), DummyExtraProduction[].class);
                cli.addExtraProduction(extraProd);// è un array di dummy extra production
                break;//potrebbe essere vuoto se non ne hai
            case DISCOUNTED_RESOURCES:
                String[] discounts = gson.fromJson(message.getPayload(), String[].class);
                cli.addDiscountedResources(discounts);//array di stringhe che dice quali risorse hai scontate
                break;//potrebbe essere vuoto se non ne hai
            case DUMMY_DEVS:
                DummyDev[] devCards = gson.fromJson(message.getPayload(), DummyDev[].class);
                cli.addDevCards(devCards);
                break;//è un array delle tue 3 development card sulla playerboard
            case REMOVE_RESOURCES:
                cli.removeResources();
            */
            default: break;

        }




    }
}


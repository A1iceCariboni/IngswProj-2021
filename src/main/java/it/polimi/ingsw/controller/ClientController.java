package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyFaithTrack;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.observers.CliObserver;
import it.polimi.ingsw.observers.Observer;

/**
 * on the clients side controls input of the client and send them to the server
 * reads messages from server and pass them to the client
 * @author Alice Cariboni
 */
public class ClientController implements CliObserver,Observer {
    private final Cli cli;

    private SocketClient client;
    private String nickname;


    public ClientController(Cli cli) {
        this.cli = cli;
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
        client.readMessage();
        client.enablePing(true);
        cli.askNickname();
    }

    /**
     * sends the new nickname to the server to see if it is an available nickname
     *
     * @param setupMessage
     */
    @Override
    public void onUpdateNickname(SetupMessage setupMessage) {
        this.nickname = setupMessage.getPayload();
        client.sendMessage(setupMessage);
        System.out.println("Nickname request sent!..");
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
                cli.askNumberOfPlayers();
                break;
            case INVALID_NICKNAME:
                cli.askNickname();
                break;
            case DUMMY_LEADER_CARD:
                DummyLeaderCard[] dummyLeaderCards = gson.fromJson(message.getPayload(), DummyLeaderCard[].class);
                //qui fai operazioni tipo aggiungere alla dummyplayerboard
                //queste te le mando solo all'inizio, poi se il giocatore decide di scartarle tu le elimini quando ti arriva il messaggio di ok
                //e se vuole attivarne una modifichi il campo quando ti arriva il messaggio di ok
                break;
            case FAITH_TRACK:
                DummyFaithTrack dummyFaithTrack = gson.fromJson(message.getPayload(), DummyFaithTrack.class);
                //qui fai ciò che devi, te ne mando uno all'inizio del gioco e poi ogni volta che una pedina si muove
                break;
            case OK:
            case ERROR:
                printMessage(message.getPayload());
                //questo semplicemente stampa i messaggi di ok ed errore vedi tu se devi fare qualcos'altro
                break;
            case DEVELOPMENT_MARKET:
                DummyDev[][] dummyDevs = gson.fromJson(message.getPayload(), DummyDev[][].class);
                //queste te le mando all'inizio e ogni volta che un giocatore pesca una carta
                break;
            case MARKET_TRAY:
                DummyMarket dummyMarket = gson.fromJson(message.getPayload(), DummyMarket.class);
                //stessa storia
                break;
        }


    }
}

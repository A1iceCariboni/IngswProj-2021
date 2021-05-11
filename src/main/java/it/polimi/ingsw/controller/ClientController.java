package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyFaithTrack;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.SetupMessage;
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
                printMessage(message.getPayload());
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
            case DISCARD_LEADER:
                printMessage(message.getPayload());
                //devi far scartare 2 carte leader tra quelle che ha quindi gliele stampi, gliele fai vedere
                //gli fai scegliere per id quelle che vuole e poi mandi al server
                //il messaggio che mandi indietro nel payload deve avere un array di id
                //quindi tipo
                //int[] id_da_scartare = new int[];
                //Message = new Message(MessageType.DISCARD_LEADER,gson.toJson(id_da_scartare))
                //e poi lo invii
                break;
            case CHOOSE_RESOURCES:
                int quantity = gson.fromJson(message.getPayload(), int.class);
                //semplicememnte chiede al player di scegliere le risorse iniziali, però
                //può essere usato anche in altre fasi del gioco tipo quando si attiva la produzione base
                //il giocatore deve scegliere le risorse e poi me le devi mandare indietro sotto forma di array di stringhe
                //nel payload c'è il numero di risorse che deve scegliere, quindi controlla che la dimensione dell'array sia giusta
                //rispondi sempre con un messaggio del tipo CHOOSE_RESOURCES
                break;
            case PLACE_RESOURCE_WAREHOUSE:
                String resource = gson.fromJson(message.getPayload(),String.class);
                //chiede al player di scegliere un posto nel warehouse dove piazzare questa risorsa
                //rispondi con un messaggio dello stesso tipo che ha come payload un'array degli id dello scaffale per ogni risorsa in ordine
                //conta anche quelli extra se ce ne sono
                //inserire -1 per scartare
                break;
            case PLACE_RESOURCE_WHEREVER:
                String res = gson.fromJson(message.getPayload(),String.class);
                //uguale a prima ma puoi aggiungere anche a strongbox, se aggiunge in strongbox
                //rispondi con un messaggio dello stesso tipo ma
                //se mette in strongbox usa come id 0
                //inserire -1 per scartare
                break;
            case FAITH_MOVE:
                int pos = gson.fromJson(message.getPayload(), int.class);
                //ti dice di quante posizioni ha spostato la pedina
                break;
            case NOTIFY_TURN:
                //ti dice che è il tuo turno
                //devi far scegliere l'azione da fare tra le 3 possibili e poi mi mandi un messaggio tra
                //1. codice BUY_DEV e nel payload la riga e la colonna nella matrice di carte come array
                // subito dopo mi mandi un messaggio RESOURCE_PAYMENT con gli id degli scaffali o -1 per strongbox dove devo andare a prendere le risorse per pagare
                // e poi un messaggio di tipo SLOT_CHOICE con il numero( da 0 a 2) dello slot in cui lo vuole mettere
                //2. codice BUY_MARKET e nel payload riga e colonna del market
                //3. codice ACTIVATE_PRODUCTION e nel payload gli id delle dev card che vuole attivare
                break;

        }


    }
}

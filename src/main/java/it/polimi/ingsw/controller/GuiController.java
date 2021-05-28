package it.polimi.ingsw.controller;

import com.google.gson.Gson;

import it.polimi.ingsw.Gui.Gui;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.*;
import it.polimi.ingsw.utility.DummyWarehouseConstructor;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import it.polimi.ingsw.observers.Observer;

/**
     * on the clients side controls input of the client and send them to the server
     * reads messages from server and pass them to the client
     * @author Alice Cariboni, Sofia Canestraci
     */
    public class GuiController implements GuiObserver, Observer{
        private final Gui gui;

        private SocketClient client;
        private String nickname;
        private ExecutorService executionQueue;
        private GamePhase gamePhase;


        public GuiController(Gui gui) {
            this.gui = gui;
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
            gui.askNickname();

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
                    executionQueue.execute(gui::askNumberOfPlayers);

                    break;
                case GENERIC_MESSAGE:
                case WINNER:
                case LOSER:
                    executionQueue.execute(() -> printMessage(message.getPayload()));
                    break;

                case INVALID_NICKNAME:
                    executionQueue.execute(() -> { gui.askNickname();
                    });
                    break;

                case DUMMY_LEADER_CARD:
                    DummyLeaderCard[] dummyLeaderCards = gson.fromJson(message.getPayload(), DummyLeaderCard[].class);
                    executionQueue.execute(() -> gui.DummyLeaderCardIn(dummyLeaderCards));
                    break;

                case FAITH_TRACK:
                    DummyFaithTrack dummyFaithTrack = gson.fromJson(message.getPayload(), DummyFaithTrack.class);
                    executionQueue.execute(() -> gui.faithTrackNew(dummyFaithTrack));
                    break;

                case DEVELOPMENT_MARKET:
                    DummyDev[][] dummyDevs = gson.fromJson(message.getPayload(), DummyDev[][].class);
                    executionQueue.execute(() -> gui.devMarketNew(dummyDevs));
                    break;

                case MARKET_TRAY:
                    DummyMarket dummyMarket = gson.fromJson(message.getPayload(), DummyMarket.class);
                    executionQueue.execute(() -> gui.marketTrayNew(dummyMarket));
                    break;

                case NOTIFY_TURN:
                    gui.setTurnPhase(TurnPhase.FREE);
                    executionQueue.execute(gui::yourTurn);
                    break;

                case CHOOSE_RESOURCES:
                    int quantity = gson.fromJson(message.getPayload(), int.class);
                    executionQueue.execute(() -> gui.chooseResources(quantity));
                    break;

                case OK:
                case ERROR:
                    executionQueue.execute(() -> gui.checkResponse(message.getCode().name()));
                    break;

                case PLACE_RESOURCE_WAREHOUSE:
                    String[] resource = gson.fromJson(message.getPayload(),String[].class);
                    executionQueue.execute(() -> gui.addResourceToWareHouse(resource));
                    break;

                case FAITH_MOVE:
                    int pos = gson.fromJson(message.getPayload(), int.class);
                    executionQueue.execute(() -> gui.modifyFaithMarker(pos));
                    break;

                case RESOURCE_PAYMENT:
                    resource  = gson.fromJson(message.getPayload(), String[].class);
                    if(gui.getTurnPhase() == TurnPhase.BUY_DEV) {
                        executionQueue.execute(() -> gui.payResources(resource));
                    }else{
                        executionQueue.execute(() -> gui.activateProduction(resource));
                    }
                    break;

                case SLOT_CHOICE:
                    executionQueue.execute(gui::slotChoice);
                    break;

                case WHITE_MARBLES:
                    int numWhite = gson.fromJson(message.getPayload(), int.class);
                    executionQueue.execute(() -> gui.askWhiteMarble(numWhite));
                    break;

                case DUMMY_STRONGBOX:
                    DummyStrongbox dummyStrongbox = gson.fromJson(message.getPayload(), DummyStrongbox.class);
                    executionQueue.execute(() -> gui.newDummyStrongBox(dummyStrongbox));
                    break;


                case DUMMY_DEVS:
                    DummyDev[] devCards = gson.fromJson(message.getPayload(), DummyDev[].class);
                    executionQueue.execute(() -> gui.addDevCards(devCards));
                    break;

                case DEPOTS:
                    DummyWareHouse dummyWareHouse = DummyWarehouseConstructor.parse(message.getPayload());
                    executionQueue.execute(() -> gui.wareHouseNew(dummyWareHouse));
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

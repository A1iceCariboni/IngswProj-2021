package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.MultiGameController;
import it.polimi.ingsw.controller.SingleGameController;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.NumberOfPlayerRequest;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Server class starts a socket server
 * @author Alice Cariboni
 */
public class Server {
    private final Map<ClientHandler, String> clients;
    private final List<ClientHandler> waiting;
    private GameController gameController;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private Map<String, VirtualView> virtualClients;


    public Server() {
        this.clients = new HashMap<>();
        this.waiting = new ArrayList<>();
        this.virtualClients = new HashMap<>();
    }

    /**
     * add a new client to the game if the nickname is not already taken
     *
     * @param nickname      the nickname the player want to use
     * @param clientHandler the client handler of thr client
     */
    public synchronized void addClient(String nickname, ClientHandler clientHandler) {
        VirtualView virtualView = new VirtualView(clientHandler, nickname);
        boolean ok = true;
        if (waiting.isEmpty()) {
            clients.put(clientHandler, nickname);
            LOGGER.info("Client " + nickname + " registered" + clientHandler);
            virtualClients.put(nickname, virtualView);
        } else {
            if (clients.get(nickname) != null) {
                virtualView.nickNameResult(false, false, false);
                ok = false;
            } else {
                clients.put(clientHandler, nickname);
                virtualClients.put(nickname, virtualView);
                gameController.addConnectedClient(nickname, virtualView);
                gameController.addPlayer(nickname);

                virtualView.nickNameResult(true, false, false);
                LOGGER.info("Client " + nickname + " registered" + clientHandler);

            }
        }
        if (ok) {
            LOGGER.info("Client " + nickname + " entered the lobby");
            lobby(clientHandler);
        }

    }


    /**
     * handles the new players connected to server,
     * if it's the first player sends a requesto for the number of player
     * if it's not the player is added to the waiting kist if there is still place in the game, if not the client is notified that the server is full
     *
     * @param clientHandler of the client who has entered the lobby
     */
    public synchronized void lobby(ClientHandler clientHandler) {

        waiting.add(clientHandler);
        LOGGER.info("Size of lobby " + waiting.size());

        if (waiting.size() == 1) {
            clientHandler.askPlayersNumber(new NumberOfPlayerRequest("You're logged in and you're the first one, choose the number of player from 1 [for the solo game] to 4 "));
        }
        if (waiting.size() == gameController.getNumberOfPlayers()) {
            gameController.sendAll(new OkMessage("Game is starting!"));
            gameController.setStarted(true);
            gameController.startGame();
        } else {
            if (gameController.isStarted()) {
                clientHandler.sendMessage(new ErrorMessage("The game is already started, try again later"));
            } else {
                int missingPlayers = gameController.getNumberOfPlayers() - waiting.size();
                gameController.sendAll(new OkMessage("Waiting for other " + missingPlayers + " players"));
            }
        }

    }

    /**
     * create a new game based on the number of player requested by the first player
     *
     * @param numberOfPlayers number of players requested by the first player
     */

    public void createGame(int numberOfPlayers, ClientHandler clientHandler) {
        if (numberOfPlayers == 1) {
            SingleGameController singleGameController = new SingleGameController();
            singleGameController.setNumberOfPlayers(numberOfPlayers);
            singleGameController.addAllConnectedClients(virtualClients);
            singleGameController.addPlayer(clients.get(clientHandler));
            this.gameController = singleGameController;
        } else {
            MultiGameController multiGameController = new MultiGameController();

            multiGameController.setNumberOfPlayers(numberOfPlayers);

            multiGameController.addAllConnectedClients(virtualClients);

            multiGameController.addPlayer(clients.get(clientHandler));

            this.gameController = multiGameController;

        }
        LOGGER.info("Created game");
    }


    public void onMessageReceived(Message message, ClientHandler clientHandler) {
        Gson gson = new Gson();

        String nickname = clients.get(clientHandler);
        VirtualView vv = virtualClients.get(nickname);
        if (!gameController.getTurnController().getActivePlayer().equals(nickname)) {
            vv.update(new ErrorMessage("It's not your turn"));
        } else {
            switch (message.getCode()) {
                case ACTIVATE_LEADER_CARD:
                    try {
                        gameController.activateLeaderCard(Integer.parseInt(message.getPayload()));
                    } catch (NullCardException ex) {
                        vv.update(new ErrorMessage(ex.getMessage()));
                    }
                    break;
                case DISCARD_LEADER_CARD:
                    try {
                        gameController.discardLeaderCards(gson.fromJson(message.getPayload(), int[].class));
                    } catch (NullCardException e) {
                        vv.update(new ErrorMessage(e.getMessage()));
                    }
                    break;
                case CHOOSE_RESOURCES:
                    ResourceType[] resources = gson.fromJson(message.getPayload(), ResourceType[].class);
                    for (ResourceType resourceType : resources) {
                        Resource resource = new Resource(resourceType);
                        vv.addFreeReource(resource);
                    }
                    gameController.placeResources();
                    break;
                case PLACE_RESOURCE_WAREHOUSE:
                case PLACE_RESEOURCE_WHEREVER:
                    boolean success;
                    int id = gson.fromJson(message.getPayload(), int.class);
                    try {
                        gameController.putResource(id);
                        success = true;
                    } catch (NotPossibleToAdd notPossibleToAdd) {
                        vv.update(new ErrorMessage(notPossibleToAdd.getMessage()));
                        success = false;
                    }
                    if (success) {
                        vv.removeFreeResources(0);
                    }
            }
        }
    }
}



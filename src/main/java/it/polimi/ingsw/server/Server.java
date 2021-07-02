package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.MultiGameController;
import it.polimi.ingsw.controller.SingleGameController;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.utility.Persistence;

import java.io.IOException;
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
    private  Map<ClientHandler, String> clients;
    private  List<ClientHandler> waiting;
    private GameController gameController;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private  Map<String, VirtualView> virtualClients;


    public Server() {
        reset();
    }


    private void reset(){
        this.clients = new HashMap<>();
        this.waiting = new ArrayList<>();
        this.virtualClients = new HashMap<>();
        this.gameController = new GameController();
    }
    /**
     * add a new client to the game if the nickname is not already taken
     *
     * @param nickname      the nickname the player want to use
     * @param clientHandler the client handler of thr client
     */
    public synchronized void addClient(String nickname, ClientHandler clientHandler) throws IOException {
        VirtualView virtualView = new VirtualView(clientHandler, nickname);
        boolean ok = true;
        if (waiting.isEmpty()) {
            LOGGER.info("Client " + nickname + " registered" + clientHandler);
        } else {
            if ((virtualClients.get(nickname) != null)) {
                if(gameController.isStarted() && !gameController.getConnectedClients().containsKey(nickname)){
                    onReconnect(nickname,virtualView);
                    clients.put(clientHandler, nickname);
                    virtualClients.remove(nickname);
                    virtualClients.put(nickname, virtualView);
                }else {
                    virtualView.nickNameResult(false, false, false);
                }
                ok = false;
            } else {
                virtualView.nickNameResult(true, false, false);
                LOGGER.info("Client " + nickname + " registered" + clientHandler);
            }
        }
        if (ok) {
            LOGGER.info("Client " + nickname + " entered the lobby");
            lobby(clientHandler, virtualView);
        }

    }


    /**
     * handles the new players connected to server,
     * if it's the first player sends a requesto for the number of player
     * if it's not the player is added to the waiting kist if there is still place in the game, if not the client is notified that the server is full
     *
     * @param clientHandler of the client who has entered the lobby
     */
    public synchronized void lobby(ClientHandler clientHandler, VirtualView virtualView) throws IOException {

        waiting.add(clientHandler);
        LOGGER.info("Size of lobby " + waiting.size());
        clients.put(clientHandler, virtualView.getNickname());
        virtualClients.put(virtualView.getNickname(), virtualView);

        if (waiting.size() == 1) {
            clientHandler.askPlayersNumber(new NumberOfPlayerRequest("You're logged in and you're the first one, choose the number of player from 1 [for the solo game] to 4 "));
        }
        if (waiting.size() == gameController.getNumberOfPlayers()) {
            gameController.addConnectedClient(virtualView.getNickname(), virtualView);
            gameController.addPlayer(virtualView.getNickname());
            Persistence persistence = new Persistence();
            GameController savedGameController = persistence.restore();
            if(savedGameController != null &&
                savedGameController.getNumberOfPlayers() == gameController.getNumberOfPlayers() &&
               savedGameController.getPlayers().containsAll(gameController.getPlayers())){
              savedGameController.addAllConnectedClients(gameController.getConnectedClients());

              gameController = savedGameController;
              gameController.sendRestoredGame();
           }else {
                gameController.sendAll(new Message(MessageType.GENERIC_MESSAGE, "Game is starting!"));
                gameController.setStarted(true);
                gameController.startGame();
            }
         } else {
            if (gameController.isStarted()) {
                clientHandler.sendMessage(new ErrorMessage("The game is already started, try again later"));
                virtualView.update(new Out());
                clientHandler.disconnect();
            } else {
                int missingPlayers = gameController.getNumberOfPlayers() - waiting.size();
                gameController.addConnectedClient(virtualView.getNickname(), virtualView);
                gameController.addPlayer(virtualView.getNickname());
                gameController.sendAll(new Message(MessageType.GENERIC_MESSAGE,"Waiting for other " + missingPlayers + " players"));
            }
        }
    }

    /**
     * create a new game based on the number of player requested by the first player
     *
     * @param numberOfPlayers number of players requested by the first player
     */

    public void createGame(int numberOfPlayers) {
        if (numberOfPlayers == 1) {
            SingleGameController singleGameController = new SingleGameController();
            singleGameController.setNumberOfPlayers(numberOfPlayers);
            gameController = singleGameController;
        } else {
            MultiGameController multiGameController = new MultiGameController();
            multiGameController.setNumberOfPlayers(numberOfPlayers);
            gameController = multiGameController;

        }
        LOGGER.info("Created game");
    }


    public void onMessageReceived(String line, ClientHandler clientHandler) {
        if(clients.get(clientHandler) == null){
            System.out.println("Register first, please");
            clientHandler.disconnect();
            return;
        }
      gameController.onMessageReceived(line,clients.get(clientHandler));
    }

    public GameController getGameController() {
        return gameController;
    }

    /**
     * handles a disconnection of a client saving his turn
     * @param clientHandler clientHandler that has disconnected
     */
    public  void onDisconnect(ClientHandler clientHandler){
        if(!gameController.isStarted()){
            unregisterClient(clientHandler);
        }else{
            String name = clients.get(clientHandler);
            clients.remove(clientHandler);
            if(!gameController.getGamePhase().equals(GamePhase.END)) {
                gameController.addDisconnectedClient(name);
                if (gameController.getTurnController().getActivePlayer().equals(name)) {
                    gameController.getTurnController().nextTurn();
                }
            }else{
                if(clients.isEmpty()){
                    reset();
                }
            }
        }
    }

    /**
     * handles the recconection of a client during the game
     * @param nickname of the client who has reconnected
     * @param virtualView of the client who has reconnected
     */
    public void onReconnect(String nickname, VirtualView virtualView){
        gameController.reconnectClient(nickname, virtualView);
    }

    /**
     * if a client disconnects when the game is not started it's deleted
     * @param clientHandler of the player who has disconnect
     */
    public void unregisterClient(ClientHandler clientHandler){
        waiting.remove(clientHandler);
        String name = clients.get(clientHandler);

        virtualClients.remove(name);
        clients.remove(clientHandler);
        if(gameController.getConnectedClients().get(name) != null){
            gameController.removeConnectedClient(name);
            gameController.removePlayer(name);
        }
    }
}



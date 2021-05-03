package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.answer.InvalidNickname;
import it.polimi.ingsw.messages.answer.NumberOfPlayerRequest;
import it.polimi.ingsw.messages.answer.OkMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Server class starts a socket server
 * @author Alice Cariboni
 */
public class Server {
    private final Map<String,ClientHandler> clients;
    private final List<ClientHandler> waiting ;
    private GameController gameController;
    private int totalPlayers;

    public Server() {
        this.clients = new HashMap<>();
        this.waiting = new ArrayList<>();
        this.totalPlayers = -1;
    }

    /**
     * add a new client to the game if the nickname is not already taken and if there is still place in the game
     * @param nickname the nickname the player want to use
     * @param clientHandler the client handler of thr client
     */
    public synchronized void addClient(String nickname , ClientHandler clientHandler){
        if(totalPlayers == -1){
            clients.put(nickname,clientHandler);
            waiting.add(clientHandler);
            totalPlayers ++;
            clientHandler.sendMessage(new NumberOfPlayerRequest("You're the first player, choose the number of players, from 1 [for the solo game] to 4"));
        }else{
            if(clients.get(nickname) != null){
                clientHandler.sendMessage(new InvalidNickname("Nickname already choosen, pick another one"));
            }else{
                clientHandler.sendMessage(new OkMessage("You're in the waiting room wait for others player to start the game"));
            }
        }
    }

}



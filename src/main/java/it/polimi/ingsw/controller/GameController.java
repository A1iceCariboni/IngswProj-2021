package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.util.HashMap;
import java.util.Map;

public abstract class GameController {
    private Game game;
    private Map<String, VirtualView> connectedClients;
    private boolean isStarted;

    private int numberOfPlayers;

    public GameController(){
        this.connectedClients = new HashMap<>();
        isStarted = false;
        numberOfPlayers = 0;
    }

    public abstract void startGame();


    public Map<String, VirtualView> getConnectedClients() {
        return connectedClients;
    }

    public void addConnectedClient(String nickname, VirtualView virtualView) {
        connectedClients.put(nickname,virtualView);
    }

    public void addAllConnectedClients(Map<String, VirtualView> clients){
        this.connectedClients.putAll(clients);
    }

    public void sendAll(Message message){
        for(VirtualView vv: connectedClients.values()){
            vv.update(message);
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
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

    public abstract void onMessageReceived(Message message , String nickname);
}

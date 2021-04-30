package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.SocketClient;

import it.polimi.ingsw.messages.request.ConnectionMessage;
import it.polimi.ingsw.observers.Observer;

import java.util.Scanner;

/**
 * this class controls the evolution of the game
 * @author Alice Cariboni
 */
public class Controller implements Observer {

    /**
     * try to create a new connection between server and client via socket
     * @param message from client containing ip and port for the connection
     */
    @Override
    public void onConnectionRequest(ConnectionMessage message) {
        Scanner in = new Scanner(System.in);
        SocketClient socketClient = new SocketClient(message.getIp(),message.getPort());
        socketClient.readMessage();
        while(true){
            String line = in.nextLine();
            socketClient.sendMessage(line);
        }
    }


}

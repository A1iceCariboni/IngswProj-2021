package it.polimi.ingsw.CLI;

import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.request.ConnectionMessage;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.observers.Observer;

import java.util.ArrayList;
import java.util.Scanner;

public class Cli extends Observable {
    Controller controller;

    public Cli(){
         controller = new Controller();
         addObserver(controller);
         start();
    }
    public void start(){
        Scanner input = new Scanner(System.in);
        System.out.println(">Insert the server IP address");
        System.out.print(">");
        String ip = input.nextLine();
        System.out.println(">Insert the server port");
        System.out.print(">");
        int port = input.nextInt();
        ConnectionMessage message = new ConnectionMessage(ip,port);
        notifyObserver(obs -> obs.onConnectionRequest(message));
    }

    public void askNickname(){

    }

}

package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.util.Scanner;

public class ServerApp {

    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
        System.out.print("What's the port number the server will listen on? >");
        int port = input.nextInt();
        Server server = new Server(port);
        server.startServer();
    }
}

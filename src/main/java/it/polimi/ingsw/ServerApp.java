package it.polimi.ingsw;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketServer;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {

    public static void main(String[] args){
        int port = Constants.DEFAULT_PORT;

        Scanner input = new Scanner(System.in);
        System.out.print("What's the port number the server will listen on? [Press enter for default port 1234] >");
        try {
            port = Integer.parseInt(input.nextLine());
        }catch (NumberFormatException | InputMismatchException ex){
            Server.LOGGER.info("Using default port");
        }
        Server server = new Server();
        SocketServer socketServer = new SocketServer(server, port);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(socketServer);

    }
}

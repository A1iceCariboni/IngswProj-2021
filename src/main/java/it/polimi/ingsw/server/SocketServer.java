package it.polimi.ingsw.server;

import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * handles all the socket connections with clients
 * @author Alice Cariboni
 */
public class SocketServer implements  Runnable{
    private final Server server;
    private final int port;
    private boolean active;
    ExecutorService executor;
    ServerSocket serverSocket;

    public SocketServer(Server server, int port) {
            this.server = server;
            this.port = port;
            this.active = true;
            this.executor = Executors.newCachedThreadPool();
    }


    public void setActive(boolean active){
    this.active = active;}

    /**
     * accept new connections from clients
     * @param serverSocket
     */
    public void acceptConnections(ServerSocket serverSocket){
        while (active){
            try {
                Socket client = serverSocket.accept();
                //client.setSoTimeout(5000);

                ClientHandler clientHandler = new ClientHandler(client, this, server);
                Server.LOGGER.info("Accepted new client "+ client.getInetAddress());
                executor.submit(clientHandler);
            } catch (IOException e) {
                System.err.println("Connection dropped!..");
            }
        }
    }

    /**
     * adds a new client
     * @param nickname nickname the player want to use
     * @param clientHandler client handler of this specific client
     */
    public void addClient(String nickname , ClientHandler clientHandler){
        server.addClient(nickname,clientHandler);
    }




    /**
     * create a new socket listening on the port given in the constructor
     */

    @Override
    public void run() {
        try{
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            acceptConnections(serverSocket);
        }catch(IOException e){
            System.err.println("Error during socket initialization, bye...");
            System.exit(0);
        }
    }

}


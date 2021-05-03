package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * handles a connection between server and client
 * @author Alice Cariboni
 */
public class ClientHandler implements  Runnable{
    private final Socket client;
    private final SocketServer socketServer;

    private boolean connected;

   private Scanner in;
   private PrintWriter out;


    public ClientHandler(Socket client, SocketServer socketServer) {
        this.client = client;
        this.socketServer = socketServer;

        this.connected = true;
        try {
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            readFromClient();
        }
    }

    /**
     * read messages from socket client and manages them
     */
    public synchronized void readFromClient(){
        String line = in.nextLine();
        Gson gson = new Gson();
        Message message = gson.fromJson(line,Message.class);

        if(message != null && message.getCode() != MessageType.PING){
            System.out.println("Message received! " + line);
            if(message.getCode() == MessageType.SETUP){
               socketServer.addClient(message.getPayload(), this);
            }
        }
    }

    /**
     * send a message to the client via socket
     * @param message to be sent
     */
    public synchronized void sendMessage(Message message){
        Gson gson = new Gson();
        out.println(gson.toJson(message));
        out.flush();
        System.out.println("Sent message "+ gson.toJson(message));
    }
}

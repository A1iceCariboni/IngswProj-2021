package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * handles a connection between server and client
 * @author Alice Cariboni
 */
public class ClientHandler implements  Runnable{
    private Server server;
    private  Socket client;
    private  SocketServer socketServer;

    private boolean connected;

   private Scanner in;
   private PrintWriter out;

    /**
     * default constructor
     * @param client the new client connected to the socket
     * @param socketServer
     */
    public ClientHandler(Socket client, SocketServer socketServer, Server server) {
        this.client = client;
        this.server = server;
        this.socketServer = socketServer;

        this.connected = true;
        try {
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientHandler(){

    }
    @Override
    public void run() {
        while (connected) {
            try {
                readFromClient();
            } catch (NullCardException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * read messages from socket client and manages them
     */
    public synchronized void readFromClient() throws NullCardException {
            String line = in.nextLine();
             Server.LOGGER.info("Message received! " + line);
          Gson gson = new Gson();
            try {
                Message message = gson.fromJson(line, Message.class);
                if (message != null && message.getCode() != MessageType.PING) {
                    handleMessage(message);
                    return;
                }
                System.out.println(message);
            }catch(JsonParseException ex){
                System.out.println("what?");
                return;
            }
    }

    /**
     * make decision based on the message code arrived from the client
     * @param message that is received from the client
     */
  public void handleMessage(Message message) {
        if (message.getCode() == MessageType.SETUP) {
            socketServer.addClient(message.getPayload(), this);
        }else{
            server.onMessageReceived(message, this);
        }
  }
    /**
     * send a message to the client via socket
     * @param message to be sent
     */
    public void sendMessage(Message message){
        Gson gson = new Gson();
        out.println(gson.toJson(message));
        out.flush();
        Server.LOGGER.info("Sent message "+ gson.toJson(message));
    }

    /**
     * wait for the first player to send the number of player for the game and create an instace of single or multigame
     * @param message asking the client to give the number of players for the game
     */
    public void askPlayersNumber(Message message){
        sendMessage(message);
            while (true) {
                String line = in.nextLine();
                Gson gson = new Gson();
                Message message1 = gson.fromJson(line, Message.class);

                if (message1.getCode() == MessageType.NUMBER_OF_PLAYERS) {

                    server.createGame(Integer.parseInt(message1.getPayload()),this);
                    break;
                }
            }

    }
}

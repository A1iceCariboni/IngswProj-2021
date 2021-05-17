package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.PongMessage;
import it.polimi.ingsw.messages.request.PingMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * handles a connection between server and client
 * @author Alice Cariboni
 */
public class ClientHandler implements  Runnable{
    private Server server;
    private  Socket client;
    private  SocketServer socketServer;
    private ScheduledExecutorService ponger;

    private Object input;
    private boolean connected;

   private BufferedReader in;
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
        this.input = new Object();
        this.connected = true;
        this.ponger = Executors.newSingleThreadScheduledExecutor();

        try {
            //client.setSoTimeout(5000);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientHandler(){

    }
    @Override
    public void run() {
        try {
                readFromClient();
            } catch (IOException e) {
                Server.LOGGER.info("Client disconnected");
                disconnect();
            }
    }

    /**
     * read messages from socket client and manages them
     */
    public synchronized void readFromClient() throws IOException {

            try {
                while (!Thread.currentThread().isInterrupted()) {
                        String line = in.readLine();
                        Gson gson = new Gson();
                        Message message = gson.fromJson(line, Message.class);
                        if(message == null) throw new IOException();
                        if (message.getCode() != MessageType.PING) {
                            Server.LOGGER.info("Message received! " + line);

                            handleMessage(message);
                        }

                }
            } catch (SocketTimeoutException ex) {
                Server.LOGGER.info("Socket timeout expired");
            }catch (JsonParseException exception){
                Server.LOGGER.info("Invalid format");
            }
            client.close();

    }

    public void disconnect(){
        if(!client.isClosed()){
            try {
                client.close();
                enablePong(false);
            } catch (IOException e) {
                Server.LOGGER.info("couldn't close");
            }
            Thread.currentThread().interrupt();
        }
    }

    /**
     * make decision based on the message code arrived from the client
     * @param message that is received from the client
     */
  public void handleMessage(Message message) throws IOException {
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
        if(message.getCode() != MessageType.PONG)
        Server.LOGGER.info("Sent message "+ gson.toJson(message));
    }

    /**
     * wait for the first player to send the number of player for the game and create an instace of single or multigame
     * @param message asking the client to give the number of players for the game
     */
    public void askPlayersNumber(Message message) throws IOException {
        sendMessage(message);
         //client.setSoTimeout(5000);
                while (true) {

                    String line = in.readLine();
                        Gson gson = new Gson();
                        Message message1 = gson.fromJson(line, Message.class);
                        if(message1 == null) throw new IOException("");
                        if (message1.getCode() == MessageType.NUMBER_OF_PLAYERS) {
                            server.createGame(Integer.parseInt(message1.getPayload()));
                            break;
                        }

                }
    }

    /**
     * thread that send a PongMessage to client every 2000 ms
     * @param enable true enable the ping
     *               false shutdown the ping
     */
    public void enablePong(boolean enable){
        if(enable){
            Message message = new PongMessage();
            this.ponger.scheduleAtFixedRate(()->sendMessage(message),0,2000, TimeUnit.MILLISECONDS);
        }else{
            this.ponger.shutdownNow();
        }
    }


}

package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.PingMessage;
import it.polimi.ingsw.observers.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * socket client implementation
 */
public class SocketClient extends Observable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private  ExecutorService readerExecutor;
    private ScheduledExecutorService pinger;
    boolean active;

    public SocketClient(String ip, int port){
        try {
            this.socket = new Socket(ip,port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.readerExecutor = Executors.newSingleThreadExecutor();
            this.active = true;
            this.pinger = Executors.newSingleThreadScheduledExecutor();
        } catch (UnknownHostException e) {
            System.out.println("Couldn't find hosts IP address...");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error during initialization of client...");
            System.exit(0);
        }
    }

    /**
     * thread that reads messages from server
     */
    public void readMessage(){
      readerExecutor.execute(() -> {
          Gson gson = new Gson();
          String line = null;
              while(!readerExecutor.isShutdown()) {
                  try {
                      line = in.readLine();
                      System.out.println("Received " + line);
                      if(line == null){
                          break;
                      }
                  } catch (IOException e) {
                      System.out.println("Error reading from server");
                      disconnect();
                      readerExecutor.shutdownNow();
                  }
                  notifyObserver( gson.fromJson(line,Message.class));
               }
              disconnect();
              });
    }

    /**
     * sends a message to server
     * @param message to be sent
     */
    public void sendMessage(Message message){
        Gson gson = new Gson();
        out.println(gson.toJson(message));
        out.flush();
    }


    /**
     * disconnects the client closing the socket
     */
    public void disconnect(){
        try{
            if(!socket.isClosed()){
                this.active = false;
                enablePing(false);
                readerExecutor.shutdownNow();
                System.out.println("Closing socket...");
                socket.close();
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Couldn't disconnect client");
        }
    }

    /**
     * thread that send a PingMessage to server every 1000 ms
     * @param enable true enable the ping
     *               false shutdown the ping
     */
    public void enablePing(boolean enable){
        if(enable){
            Message message = new PingMessage();
            this.pinger.scheduleAtFixedRate(()->sendMessage(message),0,1000, TimeUnit.MICROSECONDS);
        }else{
            this.pinger.shutdownNow();
        }
    }





}



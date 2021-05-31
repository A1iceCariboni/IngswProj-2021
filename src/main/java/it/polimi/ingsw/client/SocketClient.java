package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
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
import java.util.logging.Logger;

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
    public static Logger LOGGER = Logger.getLogger(SocketClient.class.getName());

    public SocketClient(String ip, int port){
        try {
            socket = new Socket(ip,port);
            //socket.setSoTimeout(5000);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            readerExecutor = Executors.newSingleThreadExecutor();
            active = true;
            pinger = Executors.newSingleThreadScheduledExecutor();
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
    public void readMessage() throws IOException {
      readerExecutor.execute(() -> {
          Gson gson = new Gson();
                try {
                  do{
                      String line = in.readLine();
                      if(line == null )break;
                      notifyObserver(gson.fromJson(line,Message.class));

                  }while(active) ;


                  } catch (IOException e) {
                      System.out.println("Error reading from server");
                  }
              readerExecutor.shutdownNow();
                disconnect();

      });
    }

    /**
     * sends a message to server
     * @param message to be sent
     */
    public synchronized void sendMessage( Message message){
         Gson gson = new Gson();
        if(message.getCode() != MessageType.PING) {
            System.out.println(gson.toJson(message));
        }
        out.println(gson.toJson(message));
        out.flush();

        if(message.getCode() != MessageType.PING) {
            LOGGER.info("Message sent " + message.getCode());
        }
    }


    /**
     * disconnects the client closing the socket
     */
    public void disconnect(){
        try{
            if(!socket.isClosed()){
                active = false;
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
            pinger.scheduleAtFixedRate(()->sendMessage(message),0,2000, TimeUnit.MILLISECONDS);
        }else{
            pinger.shutdownNow();
        }
    }





}



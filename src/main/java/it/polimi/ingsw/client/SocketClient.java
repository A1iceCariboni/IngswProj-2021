package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private  ExecutorService readerExecutor;



    public SocketClient(String ip, int port){
        try {
            this.socket = new Socket(ip,port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.readerExecutor = Executors.newSingleThreadExecutor();
        }
        catch (UnknownHostException e) {
            System.out.println("Couldn't find hosts IP address...");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error during initialization of client...");
            System.exit(0);
        }
    }
    public void readMessage(){
      readerExecutor.execute(() -> {
          String line = null;
              while(true) {
                  try {
                      if ((line = in.readLine())==null) break;
                  } catch (IOException e) {
                      System.out.println("Error reading from server");
                      disconnect();
                      readerExecutor.shutdownNow();
                  }
                   System.out.println("Received from server "+line);
               }
              disconnect();
              });
    }

    public void sendMessage(String message){
            out.println(message);
    }

    public void disconnect(){
        try{
            if(!socket.isClosed()){
                readerExecutor.shutdownNow();
                System.out.println("Closing socket...");
                socket.close();
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Couldn't disconnect client");
        }
    }






}



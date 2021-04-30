package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }


    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            return;
        }
        System.out.println("Server ready");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client accepted " + socket.getInetAddress().getHostAddress());
                executor.submit(new ServerClientConnection(socket));
            } catch (IOException e) {
                break;
            }
        }
        executor.shutdown();
    }
}



package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;

public class ServerClientConnection implements  Runnable{
        private Socket socket;

    public ServerClientConnection(Socket socket) {
            this.socket = socket; }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    out.println("Closing");
                    out.flush();
                    break;
                } else {
                    out.println(line);
                    out.flush(); }
            }
            in.close();
            out.close();
            System.out.println("Closing socket" + socket.getInetAddress().getHostAddress());
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

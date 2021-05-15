package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

import java.sql.SQLOutput;

public class ClientApp {

    public static void main(String[] args){
        Cli cli = new Cli();
        ClientController clientController = new ClientController(cli);
        cli.addObserver(clientController);
        cli.start();
    }
}

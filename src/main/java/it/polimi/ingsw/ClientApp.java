package it.polimi.ingsw;

import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.controller.ClientController;

public class ClientApp {
    public static void main(String[] args){
        Cli cli = new Cli();
        ClientController clientController = new ClientController(cli);
        cli.addObserver(clientController);
        cli.start();
    }
}

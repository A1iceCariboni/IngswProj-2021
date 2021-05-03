package it.polimi.ingsw.CLI;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.Observable;

import java.util.Scanner;

public class Cli extends Observable {
    private Scanner input;


    public Cli(){
        this.input = new Scanner(System.in);
    }

    public void start(){
        /*System.out.println(">Insert the server IP address");
        System.out.print(">");
        String ip = input.nextLine();
        System.out.println(">Insert the server port");
        System.out.print(">");
        int port = input.nextInt();*/
        int port = Constants.DEFAULT_PORT;
        String ip = Constants.LOCAL_HOST;
        notifyObserver(obs -> obs.onConnectionRequest(ip,port));
    }

    public void askNickname(){
        System.out.println("Insert your nickname >");
        String nickname = input.nextLine();
        SetupMessage setupMessage = new SetupMessage(nickname);
        notifyObserver(obs -> obs.onUpdateNickname(setupMessage));
    }

    public void askNumberOfPlayers(){
        System.out.println("Type number of players >");
        int number = input.nextInt();
        NumberOfPlayerReply message = new NumberOfPlayerReply(Integer.toString(number));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

}

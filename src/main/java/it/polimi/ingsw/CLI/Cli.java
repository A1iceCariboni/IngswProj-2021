package it.polimi.ingsw.CLI;

import com.google.gson.Gson;
import com.google.gson.stream.JsonToken;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.ReadInput;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.CliObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

//TODO add the method checkResponse() in all the other method that need it

/**
 * @author Sofia Canestraci
 * class that implements the method to answer to the server message in the client controller
 */
public class Cli extends CliObservable {
    private VirtualModel virtualModel = new VirtualModel();
    Gson gson = new Gson();
    private ExecutorService inputRead ;
    private static final String STR_INPUT_CANCELED = "User input canceled.";
    private TurnPhase turnPhase;

    public Cli(){
        this.inputRead = Executors.newSingleThreadExecutor();
    }

    public VirtualModel getVirtualModel(){
        return virtualModel;
    }

    public void start(){
        /*System.out.println(">Insert the server IP address");
        System.out.print(">");
        String ip = input.nextLine();
        System.out.println(">Insert the server port");
        System.out.print(">");
        int port = input.nextInt();*/
        System.out.println(Constants.MAESTRIDELRINASCIMENTO);
        System.out.println(Constants.AUTHORS);
        int port = Constants.DEFAULT_PORT;
        String ip = Constants.LOCAL_HOST;
        notifyObserver(obs -> obs.onConnectionRequest(ip,port));
    }
    /**
     * Reads a line from the standard input.
     *
     * @return the string read from the input.
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new ReadInput());
        inputRead.submit(futureTask);

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    public void askNickname()  {
        System.out.println("Insert your nickname >");
        String nickname = null;
        try {
            nickname = readLine();
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }
        SetupMessage setupMessage = new SetupMessage(nickname);
        notifyObserver(obs -> obs.onUpdateNickname(setupMessage));
    }

    public void askNumberOfPlayers() {
        boolean ok = false;
        int number = 0;
        while(!ok) {
            try {
                number = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                System.out.println(STR_INPUT_CANCELED);
            }
            if(number >= Constants.MIN_NUMBER_OF_PLAYERS && number <= Constants.MAX_NUMBER_OF_PLAYERS){
                ok = true;
            }
            if(!ok){
                System.out.println("The number must be between " + Constants.MIN_NUMBER_OF_PLAYERS + "and" + Constants.MAX_NUMBER_OF_PLAYERS);
            }
        }
        NumberOfPlayerReply message = new NumberOfPlayerReply(Integer.toString(number));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    /**
     * adds the leader card to the board of the player
     * @param dummyLeaderCards cards of the player
     */
    public void DummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards){
        virtualModel.setLeaderCard(dummyLeaderCards);
    }

    /**
     * modifies the dummy faith track
     * @param faithTrack new faith track
     */
    public void faithTrackNew(DummyFaithTrack faithTrack){
        virtualModel.getPlayerBoard().setFaithTrack(faithTrack);
        virtualModel.showFaithTrack();
    }
    /**
     * modifies the dummy market of the development cards
     * @param devMarket new development cards market
     */
    public void devMarketNew(DummyDev[][] devMarket){
        virtualModel.setBoardDevCard(devMarket);
    }

    /**
     * modifies the dummy market tray
     * @param market new market
     */
    public void marketTrayNew(DummyMarket market){
        virtualModel.setDummyMarket(market);
    }

    /**
     * asks to the player which operations wants to do during the turn and to end the turn
     */
    public void yourTurn(){
        System.out.println("It's your turn.");
        chooseAction();
    }


    public void chooseAction() {
        turnPhase = TurnPhase.FREE;
        System.out.println("If it's your first round you can:");
        System.out.println("1. Discard a leader card");
        System.out.println("If it's not you can also:");
        System.out.println("2. Activate the production");
        System.out.println("3. Activate a leader card");
        System.out.println("4. Buy a development card");
        System.out.println("5. Take resources from the market");
        System.out.println("6. Rearrange the warehouse");
        System.out.println("7. Discard some resources");
        System.out.println("8. End your turn");
        int choice = 0;
        try {
            choice = readInt(8, 1, "What you want to do? Choose a number");
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }
        switch (choice) {
            case 1:
                discardLeader();
                break;
            case 8:
                notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.END_TURN,"")));
                break;
            case 2:
                turnPhase = TurnPhase.ACTIVATE_PRODUCTION;

                activateProduction(new String[0]);
                break;
            case 4:
                turnPhase = TurnPhase.BUY_DEV;
                buyDevelopmentCard();
                break;
               /* case 2:
                    takeResourcesFromMarket();
                    break;
                case 3:
                    activateProduction();
                    do {
                        System.out.println("Do you want to activate the production of another card? (y/n)");
                        if(input.nextLine() != "y" && input.nextLine() != "n"){
                            validInput1 = false;
                            System.out.println("Answer doesn't accepted.\n");
                        }
                        else activateProduction();
                    } while (!validInput1);
                    break;
                default:
                    validInput = false;
                    System.out.println("Number doesn't accepted.\n");
                    break;
            }*/

        }
    }

    public void discardLeader(){
        virtualModel.showLeaderCards();
        try{
            int id = readAnyInt("Select one id");
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(id))));
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }
    }

    /**
     * asks the resources and notifies the server
     */
    public void chooseResources(int quantity)  {
        String[] chosenResources = new String[quantity];
        String resource = null;
        for(int i=0; i<quantity; i++){
            try {
                resource = readResource();
            } catch (ExecutionException e) {
                System.out.println(STR_INPUT_CANCELED);
            }
            chosenResources[i] = resource;
        }
        Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenResources));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    /**
     * adds a resource where the player wants to add it
     * adds the resources to the dummy warehouse
     * notifies the server about the choice and sends -1 if the resource must be discard
     */
    public void addResourceToWareHouse(String[] resource){
        int[] answer = new int[resource.length];
        virtualModel.showWarewouse();
        int i = 0;
        try {
            for(String res : resource) {
                System.out.println("Resource: " + res);

                answer[i] = readAnyInt("In which depot you want to put it? Type -1 if you want to discard and give 1 faith points to the other players");
                i++;
            }
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }

    }

    /**
     * method for buy a development card
     * asks to the player which card wants, from where he wants to take the resources
     * and in which slot he wants to put the card
     */
    public void buyDevelopmentCard(){
        virtualModel.showDevCards();
        int r = 0;
        try {
            r = readInt(Constants.rows, 0, "Which is the row of the card you want to buy? [1/2/3/4]");
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }

        int c = 0;
        try {
            c = readInt(Constants.cols, 0, "Which is the column of the card you want to buy? [1/2/3]");
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }
        ArrayList<Integer> payloadDev = new ArrayList<>();
        payloadDev.add(r);
        payloadDev.add(c);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));

    }

    public void activateProduction(String[] toPay){
     virtualModel.showPlayerDevCards();
     virtualModel.showLeaderCards();
        System.out.println("You choose to activate the production, you can: ");
        System.out.println("1. Activate a development card production");
        System.out.println("2. Activate a leader card production(if you have some)");
        System.out.println("3. Activate the basic production (2 x 1)");
        System.out.println("4. Pay to start the production");
        int choice = 0;
        try {
            choice = readInt(4,1,"What do you want to do?");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        switch(choice){
            case 1:
                ArrayList<Integer> ids = new ArrayList<>();
                int i = 0;
                String response = "y";
                try {
                    while((i < 3) && (response.equalsIgnoreCase("y"))) {
                        int id = 0;
                        id = readAnyInt("Type the card's id");
                        ids.add(id);
                        i++;
                        System.out.println("Are you done?[y , n]");
                        response = readYN();
                    }
                    Message message = new Message(MessageType.ACTIVATE_PRODUCTION, gson.toJson(ids));
                    notifyObserver(obs -> obs.onReadyReply(message));
                } catch (ExecutionException e) {
                        System.out.println(STR_INPUT_CANCELED);
                }
                break;
            case 2:
                try{
                    int id = readAnyInt("Type the extra production power id");
                    System.out.println("Type the resource you want to produce");
                    String res = readResource();
                    String [] command = new String[2];
                    command[0] = Integer.toString(id);
                    command[1] = res;
                    Message message = new Message(MessageType.EXTRA_PRODUCTION, gson.toJson(command));
                    notifyObserver(obs -> obs.onReadyReply(message));
                } catch (ExecutionException e) {
                    System.out.println(STR_INPUT_CANCELED);
                }
                break;
            case 3:
                try{
                    System.out.println("Type the first resource you want to pay");
                    String res1 = readResource();
                    System.out.println("Type the second resource you want to pay");
                    String res2 = readResource();
                    System.out.println("Type the resource you want to produce");
                    String res3 = readResource();
                    String [] command = new String[3];
                    command[0] = res1;
                    command[1] = res2;
                    command[3] = res3;
                    Message message = new Message(MessageType.BASE_PRODUCTION, gson.toJson(command));
                    notifyObserver(obs -> obs.onReadyReply(message));
                } catch (ExecutionException e) {
                    System.out.println(STR_INPUT_CANCELED);
                }
                break;
            case 4:
                if(toPay.length == 0){
                    System.out.println("You have nothing to pay yet!");
                }else {
                    payResources(toPay);
                }
                break;
            }
    }


    public String readYN() throws ExecutionException {
        String res = null;
      do {
          res = readLine();
          if ((!res.equalsIgnoreCase("y")) && (!res.equalsIgnoreCase("n"))) {
              System.out.println("You have to type y or n");
          }
      }while((!res.equalsIgnoreCase("y")) && (!res.equalsIgnoreCase("n")));
      return res;
    }
    /**
     * Asks the player a valid number in a range, checks if the input is a number and
     * it's in the range, keep asking if it's not
     * @param max_value max value of the range
     * @param min_value min value of the range
     * @param question question that asks to insert the input
     * @return the correct choosen number
     */
    public int readInt(int max_value, int min_value, String question) throws ExecutionException {
        int number = min_value - 1;
        do{
            try {
                System.out.println(question);
                number = Integer.parseInt(readLine());
                if((number < min_value) || (number > max_value)){
                    System.out.println("That's not  a possible choice, try again");
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        }while((number > max_value || number < min_value));
        return number;
    }

    /**
     * reads a number without any restriction
     * @param question question asked to the player
     * @return the number given by the player
     * @throws ExecutionException if the input stream thread is interrupted
     */
    public int readAnyInt(String question) throws ExecutionException {
        int number = 0;
            try {
                System.out.println(question);
                number = Integer.parseInt(readLine());

            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        return number;
    }

    public String readResource() throws ExecutionException {
        String res = null;

        do {
            System.out.println("Type the resource name you want : [COIN,SHIELD,SERVANT,STONE]");
            res = readLine();
            if ((!res.equalsIgnoreCase("coin")) && (!res.equalsIgnoreCase("shield")) && (!res.equalsIgnoreCase("servant")) && (!res.equalsIgnoreCase("stone"))) {
                System.out.println("Not a resource!");
            }
        }while((!res.equalsIgnoreCase("coin"))&&(!res.equalsIgnoreCase("shield"))&&(!res.equalsIgnoreCase("servant"))&&(!res.equalsIgnoreCase("stone")));

       return res.toUpperCase();
    }


    public void checkResponse(String name) {
        switch(name){
            case "OK":
                System.out.println("Action completed with success! You can proceed");
                break;
            case "ERROR":
                System.out.println("Invalid action");
                break;
        }
    }

    public void modifyFaithMarker(int pos) {
        virtualModel.getPlayerBoard().moveFaithMarker(pos);
        virtualModel.showFaithTrack();
    }

    public void payResources(String[] resource)  {
        virtualModel.showWarewouse();
        virtualModel.showStrongbox();
        int[] ids = new int[resource.length];
        int i = -1;
        System.out.println("You have to pay");
           try {
           for(String res : resource) {
               System.out.println("Resource: " + res);
               int id = readAnyInt("From where do you want to take the resources? Type the depot id or -1 for strongbox");
               ids[i++] = id;
           }
           Message message = new Message(MessageType.RESOURCE_PAYMENT, gson.toJson(ids));
           notifyObserver(obs -> obs.onReadyReply(message));
           } catch (ExecutionException e) {
               System.out.println(STR_INPUT_CANCELED);
           }
    }

    public void slotChoice() {
        int answer = 0;
            System.out.println("In which slot do you want to put the card? (1/2/3)");
        try {
            answer = readInt(3, 1, "In which slot do you want to put the card? [1/2/3]");
        } catch (ExecutionException e) {
            System.out.println(STR_INPUT_CANCELED);
        }
        Message messageSlot = new Message(MessageType.SLOT_CHOICE, gson.toJson(answer - 1));
        notifyObserver(obs -> obs.onReadyReply(messageSlot));
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }
}

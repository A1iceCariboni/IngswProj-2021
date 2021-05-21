package it.polimi.ingsw.CLI;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.CliObservable;
import it.polimi.ingsw.utility.DummyWarehouseConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;


/**
 * @author Sofia Canestraci, Alice Cariboni
 * class that implements the method to answer to the server message in the client controller
 */
public class Cli extends CliObservable {
    private final VirtualModel virtualModel = new VirtualModel();
    Gson gson = new Gson();
    private TurnPhase turnPhase;
    private ExecutorService input;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public Cli() {
        this.input = Executors.newSingleThreadExecutor();
    }

    public VirtualModel getVirtualModel() {
        return virtualModel;
    }

    public void start() {
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
     */
    public String readLine() {
        String in = null;
        try {
            in = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public void askNickname()  {
        System.out.println("Insert your nickname >");
        String nickname = readLine();
        SetupMessage setupMessage = new SetupMessage(nickname);
        notifyObserver(obs -> obs.onUpdateNickname(setupMessage));
    }

    public void askNumberOfPlayers() {
        boolean ok = false;
        int number = 0;
        while(!ok) {
                number = Integer.parseInt(readLine());
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

    public void wareHouseNew(DummyWareHouse dummyWareHouse){
        virtualModel.getPlayerBoard().setWareHouse(dummyWareHouse);
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
        System.out.println("7. Discard a resource");
        System.out.println("8. End your turn");
        int  choice = readInt(8, 1, "What you want to do? Choose a number");
        switch (choice) {
            case 1:
                discardLeader();
                break;
            case 2:
                turnPhase = TurnPhase.ACTIVATE_PRODUCTION;

                activateProduction(new String[0]);
                break;

            case 3:
                activateLeader();
                break;

            case 4:
                turnPhase = TurnPhase.BUY_DEV;
                buyDevelopmentCard();
                break;

            case 5:
                turnPhase = TurnPhase.BUY_MARKET;
                takeResourcesFromMarket();
                break;

            case 6:
                modifyWarehouse();
                break;

            case 7:
                discardResource();
                break;

            case 8:
                turnPhase = TurnPhase.NOT_YOUR_TURN;
                notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.END_TURN, "")));
                break;

        }
    }

    private void discardResource() {
        virtualModel.showWarewouse();
            System.out.println("From which depot you want to discard the resource?");
            int id = readDepotId();
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.REMOVE_RESOURCES, gson.toJson(id))));
    }

    private void modifyWarehouse()  {
        try{
        virtualModel.showWarewouse();
        DummyWareHouse dummyWareHouse = DummyWarehouseConstructor.parseVoid();
        DummyExtraDepot dummyExtraDepot1 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1();
        if(dummyExtraDepot1.getId() != -1){
            dummyWareHouse.setExtraDepot1(new DummyExtraDepot(dummyExtraDepot1.getId(),dummyExtraDepot1.getDimension(), new ArrayList<>(), dummyExtraDepot1.getResourceType()));
        }
        DummyExtraDepot dummyExtraDepot2 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1();
        if(dummyExtraDepot2.getId() != -1){
            dummyWareHouse.setExtraDepot2(new DummyExtraDepot(dummyExtraDepot2.getId(),dummyExtraDepot2.getDimension(), new ArrayList<>(), dummyExtraDepot2.getResourceType()));
        }
            for(DummyDepot dummyDepot: dummyWareHouse.getDummyDepots()){
                String answer = "y";
                if(dummyDepot.getId() != -1) {
                    ArrayList<String> res = new ArrayList<>();
                    while((answer.equals("y") && (res.size() < dummyDepot.getDimension()))){
                            System.out.println("Which resource you want to put in the depot " +dummyDepot.getId());
                            String resource = readResource();
                            res.add(resource);
                            System.out.println("Others?");
                            answer = readYN();
                    }
                    dummyDepot.setResources(res);
                }
            }
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DEPOTS, gson.toJson(dummyWareHouse))));
        } catch (JsonFileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void discardLeader() {
        virtualModel.showLeaderCards();
            int id = readAnyInt("Select one id");
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(id))));

    }

    /**
     * asks the resources and notifies the server
     */
    public void chooseResources(int quantity) {
        String[] chosenResources = new String[quantity];
        String resource = null;
        for (int i = 0; i < quantity; i++) {
                resource = readResource();

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
    public void addResourceToWareHouse(String[] resource) {
        int[] answer = new int[resource.length];
        virtualModel.showWarewouse();
        int i = 0;
            for (String res : resource) {
                System.out.println("Resource: " + res);

                answer[i] = readAnyInt("In which depot you want to put it? Type -1 if you want to discard and give 1 faith points to the other players");
                i++;
            }
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));


    }


    /**
     * asks if the player wants to activate a leader card and, in case he answers yes, notifies the server
     */
    public void activateLeader() {
        virtualModel.showLeaderCards();
            int id = readAnyInt("Type the id of the leader card you want to activate");
            Message message = new Message(MessageType.ACTIVATE_LEADER_CARD, gson.toJson(id));
            notifyObserver(obs -> obs.onReadyReply(message));

    }




    /**
     * asks to the player whit which color wants to change the white marbles
     * shows the possible choices and then notify the server whit the arrays
     * @param num number of the white marbles
     */
    public void askWhiteMarble(int num) {
        System.out.println("You have to choose the leader effect you want to use for each white marble");
        virtualModel.showLeaderCards();
        String[] powers = new String[num];
            for (int i = 0; i < num; i++) {
                System.out.println("Choose one of your active powers, type the resource you want from this white marble");
                String resource = readResource();
                powers[i] = resource;
            }
            Message message = new Message(MessageType.WHITE_MARBLES, gson.toJson(powers));
            notifyObserver(obs -> obs.onReadyReply(message));

    }



    /**
     * method to choose a row or a column of the market
     */
    public void takeResourcesFromMarket(){
        virtualModel.showMarket();
        String rOc = "";
        int n = 0;
        boolean validInput = true;
            do {
                System.out.println("Do you want to buy a row(row) or a column(col)?");
                rOc = readLine();
                switch (rOc) {
                    case "row":
                        n = readInt(3,1, "Which row? [1/2/3]");
                        break;
                    case "col":
                        n = readInt(4,1,"Which column? [1/2/3/4]");
                        break;
                    default:
                        System.out.println("Answer doesn't accepted.\n");
                        validInput = false;
                        break;
                }
            } while (!validInput);
            String[] payload = new String[2];
            payload[0] = (rOc);
            payload[1] = (Integer.toString(n - 1));
            Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
            notifyObserver(obs -> obs.onReadyReply(message));

    }

    /**
     * method for buy a development card
     * asks to the player which card wants, from where he wants to take the resources
     * and in which slot he wants to put the card
     */
    public void buyDevelopmentCard(){
        virtualModel.showBoard();
        String yn = "n";
        while(yn.equalsIgnoreCase("n")){
            int n = readInt(12, 1, "Type the number to see the card");
            virtualModel.showDev(n);
            System.out.println("Are you done?");
            yn = readYN();
        }

        int  r = readInt(Constants.rows, 0, "Which is the row of the card you want to buy? [1/2/3/4]");


        int c = readInt(Constants.cols, 0, "Which is the column of the card you want to buy? [1/2/3]");
        ArrayList<Integer> payloadDev = new ArrayList<>();
        payloadDev.add(r);
        payloadDev.add(c);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));

    }

    public void activateProduction(String[] toPay){
     //virtualModel.showPlayerDevCards();
        virtualModel.showLeaderCards();
        System.out.println("You choose to activate the production, you can: ");
        System.out.println("1. Activate a development card production");
        System.out.println("2. Activate a leader card production(if you have some)");
        System.out.println("3. Activate the basic production (2 x 1)");
        System.out.println("4. Pay to start the production");
         int choice = readInt(4,1,"What do you want to do?");

        switch(choice){
            case 1:
                ArrayList<Integer> ids = new ArrayList<>();
                int i = 0;
                String response = "n";
                    while((i < 3) && (response.equalsIgnoreCase("n"))) {
                        int id = 0;
                        id = readAnyInt("Type the card's id");
                        ids.add(id);
                        i++;
                        System.out.println("Are you done?[y , n]");
                        response = readYN();
                    }
                    Message message = new Message(MessageType.ACTIVATE_PRODUCTION, gson.toJson(ids));
                    notifyObserver(obs -> obs.onReadyReply(message));

                break;
            case 2:
                    int id = readAnyInt("Type the extra production power id");
                    System.out.println("Type the resource you want to produce");
                    String res = readResource();
                    String [] command = new String[2];
                    command[0] = Integer.toString(id);
                    command[1] = res;
                    Message message1 = new Message(MessageType.EXTRA_PRODUCTION, gson.toJson(command));
                    notifyObserver(obs -> obs.onReadyReply(message1));

                break;
            case 3:
                    System.out.println("Type the first resource you want to pay");
                    String res1 = readResource();
                    System.out.println("Type the second resource you want to pay");
                    String res2 = readResource();
                    System.out.println("Type the resource you want to produce");
                    String res3 = readResource();
                    String [] command1 = new String[3];
                    command1[0] = res1;
                    command1[1] = res2;
                    command1[3] = res3;
                    Message message2 = new Message(MessageType.BASE_PRODUCTION, gson.toJson(command1));
                    notifyObserver(obs -> obs.onReadyReply(message2));

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


    public String readYN(){
        String res ;
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
    public int readInt(int max_value, int min_value, String question)  {
        int number = min_value - 1;
        do{
                System.out.println(question);
                number = Integer.parseInt(readLine());
                if((number < min_value) || (number > max_value)){
                    System.out.println("That's not  a possible choice, try again");
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
    public int readAnyInt(String question) {
        int number = 0;
            try {
                System.out.println(question);
                number = Integer.parseInt(readLine());

            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }
        return number;
    }

    public String readResource() {
        String res;
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

           for(String res : resource) {
               System.out.println("Resource: " + res);
               int id = readAnyInt("From where do you want to take the resources? Type the depot id or -1 for strongbox");
               ids[i++] = id;
           }
           Message message = new Message(MessageType.RESOURCE_PAYMENT, gson.toJson(ids));
           notifyObserver(obs -> obs.onReadyReply(message));

    }

    public void slotChoice() {
        int answer = 0;
            System.out.println("In which slot do you want to put the card? (1/2/3)");
            answer = readInt(3, 1, "In which slot do you want to put the card? [1/2/3]");
            Message messageSlot = new Message(MessageType.SLOT_CHOICE, gson.toJson(answer - 1));
        notifyObserver(obs -> obs.onReadyReply(messageSlot));
    }

    public int readDepotId(){
        int id = 0;
        do{
                id = readAnyInt("Type the id of the depot");
                if(!virtualModel.getPlayerBoard().getWareHouse().getIds().contains(id)){
                    System.out.println("Not a valid id");
                }

        }while(!virtualModel.getPlayerBoard().getWareHouse().getIds().contains(id));
        return id;
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }


    /**
     * sets the dummy strong box in the virtual model
     * @param strongBox the new dummy strongbox
     */
    public void newDummyStrongBox(DummyStrongbox strongBox){
        virtualModel.getPlayerBoard().setStrongBox(strongBox);
    }


    /**
     * adds the development cards in the dummy development section
     * @param cards
     */
    public void addDevCards(DummyDev[] cards){
        virtualModel.getPlayerBoard().setDevSections(cards);
    }

    public void waitTurn(){
        while(turnPhase == TurnPhase.NOT_YOUR_TURN){
               if( readLineWithTimeout() != null){
                   System.out.println("Wait it's not your turn");
                }
        }
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    public String readLineWithTimeout() {
        FutureTask<String> readNextLine = new FutureTask<>(() -> {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        });
        ExecutorService inputToDiscard = Executors.newSingleThreadExecutor();
        inputToDiscard.submit(readNextLine);
        String in = null;
        try{
            in = readNextLine.get(5000, TimeUnit.MILLISECONDS);

        } catch (ExecutionException | InterruptedException| TimeoutException e) {
            readNextLine.cancel(true);
            inputToDiscard.shutdownNow();
        }
        return in;
    }

    public void flushLine(){
        readLine();
    }
}

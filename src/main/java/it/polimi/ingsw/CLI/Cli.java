package it.polimi.ingsw.CLI;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.CliObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//TODO add the method checkResponse() in all the other method that need it

/**
 * @author Sofia Canestraci
 * class that implements the method to answer to the server message in the client controller
 */
public class Cli extends CliObservable {
    private Scanner input;
    private VirtualModel virtualModel = new VirtualModel(this);
    Gson gson = new Gson();

    public Cli(){
        this.input = new Scanner(System.in);
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
        boolean ok = false;
        int number = 0;
        while(!ok) {
            number = input.nextInt();
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
        virtualModel.addLeaderCard(dummyLeaderCards);
    }

    /**
     * modifies the dummy faith track
     * @param faithTrack
     */
    public void faithTrackNew(DummyFaithTrack faithTrack){
        virtualModel.getPlayerBoard().setFaithTrack(faithTrack);
    }

    public void devMarketNew(DummyDev[][] devMarket){
        virtualModel.setBoardDevCard(devMarket);
    }

    /**
     * modifies the dummy market tray
     * @param market
     */
    public void marketTrayNew(DummyMarket market){
        virtualModel.setDummyMarket(market);
    }

    /**
     * activate the leader cards on the dummy player board
     * @param id array of the id of the cards to activate
     */
    public void activateLeaderCard(int[] id){
        for(int i=0; i<id.length; i++) {
            if(virtualModel.getLeaderCards().get(i).getId() == id[i]){
                virtualModel.getLeaderCards().get(i).setIsActive(true);
            }
        }
    }

    /**
     * asks which leader cards remove from the initially assigned,
     * notifies the server to eliminated them
     * and eliminates them from the virtual model
     */
    public void askTwoLeaderCard(){
        ArrayList<DummyLeaderCard> leaderCards = virtualModel.getLeaderCards();
        //System.out.println(leaderCards);
        boolean validInput = false;
        System.out.println("First card you want to discard(1/2/3/4)?");
        int posCard = input.nextInt();
        do {
            if (posCard == 1){
                validInput = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard == 2){
                validInput = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard == 3){
                validInput = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard == 4){
                validInput = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard != 1 && posCard != 2 && posCard != 3 && posCard != 4){
                validInput = false;
                System.out.println("Answer doesn't accepted.\n First card you want to discard(1/2/3/4)?");
                posCard = input.nextInt();
            }
        } while(!validInput);

        boolean validInput1 = false;
        System.out.println("Second card you want to discard(1/2/3/4)?");
        int posCard1 = input.nextInt();
        do {
            if (posCard1 == 1 && posCard1 != posCard){
                validInput1 = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard1-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard1 == 2 && posCard1 != posCard){
                validInput1 = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard1-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard1 == 3 && posCard1 != posCard){
                validInput1 = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard1-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard1 == 4 && posCard1 != posCard){
                validInput1 = true;
                String IdCard = Integer.toString(virtualModel.getLeaderCards().get(posCard1-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
            else if(posCard1 != 1 && posCard1 != 2 && posCard1 != 3 && posCard1 != 4 || posCard1 == posCard){
                validInput1 = false;
                System.out.println("Answer doesn't accepted.\n Second card you want to discard(1/2/3/4)?");
                posCard1 = input.nextInt();
            }
        } while(!validInput1);
        virtualModel.removeLeaderCard(posCard-1);
        virtualModel.removeLeaderCard(posCard1-1);
    }

    /**
     * asks the resources and notifies the server
     */
    public void chooseResources(int quantity){
        int n = quantity;
        String[] chosenResources = new String[n];
        for(int i=0; i<n;){
            System.out.println("Choose a resource\n(shield, servant, stone, coin)");
            String resource = input.nextLine();
            if(resource == "shield" || resource == "servant" || resource == "stone" || resource == "shield"){
                chosenResources[i] = resource;
                i++;
            }
            else{
                System.out.println("Answer doesn't accepted\n");
            }
        }
        if(chosenResources[n-1]!=null){
            for(int i=0; i<n; i++){
                chosenResources[i].toUpperCase();
            }
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenResources));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    /*
     * adds a resource where the player wants to add it
     * adds the resources to the dummy ware house
     * notifies the server about the choice and sends -1 if the resource must be discard
     */
    public void addResourceToWareHouse(String resource){
        boolean validInput = true;
        do{
            System.out.println("In which depot do you want to put the resource" + resource + "?\n(1)depot1, (2)depot2, (3)depot3, (4)extradepot1, (5)extradepot2, (6)discard)");
            String answer = input.nextLine();
            switch(answer){
                case "1":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot1().addResource(resource);
                    String id1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                    Message message1 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, id1);
                    notifyObserver(obs -> obs.onReadyReply(message1));
                case "2":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot2().addResource(resource);
                    String id2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                    Message message2 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, id2);
                    notifyObserver(obs -> obs.onReadyReply(message2));
                case "3":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot3().addResource(resource);
                    String id3 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message message3 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, id3);
                    notifyObserver(obs -> obs.onReadyReply(message3));
                case "4":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().addResource(resource);
                    String idE1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                    Message messageE1 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, idE1);
                    notifyObserver(obs -> obs.onReadyReply(messageE1));
                case "5":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().addResource(resource);
                    String idE2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message messageE2 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, idE2);
                    notifyObserver(obs -> obs.onReadyReply(messageE2));
                case "6":
                    Message discard = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(-1));
                    notifyObserver(obs -> obs.onReadyReply(discard));
                default:
                    validInput = false;
                    System.out.println("Answer doesn't valid.\n");
            }

        } while(!validInput);
    }

    /*
     * adds a resource where the player wants to add it
     * adds the resources to the dummy warehouse
     * notifies the server about the choice and sends -1 if the resource must be discard and 0 for the strongbox
     */
    public void addResourceWherever(String resource){
        boolean validInput = true;
        do{
            System.out.println("In which depot do you want to put the resource" + resource + "?\n(1)depot1, (2)depot2, (3)depot3, (4)extradepot1, (5)extradepot2, (6)discard), (7)strongbox");
            String answer = input.nextLine();
            switch(answer){
                case "1":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot1().addResource(resource);
                    String id1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                    Message message1 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, id1);
                    notifyObserver(obs -> obs.onReadyReply(message1));
                case "2":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot2().addResource(resource);
                    String id2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                    Message message2 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, id2);
                    notifyObserver(obs -> obs.onReadyReply(message2));
                case "3":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot3().addResource(resource);
                    String id3 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message message3 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, id3);
                    notifyObserver(obs -> obs.onReadyReply(message3));
                case "4":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().addResource(resource);
                    String idE1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                    Message messageE1 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, idE1);
                    notifyObserver(obs -> obs.onReadyReply(messageE1));
                case "5":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().addResource(resource);
                    String idE2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message messageE2 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, idE2);
                    notifyObserver(obs -> obs.onReadyReply(messageE2));
                case "6":
                    Message discard = new Message(MessageType.PLACE_RESOURCE_WHEREVER, gson.toJson(-1));
                    notifyObserver(obs -> obs.onReadyReply(discard));
                case "7":
                    virtualModel.getPlayerBoard().getStrongBox().addResource(resource);
                    Message strong = new Message(MessageType.PLACE_RESOURCE_WHEREVER, gson.toJson(0));
                    notifyObserver(obs -> obs.onReadyReply(strong));
                default:
                    validInput = false;
                    System.out.println("Answer doesn't valid.\n");
            }

        } while(!validInput);
    }

    /**
     * modifies the position of the faith marker on the dummy faith track
     * @param pos new position of the faith marker
     */
    public void faithMove(int pos){
        virtualModel.getPlayerBoard().moveFaithMarker(pos);
    }

    /**
     * asks to the player which operations wants to do during the turn and to end the turn
     */
    public void yourTurn(){
        System.out.println("it's your turn.\n");
        askActivateLeaderCard();
        askRemoveLeaderCard();
        removeResources();
        boolean validInput = true;
        System.out.println("Which action do you want to do(1/2/3)?\n(1)Buy a development card\n(2)Take resources from the market\n(3)Activate a production");
        do {
            switch(input.nextInt()) {
                case 1:
                    boolean validInput0 = true;
                    int r;
                    do {
                        System.out.println("Which is the row of the card you want to buy? (1/2/3/4)");
                        r = input.nextInt();
                        if (r != 1 && r != 2 && r != 3 && r != 4) {
                            validInput0 = false;
                            System.out.println("Number of the row doesn't accepted.\n");
                        }
                    } while(!validInput0);
                    boolean validInput01 = true;
                    int c;
                    do{
                        System.out.println("Which is the column of the card you want to buy? (1/2/3)");
                        c = input.nextInt();
                        if (c != 1 && c != 2 && c != 3 && c != 4) {
                            validInput01 = false;
                            System.out.println("Number of the column doesn't accepted.\n");
                        }
                    } while (!validInput01);
                    ArrayList<Integer> payloadDev = new ArrayList<>();
                    payloadDev.add(r);
                    payloadDev.add(c);
                    Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
                    notifyObserver(obs -> obs.onReadyReply(messageDev));
                    boolean validInput2 = true;
                    do {
                        System.out.println("From where do you want to take the resources?\n(1)depot1 (2)depot2 (3)depot3 (4)extradepot1 (5)extradepot2 (6)strongbox");
                        switch (input.nextInt()) {
                            case 1:
                                String depotId1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                                Message message1 = new Message(MessageType.RESOURCE_PAYMENT, depotId1);
                                notifyObserver(obs -> obs.onReadyReply(message1));
                                break;
                            case 2:
                                String depotId2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                                Message message2 = new Message(MessageType.RESOURCE_PAYMENT, depotId2);
                                notifyObserver(obs -> obs.onReadyReply(message2));
                                break;
                            case 3:
                                String depotId3 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                                Message message3 = new Message(MessageType.RESOURCE_PAYMENT, depotId3);
                                notifyObserver(obs -> obs.onReadyReply(message3));
                                break;
                            case 4:
                                String extraDepotId1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                                Message messageE1 = new Message(MessageType.RESOURCE_PAYMENT, extraDepotId1);
                                notifyObserver(obs -> obs.onReadyReply(messageE1));
                                break;
                            case 5:
                                String extraDepotId2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId());//id del depot
                                Message messageE2 = new Message(MessageType.RESOURCE_PAYMENT, extraDepotId2);
                                notifyObserver(obs -> obs.onReadyReply(messageE2));
                                break;
                            case 6:
                                Message messageS = new Message(MessageType.RESOURCE_PAYMENT, gson.toJson(-1));
                                notifyObserver(obs -> obs.onReadyReply(messageS));
                                break;
                            default:
                                System.out.println("Number doesn't accepted.\n");
                                validInput2 = false;
                        }
                    } while (!validInput2);

                    boolean validInput3 = true;
                    do {
                        System.out.println("In which slot do you want to put the card? (1/2/3)");
                        if (input.nextInt() != 1 && input.nextInt() != 2 && input.nextInt() != 3) {
                            System.out.println("Number doesn't accepted.\n");
                            validInput3 = false;
                        } else {
                            Message messageSlot = new Message(MessageType.SLOT_CHOICE, gson.toJson(input.nextInt() - 1));
                            notifyObserver(obs -> obs.onReadyReply(messageSlot));
                        }
                    } while (!validInput3);

                case 2:
                    String rOc;
                    int n;
                    boolean validInput1 = true;
                    do {
                        System.out.println("Do you want to buy a row(r) or a column(c)?");
                        rOc = input.nextLine();
                        n = 0;
                        if (rOc == "r") {
                            rOc = "row";
                            System.out.println("Which row? (1/2/3)");
                            n = input.nextInt();
                            if (n != 1 && n != 2 && n != 3){
                                validInput1 = false;
                                System.out.println("Number of the row doesn't accepted.\n");
                            }
                        } else if (rOc == "c") {
                            rOc = "col";
                            System.out.println("Which column? (1/2/3/4)");
                            n = input.nextInt();
                            if (n != 1 && n != 2 && n != 3 && n != 4){
                                validInput1 = false;
                                System.out.println("Number of the column doesn't accepted.\n");
                            }
                        }
                        if(rOc != "r" && rOc != "c"){
                            System.out.println("Answer doesn't accepted.\n");
                            validInput1 = false;
                        }
                    } while (!validInput1);
                    ArrayList<String> payload = new ArrayList<>();
                    payload.add(rOc);
                    payload.add(Integer.toString(n));
                    Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
                    notifyObserver(obs -> obs.onReadyReply(message));

                case 3:
                    boolean validInput4 = true;
                    do{
                        System.out.println("Which card do you want to use to activate the production?\n(1)card of slot 1, (2)card of slot 2, (3)card of slot 3");
                        switch (input.nextInt()){
                            case 1:
                                String devId1 = gson.toJson(virtualModel.getPlayerBoard().getDevSection1().getId());
                                Message messageDev1 = new Message(MessageType.ACTIVATE_PRODUCTION, devId1);
                                notifyObserver(obs -> obs.onReadyReply(messageDev1));
                            case 2:
                                String devId2 = gson.toJson(virtualModel.getPlayerBoard().getDevSection2().getId());
                                Message messageDev2 = new Message(MessageType.ACTIVATE_PRODUCTION, devId2);
                                notifyObserver(obs -> obs.onReadyReply(messageDev2));
                            case 3:
                                String devId3 = gson.toJson(virtualModel.getPlayerBoard().getDevSection3().getId());
                                Message messageDev3 = new Message(MessageType.ACTIVATE_PRODUCTION, devId3);
                                notifyObserver(obs -> obs.onReadyReply(messageDev3));
                            default:
                                validInput4 = false;
                                System.out.println("Number doesn't accepted.\n");
                        }
                    } while (!validInput4);
                default:
                    validInput = false;
                    System.out.println("Number doesn't accepted.\n");
            }
        } while (!validInput);

        removeResources();
        askActivateLeaderCard();
        askRemoveLeaderCard();

        System.out.println("Do you want to end the turn? (y/n)");
        String answer = input.nextLine();
        boolean yes = true;
        do{
            if(answer == "y"){
                Message message = new Message(MessageType.END_TURN, "");
                notifyObserver(obs -> obs.onReadyReply(message));
            }
        } while(!yes);
    }

    /**
     * asks if the player wants to activate a leader card and, in case he answers yes, notifies the server
     */
    public void askActivateLeaderCard(){
        ArrayList<DummyLeaderCard> cards;
        cards = virtualModel.getLeaderCards();
        String firstCard = Integer.toString(cards.get(0).getId());
        String secondCard = Integer.toString(cards.get(1).getId());
        boolean yes = true;
        do {
            System.out.println("Do you want to activate a leader card? (y/n)");
            String answer = input.nextLine();
            if(answer == "y"){
                System.out.println("Which card do you want to activate? (1/2)");
                int card = input.nextInt();
                boolean validInput = false;
                do {
                    if (card == 1) {
                        System.out.println("Do you want to activate also the other card (y/n?");
                        String choice = input.nextLine();
                        if (choice == "y") {
                            validInput = true;
                            Message message = new Message(MessageType.ACTIVATE_LEADER_CARD, firstCard);
                            Message message1 = new Message(MessageType.ACTIVATE_LEADER_CARD, secondCard);
                            notifyObserver(obs -> obs.onReadyReply(message));
                            notifyObserver(obs -> obs.onReadyReply(message1));
                        } else {
                            if (choice == "n") {
                                validInput = true;
                                Message message = new Message(MessageType.ACTIVATE_LEADER_CARD, firstCard);
                                notifyObserver(obs -> obs.onReadyReply(message));
                            }
                        }
                    } else if (card == 2) {
                        System.out.println("Do you want to discard also the other card (y/n?");
                        String choice = input.nextLine();
                        if (choice == "y") {
                            validInput = true;
                            Message message = new Message(MessageType.ACTIVATE_LEADER_CARD, firstCard);
                            Message message1 = new Message(MessageType.ACTIVATE_LEADER_CARD, secondCard);
                            notifyObserver(obs -> obs.onReadyReply(message));
                            notifyObserver(obs -> obs.onReadyReply(message1));
                        } else {
                            if (choice == "n") {
                                validInput = true;
                                Message message1 = new Message(MessageType.ACTIVATE_LEADER_CARD, secondCard);
                                notifyObserver(obs -> obs.onReadyReply(message1));
                            }
                        }
                    }
                    if (card != 1 && card != 2) {
                        System.out.println("Number doesn't accepted.\n");
                        card = input.nextInt();
                    }
                } while (!validInput);
            }
            else if(answer != "y" && answer != "n"){
                yes = false;
                System.out.println("Answer doesn't accepted.\n");
            }
        } while(!yes);
    }

    /**
     * when the client wants to discard a leader card or both two
     * the client notify the server
     * and the dummy leader cards are eliminated from the virtual model
     */
    public void askRemoveLeaderCard(){
        //deve far vedere le due carte
        //System.out.println(virtualModel.getLeaderCards());
        ArrayList<DummyLeaderCard> cards;
        cards = virtualModel.getLeaderCards();
        String firstCard = Integer.toString(cards.get(0).getId());
        String secondCard = Integer.toString(cards.get(1).getId());
        boolean yes = true;
        do {
            System.out.println("Do you want to discard a leader card? (y/n)");
            String answer = input.nextLine();
            if(answer == "y"){
            System.out.println("Which card do you want to discard (1/2)?");
            int card = input.nextInt();
            boolean validInput = false;
            do {
                if (card == 1) {
                    System.out.println("Do you want to discard also the other card (y/n?");
                    String choice = input.nextLine();
                    if (choice == "y") {
                        validInput = true;
                        int pos = 0;
                        virtualModel.removeLeaderCard(pos);
                        pos = 1;
                        virtualModel.removeLeaderCard(pos);
                        Message message = new Message(MessageType.DISCARD_LEADER, firstCard);
                        Message message1 = new Message(MessageType.DISCARD_LEADER, secondCard);
                        notifyObserver(obs -> obs.onReadyReply(message));
                        notifyObserver(obs -> obs.onReadyReply(message1));
                    } else {
                        if (choice == "n") {
                            validInput = true;
                            int pos = 0;
                            virtualModel.removeLeaderCard(pos);
                            Message message = new Message(MessageType.DISCARD_LEADER, firstCard);
                            notifyObserver(obs -> obs.onReadyReply(message));
                        }
                    }
                } else if (card == 2) {
                    System.out.println("Do you want to discard also the other card (y/n?");
                    String choice = input.nextLine();
                    if (choice == "y") {
                        validInput = true;
                        int pos = 0;
                        virtualModel.removeLeaderCard(pos);
                        pos = 1;
                        virtualModel.removeLeaderCard(pos);
                        Message message = new Message(MessageType.DISCARD_LEADER, firstCard);
                        Message message1 = new Message(MessageType.DISCARD_LEADER, secondCard);
                        notifyObserver(obs -> obs.onReadyReply(message));
                        notifyObserver(obs -> obs.onReadyReply(message1));
                    } else {
                        if (choice == "n") {
                            validInput = true;
                            int pos = 1;
                            virtualModel.removeLeaderCard(pos);
                            Message message1 = new Message(MessageType.DISCARD_LEADER, secondCard);
                            notifyObserver(obs -> obs.onReadyReply(message1));
                        }
                    }
                }
                if (card != 1 && card != 2) {
                    System.out.println("Number doesn't accepted.\n");
                    card = input.nextInt();
                }
            } while (!validInput);
            }
            else if(answer != "y" && answer != "n"){
                yes = false;
                System.out.println("Answer doesn't accepted.\n");
            }
        } while(!yes);
    }

    /**
     * asks to the player whit which color wants to change the white marbles
     * shows the possible choices and then notify the server whit the arrays
     * @param num number of the white marbles
     */
    public void askWhiteMarble(int num){
        boolean validInput = true;
        String[] powers = new String[num];
        for(int i=0; i<num; i++){
            do{
                System.out.println("Which color do you want to transform this white marble between these " + virtualModel.getPlayerBoard().getWhiteMarblePower() + " ? (1/2/3/4)");
                switch(input.nextInt()) {
                    case 1:
                        powers[i] = virtualModel.getPlayerBoard().getWhiteMarblePower().get(0);
                    case 2:
                        powers[i] = virtualModel.getPlayerBoard().getWhiteMarblePower().get(1);
                    case 3:
                        powers[i] = virtualModel.getPlayerBoard().getWhiteMarblePower().get(2);
                    case 4:
                        powers[i] = virtualModel.getPlayerBoard().getWhiteMarblePower().get(3);
                    default:
                        validInput = false;
                        System.out.println("Number doesn't accepted.\n");
                }
            } while (!validInput);
        }
        Message message = new Message(MessageType.WHITE_MARBLES, gson.toJson(powers));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    /**
     * adds to the virtualModel all the ids of the depots and extradepots
     * @param depots
     */
    public void allDepots(DummyDepot[] depots){
        ArrayList<DummyDepot> dummyDepots = new ArrayList<>(Arrays.asList(depots));
        for(int i=0; i<depots.length; i++) {
            switch(dummyDepots.get(i).getId()){
                case 1:
                    virtualModel.getPlayerBoard().getWareHouse().setDepot1(dummyDepots.get(i));
                case 2:
                    virtualModel.getPlayerBoard().getWareHouse().setDepot2(dummyDepots.get(i));
                case 3:
                    virtualModel.getPlayerBoard().getWareHouse().setDepot3(dummyDepots.get(i));
                case 4:
                    virtualModel.getPlayerBoard().getWareHouse().setExtraDepot1((DummyExtraDepot) dummyDepots.get(i));
                case 5:
                    virtualModel.getPlayerBoard().getWareHouse().setExtraDepot2((DummyExtraDepot) dummyDepots.get(i));
            }
        }
    }

    /**
     * sets the dummy strong box in the virtual model
     * @param strongBox the new dummy strongbox
     */
    public void newDummyStrongBox(DummyStrongbox strongBox){
        virtualModel.getPlayerBoard().setStrongBox(strongBox);
    }

    /**
     * set the dummy extra production
     * @param extraProd
     */
    public void addExtraProduction(DummyExtraProduction[] extraProd){
        virtualModel.getPlayerBoard().setExtraProduction(extraProd);
    }

    /**
     * adds the discounted resources to the virtual model
     * @param discounts
     */
    public void addDiscountedResources(String[] discounts){
        ArrayList<String> discountedResources = new ArrayList<>(Arrays.asList(discounts));
        virtualModel.getPlayerBoard().setDiscountedResources(discountedResources);
    }

    /**
     * adds the development cards in the dummy development section
     * @param cards
     */
    public void addDevCards(DummyDev[] cards){
        virtualModel.getPlayerBoard().setDevSection1(cards[0]);
        virtualModel.getPlayerBoard().setDevSection2(cards[1]);
        virtualModel.getPlayerBoard().setDevSection3(cards[2]);
    }

    /**
     * set the whites marbles powers in the virtual model
     * @param powers
     */
    public void addWhiteMarblesPower(String[] powers){
        ArrayList<String> whiteMarblePower = new ArrayList<>(Arrays.asList(powers));
        virtualModel.getPlayerBoard().setWhiteMarblePower(whiteMarblePower);
    }

    public void removeResources(){
        boolean validInput = true;
        boolean validInput1 = true;
        ArrayList<String> remove = new ArrayList<>();
        String idDepot;
        System.out.println("Do you want to discard some resources? (y/n)");
        String answer = input.nextLine();
        do{
            if(answer == "y"){
                System.out.println("Which depot?\n(1)depot1, (2)depot2, (3)depot3, (4)extradepot1, (5)extradepot2");
                do{
                    switch (input.nextInt()){
                        case 1:
                            idDepot = Integer.toString(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                            remove.add(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getResources().get(0));
                            remove.add(idDepot);
                            Message message = new Message(MessageType.REMOVE_RESOURCES, gson.toJson(remove));
                            notifyObserver(obs -> obs.onReadyReply(message));
                        case 2:
                            idDepot = Integer.toString(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                            remove.add(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getResources().get(0));
                            remove.add(idDepot);
                            Message message2 = new Message(MessageType.REMOVE_RESOURCES, gson.toJson(remove));
                            notifyObserver(obs -> obs.onReadyReply(message2));
                        case 3:
                            idDepot = Integer.toString(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                            remove.add(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getResources().get(0));
                            remove.add(idDepot);
                            Message message3 = new Message(MessageType.REMOVE_RESOURCES, gson.toJson(remove));
                            notifyObserver(obs -> obs.onReadyReply(message3));
                        case 4:
                            idDepot = Integer.toString(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                            remove.add(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().get(0));
                            remove.add(idDepot);
                            Message messageE1 = new Message(MessageType.REMOVE_RESOURCES, gson.toJson(remove));
                            notifyObserver(obs -> obs.onReadyReply(messageE1));
                        case 5:
                            idDepot = Integer.toString(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId());
                            remove.add(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().get(0));
                            remove.add(idDepot);
                            Message messageE2 = new Message(MessageType.REMOVE_RESOURCES, gson.toJson(remove));
                            notifyObserver(obs -> obs.onReadyReply(messageE2));
                        default:
                            validInput1 = false;
                            System.out.println("Number doesn't accepted.\n");
                    }
                } while(!validInput1);
            }
            else if(answer != "y" && answer != "n"){
                validInput = false;
                System.out.println("Answer doesn't accepted.\n");
            }
        } while (!validInput);
    }

    public boolean checkResponse(){
        return true;
    }

}

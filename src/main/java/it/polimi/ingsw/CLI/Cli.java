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
    private VirtualModel virtualModel = new VirtualModel();
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
        System.out.println(Constants.MAESTRIDELRINASCIMENTO);
        System.out.println(Constants.AUTHORS);
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
     * @param faithTrack new faith track
     */
    public void faithTrackNew(DummyFaithTrack faithTrack){
        virtualModel.getPlayerBoard().setFaithTrack(faithTrack);
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
     * asks which leader cards the player want to remove from the initially assigned,
     * it notifies the server to eliminated them
     * and it eliminates them from the virtual model
     */
    public void askTwoLeaderCard(){
        //ArrayList<DummyLeaderCard> leaderCards = virtualModel.getLeaderCards();
        //System.out.println(leaderCards);
        boolean validInput = true;
        System.out.println("First card you want to discard(1/2/3/4)?");
        int posCard = input.nextInt();
        do {
            if(posCard != 1 && posCard != 2 && posCard != 3 && posCard != 4){
                validInput = false;
                System.out.println("Answer doesn't accepted.\n First card you want to discard(1/2/3/4)?");
                posCard = input.nextInt();}
            else{
                String IdCard = gson.toJson(virtualModel.getLeaderCards().get(posCard-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
                }
        } while(!validInput);

        boolean validInput1 = true;
        System.out.println("Second card you want to discard(1/2/3/4)?");
        int posCard1 = input.nextInt();
        do {
            if(posCard1 != 1 && posCard1 != 2 && posCard1 != 3 && posCard1 != 4 || posCard1 == posCard){
                validInput1 = false;
                System.out.println("Answer doesn't accepted.\n Second card you want to discard(1/2/3/4)?");
                posCard1 = input.nextInt();
            }
            else{
                String IdCard = gson.toJson(virtualModel.getLeaderCards().get(posCard1-1).getId());
                Message message = new Message(MessageType.DISCARD_LEADER, IdCard);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
        } while(!validInput1);

        //manca il messaggio di ok che permetta questo
        virtualModel.removeLeaderCard(posCard-1);
        virtualModel.removeLeaderCard(posCard1-1);
    }

    /**
     * asks the resources and notifies the server
     */
    public void chooseResources(int quantity){
        boolean validInput = true;
        String[] chosenResources = new String[quantity];
        for(int i=0; i<quantity; i++){
            do{
                System.out.println("Choose a resource\n(shield, servant, stone, coin)");
                String resource = input.nextLine();
                if(resource == "shield" || resource == "servant" || resource == "stone" || resource == "coin"){
                    chosenResources[i] = resource.toUpperCase();
                }
                else{
                    validInput = false;
                    System.out.println("Answer doesn't accepted\n");
                }
            } while(!validInput);
        }
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenResources));
            notifyObserver(obs -> obs.onReadyReply(message));
    }

    /*
     * adds a resource where the player wants to add it
     * adds the resources to the dummy warehouse
     * notifies the server about the choice and sends -1 if the resource must be discard
     */
    public void addResourceToWareHouse(String resource){
        boolean validInput = true;
        do{
            System.out.println("In which depot do you want to put the resource" + resource + "?\n(1)depot1, (2)depot2, (3)depot3, (4)extradepot1, (5)extradepot2, (6)discard)");
            String answer = input.nextLine();
            switch(answer){
                case "1":
                    //ci vorrebbe il messaggio di ok prima di aggiungere le risorse
                    virtualModel.getPlayerBoard().getWareHouse().getDepot1().addResource(resource);
                    String id1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                    Message message1 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, id1);
                    notifyObserver(obs -> obs.onReadyReply(message1));
                    break;
                case "2":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot2().addResource(resource);
                    String id2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                    Message message2 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, id2);
                    notifyObserver(obs -> obs.onReadyReply(message2));
                    break;
                case "3":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot3().addResource(resource);
                    String id3 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message message3 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, id3);
                    notifyObserver(obs -> obs.onReadyReply(message3));
                    break;
                case "4":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().addResource(resource);
                    String idE1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                    Message messageE1 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, idE1);
                    notifyObserver(obs -> obs.onReadyReply(messageE1));
                    break;
                case "5":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().addResource(resource);
                    String idE2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message messageE2 = new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, idE2);
                    notifyObserver(obs -> obs.onReadyReply(messageE2));
                    break;
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
            System.out.println("Where do you want to put the resource" + resource + "?\n(1)depot1, (2)depot2, (3)depot3, (4)extradepot1, (5)extradepot2, (6)discard), (7)strongbox");
            String answer = input.nextLine();
            switch(answer){
                case "1":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot1().addResource(resource);
                    String id1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                    Message message1 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, id1);
                    notifyObserver(obs -> obs.onReadyReply(message1));
                    break;
                case "2":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot2().addResource(resource);
                    String id2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                    Message message2 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, id2);
                    notifyObserver(obs -> obs.onReadyReply(message2));
                    break;
                case "3":
                    virtualModel.getPlayerBoard().getWareHouse().getDepot3().addResource(resource);
                    String id3 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message message3 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, id3);
                    notifyObserver(obs -> obs.onReadyReply(message3));
                    break;
                case "4":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().addResource(resource);
                    String idE1 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                    Message messageE1 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, idE1);
                    notifyObserver(obs -> obs.onReadyReply(messageE1));
                    break;
                case "5":
                    virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().addResource(resource);
                    String idE2 = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    Message messageE2 = new Message(MessageType.PLACE_RESOURCE_WHEREVER, idE2);
                    notifyObserver(obs -> obs.onReadyReply(messageE2));
                    break;
                case "6":
                    Message discard = new Message(MessageType.PLACE_RESOURCE_WHEREVER, gson.toJson(-1));
                    notifyObserver(obs -> obs.onReadyReply(discard));
                    break;
                case "7":
                    virtualModel.getPlayerBoard().getStrongBox().addResource(resource);
                    Message strong = new Message(MessageType.PLACE_RESOURCE_WHEREVER, gson.toJson(0));
                    notifyObserver(obs -> obs.onReadyReply(strong));
                    break;
                default:
                    validInput = false;
                    System.out.println("Answer doesn't valid.\n");
                    break;
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
        boolean validInput1 = true;
        System.out.println("Which action do you want to do(1/2/3)?\n(1)Buy a development card\n(2)Take resources from the market\n(3)Activate a production");
        do {
            switch(input.nextInt()) {
                case 1:
                    buyDevelopmentCard();
                    break;

                case 2:
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
            }
        } while (!validInput);
        endTurn();
        removeResources();
        askActivateLeaderCard();
        askRemoveLeaderCard();
        endTurn();
    }

    /**
     * asks if the player wants to end the turn
     */
    public void endTurn(){
        System.out.println("Do you want to end the turn? (y/n)");
        String answer = input.nextLine();
        boolean yes = true;
        do{
            if(answer != "y" && answer != "n"){
                yes = false;
                System.out.println("Answer doesn't accepted.\nDo you want to end the turn? (y/n)");
                answer = input.nextLine();
            }
            else if(answer == "y"){
                Message message = new Message(MessageType.END_TURN, "");
                notifyObserver(obs -> obs.onReadyReply(message));
            }
        } while(!yes);
    }

    /**
     * method for buy a development card
     * asks to the player which card wants, from where he wants to take the resources
     * and in which slot he wants to put the card
     */
    public void buyDevelopmentCard(){
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
        String depotId = null;
        do { //qui ci vorrebbe un messaggio dal server che dice di quante risorse c'è bisogno così il messaggio
            // viene ripetuto tante volte quante sono le risorse necessarie
            System.out.println("From where do you want to take the resources?\n(1)depot1 (2)depot2 (3)depot3 (4)extradepot1 (5)extradepot2 (6)strongbox");
            switch (input.nextInt()) {
                case 1:
                    depotId = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                    break;
                case 2:
                    depotId = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                    break;
                case 3:
                    depotId = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                    break;
                case 4:
                    depotId = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                    break;
                case 5:
                    depotId = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId());
                    break;
                case 6:
                    Message messageS = new Message(MessageType.RESOURCE_PAYMENT, gson.toJson(-1));
                    notifyObserver(obs -> obs.onReadyReply(messageS));
                    break;
                default:
                    System.out.println("Number doesn't accepted.\n");
                    validInput2 = false;
            }
            if(depotId != null){
                Message message = new Message(MessageType.RESOURCE_PAYMENT, depotId);
                notifyObserver(obs -> obs.onReadyReply(message));
            }
        } while (!validInput2);

        boolean validInput3 = true;
        int answer;
        do {
            System.out.println("In which slot do you want to put the card? (1/2/3)");
            answer = input.nextInt();
            if (answer != 1 && answer != 2 && answer != 3) {
                System.out.println("Number doesn't accepted.\n");
                validInput3 = false;
            } else {
                Message messageSlot = new Message(MessageType.SLOT_CHOICE, gson.toJson(answer - 1));
                notifyObserver(obs -> obs.onReadyReply(messageSlot));
            }
        } while (!validInput3);
    }

    /**
     * method to choose a row or a column of the market
     */
    public void takeResourcesFromMarket(){
        String rOc;
        int n;
        boolean validInput1 = true;
        do {
            System.out.println("Do you want to buy a row(r) or a column(c)?");
            rOc = input.nextLine();
            n = 0;
            switch(rOc){
                case "r":
                    rOc = "row";
                    System.out.println("Which row? (1/2/3)");
                    n = input.nextInt();
                    if (n != 1 && n != 2 && n != 3){
                        validInput1 = false;
                        System.out.println("Number of the row doesn't accepted.\n");
                    }
                    break;
                case "c":
                    rOc = "col";
                    System.out.println("Which column? (1/2/3/4)");
                    n = input.nextInt();
                    if (n != 1 && n != 2 && n != 3 && n != 4){
                        validInput1 = false;
                        System.out.println("Number of the column doesn't accepted.\n");
                    }
                    break;
                default:
                    System.out.println("Answer doesn't accepted.\n");
                    validInput1 = false;
                    break;
            }
        } while (!validInput1);
        ArrayList<String> payload = new ArrayList<>();
        payload.add(rOc);
        payload.add(Integer.toString(n));
        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void activateProduction(){
        boolean validInput4 = true;
        DummyDev[] devs = virtualModel.getPlayerBoard().getDevSections();
        int card;
        do{
            System.out.println("Which card do you want to use to activate the production?\n(1)card of slot 1, (2)card of slot 2, (3)card of slot 3");
            card = input.nextInt();
            if(card != 1 && card != 2 && card != 3){
                validInput4 = false;
                System.out.println("Number doesn't accepted.\n");
            }
            else {
                String devId = gson.toJson(devs[card-1].getId());
                Message messageDev = new Message(MessageType.ACTIVATE_PRODUCTION, devId);
                notifyObserver(obs -> obs.onReadyReply(messageDev));
            }
            } while (!validInput4);
    }

    /**
     * asks if the player wants to activate a leader card and, in case he answers yes, notifies the server
     */
    public void askActivateLeaderCard(){
        ArrayList<DummyLeaderCard> cards;
        cards = virtualModel.getLeaderCards();
        boolean yes = true;
        do {
            System.out.println("Do you want to activate a leader card? (y/n)");
            String answer = input.nextLine();
            if(answer == "y"){
                boolean validInput = true;
                do {
                    System.out.println("Which card do you want to activate? (1/2)");
                    int card = input.nextInt();
                    if (card != 1 && card != 2) {
                        validInput = false;
                        System.out.println("Number doesn't accepted.\n");
                    }
                    else {
                        //se il server risponde ok:
                        //virtualModel.getLeaderCards().get(card-1).setIsActive(true);
                        Message message = new Message(MessageType.ACTIVATE_LEADER_CARD, gson.toJson(cards.get(card-1).getId()));
                        notifyObserver(obs -> obs.onReadyReply(message));
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
        boolean yes = true;
        do {
            System.out.println("Do you want to discard a leader card? (y/n)");
            String answer = input.nextLine();
            if(answer == "y"){
                boolean validInput = true;
                do {
                    System.out.println("Which card do you want to discard (1/2)?");
                    int card = input.nextInt();
                    if (card != 1 && card != 2) {
                        validInput = false;
                        System.out.println("Number doesn't accepted.\n");
                    }
                    else{
                        virtualModel.removeLeaderCard(card-1);
                        Message message = new Message(MessageType.DISCARD_LEADER, gson.toJson(cards.get(card-1).getId()));
                        notifyObserver(obs -> obs.onReadyReply(message));
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
                System.out.println("Which color do you want to transform this white marble? (1/2/3/4)");
                int color = input.nextInt();
                if(color != 1 && color != 2 && color !=3 && color != 4){
                    validInput = false;
                    System.out.println("Number doesn't accepted.\n");
                }
                else powers[i] = virtualModel.getPlayerBoard().getWhiteMarblePower().get(color-1);
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
        virtualModel.getPlayerBoard().setDevSections(cards);
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
        String idDepot = null;
        int number;
        System.out.println("Do you want to discard a resource? (y/n)");
        String answer = input.nextLine();
        do{
            if(answer == "y"){
                do{
                    System.out.println("Which depot?\n(1)depot1, (2)depot2, (3)depot3, (4)extradepot1, (5)extradepot2");
                    number = input.nextInt();
                    switch (number){
                        case 1:
                            idDepot = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot1().getId());
                            break;
                        case 2:
                            idDepot = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot2().getId());
                            break;
                        case 3:
                            idDepot = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getDepot3().getId());
                            break;
                        case 4:
                            idDepot = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId());
                            break;
                        case 5:
                            idDepot = gson.toJson(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId());
                            break;
                        default:
                            validInput1 = false;
                            System.out.println("Number doesn't accepted.\n");
                            break;
                    }
                } while(!validInput1);
                Message message = new Message(MessageType.REMOVE_RESOURCES, idDepot);
                notifyObserver(obs -> obs.onReadyReply(message));
                System.out.println("Do you want to discard another resource? (y/n)");
                answer = input.nextLine();
            }
            else if(answer != "y" && answer != "n"){
                validInput = false;
                System.out.println("Answer doesn't accepted.\n");
                System.out.println("Do you want to discard a resource? (y/n)");
                answer = input.nextLine();
            }
        } while (!validInput || answer == "y");
    }

    public boolean checkResponse(){
        return true;
    }

}

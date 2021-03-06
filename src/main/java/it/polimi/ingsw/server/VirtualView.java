package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.InvalidNickname;
import it.polimi.ingsw.messages.OkMessage;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.observers.Observer;

import java.util.ArrayList;

/**
 * class between network and the game controller on the server side , the controller call the methods of this class and this class communicate with the client
 * @author Alice Cariboni
 */
public class VirtualView implements Observer {

    private ClientHandler clientHandler;
    private String nickname;
    private ArrayList<Marble> freeMarble;
    private ArrayList<Resource> resourcesToPay;
    private ArrayList<Integer> cardsToActivate;
    private ArrayList<Integer> extraProductionToActivate;
    private ArrayList<Resource> resourcesToProduce;
    private Resource basicProd;
    private int gameActionPerTurn;

    public VirtualView() {
    }

    public VirtualView(ClientHandler clientHandler, String nickname) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
        freeMarble = new ArrayList<>();
        resourcesToPay = new ArrayList<>();
        cardsToActivate = new ArrayList<>();
        extraProductionToActivate = new ArrayList<>();
        resourcesToProduce = new ArrayList<>();
        gameActionPerTurn = 0;
    }

    public void nickNameResult(boolean accepted, boolean first, boolean fullServer) {
        if (accepted) {
            clientHandler.sendMessage(new OkMessage("You're logged in"));
        } else {
            if (fullServer) {
                clientHandler.sendMessage(new ErrorMessage("The game is already started, try again later"));
            } else {
                clientHandler.sendMessage(new InvalidNickname("This nickname is already in use, try another one"));
                clientHandler.sendMessage(new ErrorMessage("Nickname already in use"));
            }
        }
    }


    @Override
    public void update(String line) {

    }

    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void onDisconnect() {

    }

    public String getNickname() {
        return nickname;
    }



    public ArrayList<Marble> getFreeMarble() {
        return freeMarble;
    }



    public void sendInvalidActionMessage() {
        clientHandler.sendMessage(new ErrorMessage("Not a valid action"));
    }

    public void addFreeMarble(ArrayList<Marble> freeMarble) {
        this.freeMarble = freeMarble;
    }



    public void addAllFreeMarbles(ArrayList<Marble> marbles) {
        this.freeMarble.addAll(marbles);
    }

    public void removeAllFreeMarbles() {
        this.freeMarble.clear();
    }




    public void addCardToActivate(int id){
        this.cardsToActivate.add(id);
    }

    public void addAllResourcesToPay(ArrayList<Resource> resources){
        this.resourcesToPay.addAll(resources);
    }

    public void removeCardsToActivate() {
        this.cardsToActivate.clear();
    }

    public void removeResourcesToPay() {
        this.resourcesToPay.clear();
    }

    public ArrayList<Resource> getResourcesToPay() {
        return resourcesToPay;
    }

    public ArrayList<Integer> getCardsToActivate() {
        return cardsToActivate;
    }

    public void addExtraProductionToActivate(int id){
        this.extraProductionToActivate.add(id);
    }

    public void removeAllExtraProduction(){
        this.extraProductionToActivate.clear();
    }

    public void addResourceToProduce(Resource resource){
        this.resourcesToProduce.add(resource);
    }

    public void removeAllResourcesToProduce(){
        this.resourcesToProduce.clear();
    }

    public Resource getBasicProd() {
        return basicProd;
    }

    public void setBasicProd(Resource basicProd) {
        this.basicProd = basicProd;
    }

    public ArrayList<Integer> getExtraProductionToActivate() {
        return extraProductionToActivate;
    }

    public ArrayList<Resource> getResourcesToProduce() {
        return resourcesToProduce;
    }
    public void removeResourceToProduce(int pos){
        this.resourcesToProduce.remove(pos);
    }

    public void doneGameAction(int done){
        gameActionPerTurn = done;
    }

    public boolean isGameActionDone(){
        return gameActionPerTurn == 1;
    }

    public void close(){
        clientHandler.disconnect();
    }
}
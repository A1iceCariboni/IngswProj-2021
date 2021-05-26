package it.polimi.ingsw.server;

import it.polimi.ingsw.enumerations.MarbleColor;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.InvalidNickname;
import it.polimi.ingsw.messages.answer.NumberOfPlayerRequest;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.StrongBox;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.observers.Observer;

import java.util.ArrayList;
import java.util.Map;

/**
 * class between network and the game controller on the server side , the controller call the methods of this class and this class communicate with the client
 * @author Alice Cariboni
 */
public class VirtualView implements Observer {
    private ClientHandler clientHandler;
    private String nickname;
    private ArrayList<Resource> freeResources;
    private ArrayList<Marble> freeMarble;
    private ArrayList<DevelopmentCard> freeDevelopment;
    private ArrayList<Resource> resourcesToPay;
    private ArrayList<Integer> cardsToActivate;
    private ArrayList<Integer> extraProductionToActivate;
    private ArrayList<Resource> resourcesToProduce;
    private Resource basicProd;

    public VirtualView() {
    }

    public VirtualView(ClientHandler clientHandler, String nickname) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
        freeResources = new ArrayList<>();
        freeDevelopment = new ArrayList<>();
        freeMarble = new ArrayList<>();
        this.resourcesToPay = new ArrayList<>();
        this.cardsToActivate = new ArrayList<>();
        this.extraProductionToActivate = new ArrayList<>();
        this.resourcesToProduce = new ArrayList<>();
    }

    public void nickNameResult(boolean accepted, boolean first, boolean fullServer) {
        if (accepted) {
            clientHandler.sendMessage(new OkMessage("You're logged in"));
        } else {
            if (fullServer) {
                clientHandler.sendMessage(new ErrorMessage("The game is already started, try again later"));
            } else {
                clientHandler.sendMessage(new InvalidNickname("This nickname is already in use, try another one"));
            }
        }
    }


    public void addFreeResource(Resource resource) {
        this.freeResources.add(resource);
    }

    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }

    public String getNickname() {
        return nickname;
    }

    public void removeFreeResources(int pos) {
        freeResources.remove(pos);
    }

    public ArrayList<Resource> getFreeResources() {
        return freeResources;
    }

    public ArrayList<Marble> getFreeMarble() {
        return freeMarble;
    }

    public ArrayList<DevelopmentCard> getFreeDevelopment() {
        return freeDevelopment;
    }


    public void sendInvalidActionMessage() {
        clientHandler.sendMessage(new ErrorMessage("Not a valid action"));
    }

    public void addFreeMarble(ArrayList<Marble> freeMarble) {
        this.freeMarble = freeMarble;
    }

    public void addFreeDevelopment(DevelopmentCard freeDevelopment) {
        this.freeDevelopment.add(freeDevelopment);
    }

    public void addAllFreeMarbles(ArrayList<Marble> marbles) {
        this.freeMarble.addAll(marbles);
    }

    public void removeAllFreeMarbles() {
        this.freeMarble.removeAll(this.freeMarble);
    }

    public void removeFreeDevelopment(int pos) {
        this.freeDevelopment.remove(pos);
    }


    public void addCardToActivate(int id){
        this.cardsToActivate.add(id);
    }

    public void addAllResourcesToPay(ArrayList<Resource> resources){
        this.resourcesToPay.addAll(resources);
    }

    public void removeCardsToActivate() {
        this.cardsToActivate.removeAll(this.cardsToActivate);
    }

    public void removeResourcesToPay() {
        this.resourcesToPay.removeAll(this.resourcesToPay);
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
        this.extraProductionToActivate.removeAll(this.extraProductionToActivate);
    }

    public void addResourceToProduce(Resource resource){
        this.resourcesToProduce.add(resource);
    }

    public void removeAllResourcesToProduce(){
        this.resourcesToProduce.removeAll(this.resourcesToProduce);
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
}
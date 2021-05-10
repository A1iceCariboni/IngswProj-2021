package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.InvalidNickname;
import it.polimi.ingsw.messages.answer.NumberOfPlayerRequest;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.observers.Observer;

import java.util.ArrayList;

/**
 * class between network and the game controller on the server side , the controller call the methods of this class and this class communicate with the client
 * @author Alice Cariboni
 */
public class VirtualView implements Observer {
    private ClientHandler clientHandler;
    private String nickname ;
    private ArrayList<Resource> freeResources;
    private ArrayList<Marble> freeMarble;
    private ArrayList<DevelopmentCard> freeDevelopment;

    public VirtualView(){}

    public VirtualView(ClientHandler clientHandler, String nickname) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
        freeResources = new ArrayList<>();
        freeDevelopment = new ArrayList<>();
        freeMarble = new ArrayList<>();
    }

    public void nickNameResult(boolean accepted, boolean first, boolean fullServer){
        if(accepted){
                clientHandler.sendMessage(new OkMessage("You're logged in"));
        }else{
            if(fullServer){
                clientHandler.sendMessage(new ErrorMessage("The game is already started, try again later"));
            }else {
                clientHandler.sendMessage(new InvalidNickname("This nickname is already in use, try another one"));
            }
        }
    }


    public void addFreeResource(Resource resource){
        this.freeResources.add(resource);
    }
    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }

    public String getNickname() {
        return nickname;
    }

    public void removeFreeResources(int pos){
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



    public void addFreeMarble(ArrayList<Marble> freeMarble) {
        this.freeMarble = freeMarble;
    }

    public void addFreeDevelopment(DevelopmentCard freeDevelopment) {
        this.freeDevelopment.add(freeDevelopment);
    }
    public void removeFreeDevelopment(int pos) {
        this.freeDevelopment.remove(pos);
    }

}

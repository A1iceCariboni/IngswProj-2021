package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.InvalidNickname;
import it.polimi.ingsw.messages.answer.NumberOfPlayerRequest;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.observers.Observer;

import java.util.ArrayList;

/**
 * class between network and the game controller on the server side , the controller call the methods of this class and this class communicate with the client
 * @author Alice Cariboni
 */
public class VirtualView implements Observer {
    private ClientHandler clientHandler;
    private String nickname ;

    public VirtualView(ClientHandler clientHandler, String nickname) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
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
    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }
}

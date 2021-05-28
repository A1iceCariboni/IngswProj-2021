package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;

import java.io.IOException;

public interface GuiObserver{

    public abstract void onConnectionRequest(String ip, int port) ;
    public abstract void onUpdateNickname(SetupMessage setupMessage);
    public abstract void onReadyReply(Message message);
}

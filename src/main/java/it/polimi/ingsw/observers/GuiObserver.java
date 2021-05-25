package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;

public interface GuiObserver{

    public abstract void onConnectionRequest(String ip, int port);
    public abstract void onUpdateNickname(SetupMessage setupMessage);
    public abstract void onReadyReply(Message message);
}

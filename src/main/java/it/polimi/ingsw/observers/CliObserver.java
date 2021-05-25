package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;

public interface CliObserver{

   void onConnectionRequest(String ip, int port);
   void onUpdateNickname(SetupMessage setupMessage);
   void onReadyReply(Message message);
}

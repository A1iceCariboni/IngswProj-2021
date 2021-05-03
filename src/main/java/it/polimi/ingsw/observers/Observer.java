package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.NumberOfPlayerReply;
import it.polimi.ingsw.messages.request.SetupMessage;

public interface Observer {

   void onConnectionRequest(String ip, int port);
   void onUpdateNickname(SetupMessage setupMessage);
   void onReadyReply(Message message);
   void update(Message message);

}

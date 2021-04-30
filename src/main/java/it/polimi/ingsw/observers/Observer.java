package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.request.ConnectionMessage;

public interface Observer {

  /**
   * method called when there is a request of connection from a client
   * @param message containing ip and port from client
   */
  public void onConnectionRequest(ConnectionMessage message);

}

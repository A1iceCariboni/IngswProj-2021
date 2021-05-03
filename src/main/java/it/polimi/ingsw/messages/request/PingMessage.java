package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

/**
 * ping from client to server
 */
public class PingMessage extends Message{

    public PingMessage() {
        super(MessageType.PING, "");
    }
}

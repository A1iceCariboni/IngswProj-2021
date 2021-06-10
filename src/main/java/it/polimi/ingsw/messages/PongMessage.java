package it.polimi.ingsw.messages;


import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class PongMessage extends Message {
    public PongMessage() {
        super(MessageType.PONG, "");
    }
}

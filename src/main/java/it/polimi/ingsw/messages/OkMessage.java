package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

/**
 * generic ok message
 */
public class OkMessage extends Message {

    public OkMessage(String payload) {
        super(MessageType.OK,payload);
    }
}

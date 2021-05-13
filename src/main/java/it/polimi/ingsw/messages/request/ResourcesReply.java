package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class ResourcesReply extends Message {

    public ResourcesReply(String payload) {
        super(MessageType.CHOOSE_RESOURCES, payload);
    }
}

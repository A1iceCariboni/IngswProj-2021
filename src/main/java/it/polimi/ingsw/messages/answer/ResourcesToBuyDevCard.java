package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class ResourcesToBuyDevCard extends Message {

    public ResourcesToBuyDevCard(String payload) {
        super(MessageType.RESOURCES_BUY_DEV_CARD, payload);
    }
}

package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.cards.LeaderCard;

/**
 * message from client to discard a leader card
 */
public class RemoveLeaderCardMessage extends Message {

    /**
     * constructor to remove a leader card
     * @param payload
     */
    public RemoveLeaderCardMessage(String payload) {
        super(MessageType.DISCARD_LEADER, payload);
    }
}

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
     * @param leaderCard the card the client want to discard
     */
    public RemoveLeaderCardMessage(String payload, LeaderCard leaderCard) {
        super(MessageType.DISCARD_LEADER_CARD, payload);
    }
}

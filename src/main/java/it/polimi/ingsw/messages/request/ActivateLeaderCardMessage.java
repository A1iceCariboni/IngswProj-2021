package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.cards.LeaderCard;

/**
 *  message from client to activate the leader card
 */
public class ActivateLeaderCardMessage extends Message {

    /**
     * constructor to activate a leader card
     * @param payload
     * @param leaderCard the card the client wants to activate
     */
    public ActivateLeaderCardMessage(String payload, LeaderCard leaderCard) {
        super(MessageType.ACTIVATE_LEADER_CARD, payload);
    }

}

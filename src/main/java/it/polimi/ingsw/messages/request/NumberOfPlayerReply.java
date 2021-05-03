package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

/**
 * message from client containinga the nuber of players
 */
public class NumberOfPlayerReply extends Message {

    public NumberOfPlayerReply(String payload) {
        super(MessageType.NUMBER_OF_PLAYERS, payload);
    }
}

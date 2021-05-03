package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

/**
 * massage sent to tell the player that he is the first one logged and he has to choose the number of players
 */
public class NumberOfPlayerRequest extends Message {

    public NumberOfPlayerRequest(String payload) {
        super(MessageType.NUMBER_OF_PLAYERS , payload);
    }
}

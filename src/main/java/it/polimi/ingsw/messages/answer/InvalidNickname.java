package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

/**
 * message sent when the choosen nickname is already taken or has not the right form
 */
public class InvalidNickname extends Message {

    public InvalidNickname(String payload) {
        super(MessageType.ERROR, payload);
    }
}

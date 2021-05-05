package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class ErrorMessage extends Message {
    public ErrorMessage(String payload) {
        super(MessageType.ERROR, payload);
    }
}

package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class FaithTrackMessage extends Message {
    public FaithTrackMessage(String payload) {
        super(MessageType.FAITH_TRACK, payload);
    }
}

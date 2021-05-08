package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class LeaderCardMessage extends Message {
    public LeaderCardMessage(String payload){
        super(MessageType.DUMMY_LEADER_CARD, payload);
    }
}

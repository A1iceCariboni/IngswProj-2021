package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class DevelopmentMarketMessage extends Message {

    public DevelopmentMarketMessage(String payload) {
        super(MessageType.DEVELOPMENT_MARKET, payload);
    }
}

package it.polimi.ingsw.messages.answer;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

public class MarketTrayMessage extends Message {
    public MarketTrayMessage(String payload) {
        super(MessageType.MARKET_TRAY, payload);
    }
}

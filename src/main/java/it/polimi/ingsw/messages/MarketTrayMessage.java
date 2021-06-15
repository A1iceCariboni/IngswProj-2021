package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.model.MarketTray;

public class MarketTrayMessage extends Message{

    public MarketTrayMessage(MarketTray marketTray) {
        super(MessageType.MARKET_TRAY, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(marketTray.getDummy()));
    }

    public DummyMarket getMarket(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), DummyMarket.class);
    }
}

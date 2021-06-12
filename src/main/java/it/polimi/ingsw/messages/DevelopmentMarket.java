package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDev;

/**
 * message sent to player with the dummy market of development cards
 */
public class DevelopmentMarket extends Message{


    public DevelopmentMarket(DummyDev[][] dummyDevs) {
        super(MessageType.DEVELOPMENT_MARKET, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(dummyDevs));
    }

    public DummyDev[][] getDummyDevs() {
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), DummyDev[][].class);
    }
}

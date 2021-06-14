package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyStrongbox;
import it.polimi.ingsw.model.StrongBox;

public class DummyStrongBox extends Message{

    public DummyStrongBox(StrongBox strongbox) {
        super(MessageType.DUMMY_STRONGBOX, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(strongbox.getDummy()));
    }

    public DummyStrongbox getStrongBox(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), DummyStrongbox.class);
    }
}

package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyStrongbox;
import it.polimi.ingsw.model.StrongBox;

public class OtherStrongBox extends Message{

    public OtherStrongBox(StrongBox strongbox) {
        super(MessageType.OTHER_STRONGBOX, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(strongbox.getDummy()));
    }

    public DummyStrongbox getStrongBox(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), DummyStrongbox.class);
    }
}

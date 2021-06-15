package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyFaithTrack;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.Game;

public class FaithTrackMessage extends Message{

    public FaithTrackMessage(FaithTrack faithTrack) {
        super(MessageType.FAITH_TRACK, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(faithTrack));
    }

    public DummyFaithTrack getFaithTrack(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), DummyFaithTrack.class);
    }
}

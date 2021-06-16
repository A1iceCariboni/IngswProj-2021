package it.polimi.ingsw.messages;

import com.google.gson.Gson;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class PlaceResources extends Message{

    public PlaceResources(int[] ids) {
        super(MessageType.PLACE_RESOURCE_WAREHOUSE, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(ids));
    }

    public PlaceResources(ArrayList<String> res) {
        super(MessageType.PLACE_RESOURCE_WAREHOUSE, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(res));
    }

    public int[] getIds(){
        Gson gson = new Gson();

        return gson.fromJson(getPayload(), int[].class);
    }

    public String[] getRes(){
        Gson gson = new Gson();

        return gson.fromJson(getPayload(), String[].class);
    }

}

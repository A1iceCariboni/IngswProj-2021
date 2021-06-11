package it.polimi.ingsw.messages;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BuyDev extends Message{

    public BuyDev(int[] coordinates) {
        super(MessageType.BUY_DEV, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(coordinates));
    }

    public BuyDev(ArrayList<Integer> coordinates) {
        super(MessageType.BUY_DEV, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(coordinates));
    }

    public int[] getCoordinates()
    {
        Gson gson = new Gson();

        return gson.fromJson(getPayload(), int[].class);
    }
}

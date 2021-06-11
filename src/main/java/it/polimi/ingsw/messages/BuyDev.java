package it.polimi.ingsw.messages;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BuyDev extends Message{
    private int[] coordinates;

    public BuyDev(int[] coordinates) {
        super(MessageType.BUY_DEV, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(coordinates));
        this.coordinates = coordinates;
    }

    public BuyDev(ArrayList<Integer> coordinates) {
        super(MessageType.BUY_DEV, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(coordinates));
        this.coordinates = new int[2];

        this.coordinates[0] = coordinates.get(0);
        this.coordinates[1] = coordinates.get(1);
    }

    public int[] getCoordinates() {
        return this.coordinates;
    }
}

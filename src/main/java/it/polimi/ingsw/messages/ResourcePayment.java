package it.polimi.ingsw.messages;

import com.google.gson.Gson;

public class ResourcePayment extends Message{

    public ResourcePayment(String[] res) {
        super(MessageType.RESOURCE_PAYMENT, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(res));
    }

    public ResourcePayment(int[] ids) {
        super(MessageType.RESOURCE_PAYMENT, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(ids));
    }

    public String[] getRes(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), String[].class);
    }

    public int[] getIds(){
        Gson gson = new Gson();

        return gson.fromJson(getPayload(), int[].class);
    }
}

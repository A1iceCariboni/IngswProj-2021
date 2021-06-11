package it.polimi.ingsw.messages;

import com.google.gson.Gson;

public class ActivateBaseProd extends Message{

    public ActivateBaseProd(String[] command) {
        super(MessageType.BASE_PRODUCTION, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(command));
    }

    public String[] getCommand() {
        Gson gson = new Gson();

        return gson.fromJson(getPayload(), String[].class);
    }
}

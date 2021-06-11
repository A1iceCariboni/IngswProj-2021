package it.polimi.ingsw.messages;

import com.google.gson.Gson;

public class ActivateBaseProd extends Message{
    private String[] command;

    public ActivateBaseProd(String[] command) {
        super(MessageType.BASE_PRODUCTION, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(command));
        this.command = command;
    }

    public String[] getCommand() {
        return command;
    }
}

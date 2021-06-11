package it.polimi.ingsw.messages;

import com.google.gson.Gson;

/**
 * message sent to activate a production from a development card
 */
public class ActivateDevProd extends Message{

    public ActivateDevProd(int[] ids) {
        super(MessageType.ACTIVATE_PRODUCTION, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(ids));
    }

    public int[] getIds() {
        Gson gson = new Gson();

        return gson.fromJson(getPayload(), int[].class);
    }

}

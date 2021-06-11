package it.polimi.ingsw.messages;

import com.google.gson.Gson;

/**
 * message sent to activate a production from a development card
 */
public class ActivateDevProd extends Message{
    private int[] ids;

    public ActivateDevProd(int[] ids) {
        super(MessageType.ACTIVATE_PRODUCTION, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(ids));
        this.ids = ids;
    }

    public int[] getIds() {
        return this.ids;
    }

}

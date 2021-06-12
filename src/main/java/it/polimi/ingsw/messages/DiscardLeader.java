package it.polimi.ingsw.messages;

import com.google.gson.Gson;
/**
 * id of the leader card the player wants to discard
 */
public class DiscardLeader extends Message{

    public DiscardLeader(int id) {
        super(MessageType.DISCARD_LEADER, Integer.toString(id));
    }

    public int getIds(){
        return Integer.parseInt(getPayload());
    }
}

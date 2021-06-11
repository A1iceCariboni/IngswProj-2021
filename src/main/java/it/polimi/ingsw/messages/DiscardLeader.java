package it.polimi.ingsw.messages;

import com.google.gson.Gson;
/**
 * id of the leader card the player wants to discard
 */
public class DiscardLeader extends Message{
    private int id;
    public DiscardLeader(int id) {
        super(MessageType.DISCARD_LEADER, Integer.toString(id));
        this.id = id;
    }

    public int getIds(){
        return id;
    }
}

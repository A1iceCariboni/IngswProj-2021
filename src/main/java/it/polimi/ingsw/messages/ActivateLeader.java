package it.polimi.ingsw.messages;

public class ActivateLeader extends Message{
    private int id;

    public ActivateLeader(int id) {
        super(MessageType.ACTIVATE_LEADER_CARD, Integer.toString(id));
        this.id = id;
    }

    public int getId(){
        return id;
    }
}

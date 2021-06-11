package it.polimi.ingsw.messages;

public class ActivateLeader extends Message{

    public ActivateLeader(int id) {
        super(MessageType.ACTIVATE_LEADER_CARD, Integer.toString(id));
    }

    public int getId(){
        return Integer.parseInt(getPayload());
    }
}

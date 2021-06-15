package it.polimi.ingsw.messages;

public class FaithMove extends Message{
    public FaithMove(int pos) {
        super(MessageType.FAITH_MOVE, Integer.toString(pos));
    }

    public int getPos(){
        return Integer.parseInt(getPayload());
    }
}

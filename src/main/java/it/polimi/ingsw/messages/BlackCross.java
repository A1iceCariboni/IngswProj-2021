package it.polimi.ingsw.messages;

public class BlackCross extends Message{

    public BlackCross(int pos) {
        super(MessageType.BLACK_CROSS, Integer.toString(pos));
    }

    public int getPos(){
        return Integer.parseInt(getPayload());
    }
}

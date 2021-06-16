package it.polimi.ingsw.messages;

public class OtherFaithMarker extends Message{

    public OtherFaithMarker(int pos) {
        super(MessageType.OTHER_FAITHMARKER, Integer.toString(pos));
    }

    public int getPos(){
        return Integer.parseInt(getPayload());
    }
}

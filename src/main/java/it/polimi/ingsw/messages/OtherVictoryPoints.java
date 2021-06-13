package it.polimi.ingsw.messages;

public class OtherVictoryPoints extends Message{

    public OtherVictoryPoints(int points) {
        super(MessageType.OTHER_VICTORY_POINTS, Integer.toString(points));
    }

    public int getVictory(){
        return Integer.parseInt(getPayload());
    }
}

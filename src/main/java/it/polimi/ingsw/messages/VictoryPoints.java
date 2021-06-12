package it.polimi.ingsw.messages;

public class VictoryPoints extends Message{

    public VictoryPoints(int points) {
        super(MessageType.VICTORY_POINTS, Integer.toString(points));
    }

    public int getPoints(){
        return Integer.parseInt(getPayload());
    }
}

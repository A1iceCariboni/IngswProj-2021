package it.polimi.ingsw.messages;

public class VictoryPoints extends Message{
    int points;
    public VictoryPoints(int points) {
        super(MessageType.VICTORY_POINTS, Integer.toString(points));
        this.points = points;
    }

    public int getPoints(){
        return points;
    }
}

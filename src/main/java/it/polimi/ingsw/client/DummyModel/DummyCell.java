package it.polimi.ingsw.client.DummyModel;

public class DummyCell {
    private final boolean isPopeSpace;
    private final int victoryPoints;
    private final int pointsForPopeSpace;
    private final int reportSection;
    private final int number;

    public DummyCell(boolean isPopeSpace, int victoryPoints, int pointsForPopeSpace, int reportSection, int number) {
        this.isPopeSpace = isPopeSpace;
        this.victoryPoints = victoryPoints;
        this.pointsForPopeSpace = pointsForPopeSpace;
        this.reportSection = reportSection;
        this.number = number;
    }

    @Override
    public String toString() {
        return "DummyCell{" +
                "isPopeSpace=" + isPopeSpace +
                ", victoryPoints=" + victoryPoints +
                ", pointsForPopeSpace=" + pointsForPopeSpace +
                ", reportSection=" + reportSection +
                ", number=" + number +
                '}';


    }

    public boolean isPopeSpace() {
        return isPopeSpace;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }


    public int getPointsForPopeSpace() {
        return pointsForPopeSpace;
    }

    public int getReportSection() {
        return reportSection;
    }

    public int getNumber() {
        return number;
    }
}

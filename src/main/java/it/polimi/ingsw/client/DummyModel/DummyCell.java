package it.polimi.ingsw.client.DummyModel;

public class DummyCell {
    private boolean isPopeSpace;
    private final int victoryPoints;
    private boolean isReportSection;
    private int pointsForPopeSpace;
    private int reportSection;
    private int number;

    public DummyCell(boolean isPopeSpace, int victoryPoints, boolean isReportSection, int pointsForPopeSpace, int reportSection, int number) {
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
                ", isReportSection=" + isReportSection +
                ", pointsForPopeSpace=" + pointsForPopeSpace +
                ", reportSection=" + reportSection +
                ", number=" + number +
                '}';
    }
}

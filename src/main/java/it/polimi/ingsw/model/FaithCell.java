package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * this class represents a single cell of the faith track
 * @author Alice Cariboni
 */
public class FaithCell implements Serializable {
    private static final long serialVersionUID = -2853218142037035039L;

    private  boolean isPopeSpace;
    private final int victoryPoints;
    private int pointsForPopeSpace;
    private int reportSection;
    private int number;

    public FaithCell(boolean isPopeSpace, int victoryPoints, int pointsForPopeSpace, int reportSection, int number) {
        this.isPopeSpace = isPopeSpace;
        this.victoryPoints = victoryPoints;
        this.pointsForPopeSpace = pointsForPopeSpace;
        this.reportSection = reportSection;
        this.number = number;
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

    public void setPopeSpace(boolean popeSpace) {
        isPopeSpace = popeSpace;
    }

    public void setPointsForPopeSpace(int pointsForPopeSpace) {
        this.pointsForPopeSpace = pointsForPopeSpace;
    }

    public int getReportSection() {
        return reportSection;
    }

    public void setReportSection(int reportSection) {
        this.reportSection = reportSection;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

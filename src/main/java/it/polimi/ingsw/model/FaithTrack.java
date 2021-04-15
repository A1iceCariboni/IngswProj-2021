package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * @author Sofia Canestraci
 * the class controls the faith track of the game
 */
public class FaithTrack {
    private final boolean[] isPopeSpace = new boolean[24];
    private final int[] victoryPoints = new int[24];
    private final int[] pointsForPopeSpace = new int[3];
    private boolean isReportSection1;
    private boolean isReportSection2;
    private boolean isReportSection3;

    public FaithTrack(){
        for(int r=0; r<24; r++){
            if (r == 7 || r == 15 || r == 23){
                isPopeSpace[r] = true;
            }
            else isPopeSpace[r] = false;
        }
        for (int i=0; i<24; i++) {
            victoryPoints[i] = 0;
        }
        victoryPoints[2] = 1;
        victoryPoints[3] = 1;
        victoryPoints[4] = 1;
        victoryPoints[5] = 2;
        victoryPoints[6] = 2;
        victoryPoints[7] = 2;
        victoryPoints[8] = 4;
        victoryPoints[9] = 4;
        victoryPoints[10] = 4;
        victoryPoints[11] = 6;
        victoryPoints[12] = 6;
        victoryPoints[13] = 6;
        victoryPoints[14] = 9;
        victoryPoints[15] = 9;
        victoryPoints[16] = 9;
        victoryPoints[17] = 12;
        victoryPoints[18] = 12;
        victoryPoints[19] = 12;
        victoryPoints[20] = 16;
        victoryPoints[21] = 16;
        victoryPoints[22] = 16;
        victoryPoints[23] = 20;

        pointsForPopeSpace[0] = 2;
        pointsForPopeSpace[1] = 3;
        pointsForPopeSpace[2] = 4;

        isReportSection1 = true;
        isReportSection2 = true;
        isReportSection3 = true;
    }

    /**
     * it controls if the position pos is contained in a report section
     * @param pos position of the faith market of the player
     * @return true if the report section is active
     */
    public boolean isReportSection (int pos) {
        boolean b = false;
        if (pos>4 && pos<9){
            b = isReportSection1;
        }
        if (pos>11 && pos<17){
            b = isReportSection2;
        }
        if (pos>18 && pos<25){
            b = isReportSection3;
        }

        return b;
    }

    /**
     * it deactivates a pope space
     * @param space the space where is the faith marker of the player
     */
    public void deactivatePopeSpace (int space){
        isPopeSpace[space] = false;
    }

    /**
     * it checks if the faith marker of the player is on a pope space
     * @param pos position of the faith marker of the player
     * @return true if the faith marker of the player is on a pope space
     */
    public boolean isPopeSpace (int pos) {
        if (isPopeSpace[pos]){
            deactivatePopeSpace(pos);
            deactivateSection(pos);
            return true;
        }
        else return false;
    }

    /**
     * it returns the victory points that are at the position of the faith marker of the player on the faith track
     * @param pos position of the faith marker of the player
     * @return victoryPoints[]:the victory points that are at the position of the faith track
     */
    public int getLastVictoryPoint (int pos){ return victoryPoints[pos];}

    public void deactivateSection(int pos){
        if (pos>4 && pos<9){
            isReportSection1 = false;
        }
        if (pos>11 && pos<17){
            isReportSection2 = false;
        }
        if (pos>18 && pos<25){
            isReportSection3 = false;
        }
    }

    /**
     * it returns the points for the pope space if the faith marker of the player is in a correct position
     * @param pos position of the faith marker of the player
     * @return i: the value of the victory points of the pope space
     */
    public int getPointsForPope(int pos){
        int i = 0;
        if (pos>4 && pos<9){
            i = pointsForPopeSpace[0];
        }
        if (pos>11 && pos<17){
            i = pointsForPopeSpace[1];
        }
        if (pos>18 && pos<=24){
            i = pointsForPopeSpace[2];
        }

        return i;
    }

}


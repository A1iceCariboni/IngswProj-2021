package it.polimi.ingsw.model;

import it.polimi.ingsw.client.DummyModel.DummyFaithTrack;
import it.polimi.ingsw.utility.FaithTrackParser;

import java.util.ArrayList;

/**
 * @author Sofia Canestraci
 * the class controls the faith track of the game
 */
public class FaithTrack {
    private FaithCell[] faithTrack;

    public FaithTrack(){
        this.faithTrack = FaithTrackParser.parseFaithTrack();
    }

    /**
     * it controls if the position pos is contained in a report section
     * @param pos position of the faith market of the player
     * @return true if the report section is active
     */
    public boolean isReportSection (int pos) {
        return faithTrack[pos].getReportSection()!=0;
    }

    /**
     * it deactivates a pope space
     * @param space the space where is the faith marker of the player
     */
    public void deactivatePopeSpace (int space){
        this.faithTrack[space].setPopeSpace(false);
    }

    /**
     * it checks if the faith marker of the player is on a pope space
     * @param pos position of the faith marker of the player
     * @return true if the faith marker of the player is on a pope space
     */
    public boolean isPopeSpace (int pos) {
        return faithTrack[pos].isPopeSpace();
    }

    /**
     * it returns the victory points that are at the position of the faith marker of the player on the faith track
     * @param pos position of the faith marker of the player
     * @return victoryPoints[]:the victory points that are at the position of the faith track
     */
    public int getLastVictoryPoint (int pos){ return faithTrack[pos].getVictoryPoints();}

    public void deactivateSection(int pos){
       int sec = faithTrack[pos].getReportSection();
       for(FaithCell faithCell : this.faithTrack){
           if(faithCell.getReportSection() == sec){
               faithCell.setReportSection(0);
               faithCell.setReportSection(0);
               faithCell.setPointsForPopeSpace(0);
           }
       }
    }

    /**
     * it returns the points for the pope space if the faith marker of the player is in a correct position
     * @param pos position of the faith marker of the player
     * @return i: the value of the victory points of the pope space
     */
    public int getPointsForPope(int pos){
       return faithTrack[pos].getPointsForPopeSpace();
    }


}


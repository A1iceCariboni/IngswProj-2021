package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.cards.effects.LeaderEffect;
import it.polimi.ingsw.model.cards.requirements.Requirement;

import java.util.ArrayList;

/**
 * This class represents a LeaderCard
 * @author Alice Cariboni
 */
public class LeaderCard extends Card{

private boolean isActive;
private final LeaderEffect leaderEffect;
private final int victoryPoints;
private final ArrayList<Requirement> requirements;

    public LeaderCard( LeaderEffect leaderEffect, int victoryPoints, ArrayList<Requirement> requirements) {
        this.isActive = false;
        this.leaderEffect = leaderEffect;
        this.victoryPoints = victoryPoints;
        this.requirements = requirements;
    }

    /**
     * @return true if the player who owns the card has activated it false if the card is in the hand of the player or discarded
     */
    public boolean isActive(){
        return isActive;
    }

    /**
     * this method make the leadercard active for the player who owns it
     */
    public void active(){
        this.isActive = true;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    /**
     * @param b the playerboard of the player who wants to activate the card
     * @return true if the player has the requirements flase if he doesn't
     */
    public boolean isActivableBy(PlayerBoard b){
        for(Requirement r : requirements){
            if(!r.hasEnough(b)){
                return false;
            }
        }
        return true;
    }


}


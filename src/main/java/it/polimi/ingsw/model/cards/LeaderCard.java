package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.cards.effects.LeaderEffect;
import it.polimi.ingsw.model.cards.requirements.Requirement;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents a LeaderCard
 * @author Alice Cariboni
 */
public class LeaderCard extends Card {

    private boolean isActive;
    private final LeaderEffect leaderEffect;
    private final int victoryPoints;
    private final ArrayList<Requirement> requirements;
    private final int id;



    public LeaderCard(int id, LeaderEffect leaderEffect, int victoryPoints, ArrayList<Requirement> requirements) {
        this.isActive = false;
        this.id = id;
        this.leaderEffect = leaderEffect;
        this.victoryPoints = victoryPoints;
        this.requirements = requirements;
    }

    /**
     * @return true if the player who owns the card has activated it false if the card is in the hand of the player or discarded
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * this method make the leadercard active for the player who owns it
     */
    public void active(Player p, PlayerBoard b) {
        this.isActive = true;
        leaderEffect.applyEffect(p,b);
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    /**
     * @param b the playerboard of the player who wants to activate the card
     * @return true if the player has the requirements flase if he doesn't
     */
    public boolean isActivableBy(PlayerBoard b) {
        for (Requirement r : requirements) {
            if (!r.hasEnough(b)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderCard that = (LeaderCard) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isActive, leaderEffect, victoryPoints, requirements, id);
    }

    public int getId() {
        return id;
    }


    public DummyLeaderCard getDummy() {
        ArrayList<String> req = new ArrayList<>();

        for (Requirement requirement : requirements) {
            req.add(requirement.toString());
        }
        return new DummyLeaderCard(id, req, isActive, leaderEffect.toString(), victoryPoints);
    }
    public int getVictoryPoints() {
        return this.victoryPoints;
    }
}


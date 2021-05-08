package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyLeaderCard {
    int id;
    ArrayList<String> requirements;
    boolean isActive;
    String effect;
    int victoryPoints;

    public DummyLeaderCard(int id, ArrayList<String> requirements, boolean isActive, String effect, int victoryPoints) {
        this.id = id;
        this.requirements = requirements;
        this.isActive = isActive;
        this.effect = effect;
        this.victoryPoints = victoryPoints;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getEffect() {
        return effect;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public String toString() {
        return "DummyLeaderCard{" +
                "id=" + id +
                ", requirements=" + requirements +
                ", isActive=" + isActive +
                ", effect='" + effect + '\'' +
                ", victoryPoints=" + victoryPoints +
                '}';
    }
}

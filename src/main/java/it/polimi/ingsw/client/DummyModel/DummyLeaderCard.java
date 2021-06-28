package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyLeaderCard {
    int id;
    ArrayList<String> requirements;
    boolean isActive;
    String effect;
    String effectName;
    int victoryPoints;
    String type;


    public DummyLeaderCard(int id, ArrayList<String> requirements, boolean isActive, String effect, int victoryPoints, String effectName, String type) {
        this.id = id;
        this.requirements = requirements;
        this.isActive = isActive;
        this.effect = effect;
        this.victoryPoints = victoryPoints;
        this.effectName = effectName;
        this.type = type;
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

    public void setIsActive(boolean set) {
        this.isActive = set;
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

    public String getEffectName() {
        return this.effectName;
    }

    public String getType() {
        return this.type;
    }
}

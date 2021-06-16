package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyLeaderCard {
    int id;
    ArrayList<String> requirements;
    boolean isActive;
    String effect;
    int victoryPoints;

    public void setExtraProduction(DummyExtraProduction extraProduction) {
        this.extraProduction = extraProduction;
    }

    public DummyExtraProduction getExtraProduction() {
        return extraProduction;
    }

    DummyExtraProduction extraProduction;
    ArrayList<String> entry;

    public void setEntry(ArrayList<String> entry) {
        this.entry = entry;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    int eid;

    public DummyLeaderCard(int id, ArrayList<String> requirements, boolean isActive, String effect, int victoryPoints) {
        this.id = id;
        this.requirements = requirements;
        this.isActive = isActive;
        this.effect = effect;
        this.victoryPoints = victoryPoints;
        this.extraProduction = new DummyExtraProduction(entry, eid);
        this.entry = new ArrayList<>();
        this.eid= 0;
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
}

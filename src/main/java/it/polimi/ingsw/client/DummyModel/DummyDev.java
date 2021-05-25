package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyDev {
private final int id;
private final ArrayList<String> cost;
private final int level;
private final String color;
private final DummyProductionPower productionPower;

    public ArrayList<String> getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public String getColor() {
        return color;
    }

    public DummyProductionPower getProductionPower() {
        return productionPower;
    }

    public DummyDev(int id, ArrayList<String> cost, int level, String color, DummyProductionPower productionPower) {
        this.id = id;
        this.cost = cost;
        this.level = level;
        this.color = color;
        this.productionPower = productionPower;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "DummyDev{" +
                "id=" + id +
                ", cost=" + cost +
                ", level=" + level +
                ", color='" + color + '\'' +
                ", productionPower=" + productionPower +
                '}';
    }
}


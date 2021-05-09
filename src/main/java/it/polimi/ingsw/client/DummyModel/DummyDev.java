package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyDev {
private int id;
private ArrayList<String> cost;
private int level;
private String color;
private DummyProductionPower productionPower;

    public DummyDev(int id, ArrayList<String> cost, int level, String color, DummyProductionPower productionPower) {
        this.id = id;
        this.cost = cost;
        this.level = level;
        this.color = color;
        this.productionPower = productionPower;
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


package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyProductionPower {
    private final ArrayList<String> entry;
    private final ArrayList<String> prod;

    public DummyProductionPower(ArrayList<String> entry, ArrayList<String> prod) {
        this.entry = entry;
        this.prod = prod;
    }

    @Override
    public String toString() {
        return
                "entry=" + entry +
                ", prod=" + prod +
                '}';
    }
}

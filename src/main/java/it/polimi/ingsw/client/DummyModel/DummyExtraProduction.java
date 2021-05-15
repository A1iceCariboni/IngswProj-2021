package it.polimi.ingsw.client.DummyModel;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class DummyExtraProduction {
    private int id;
    private ArrayList<String> entryResources;


    public DummyExtraProduction(ArrayList<String> entryResources, int id) {
        this.entryResources = entryResources;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getEntryResources() {
        return entryResources;
    }
}

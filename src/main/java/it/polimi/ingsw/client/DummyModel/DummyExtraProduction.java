package it.polimi.ingsw.client.DummyModel;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class DummyExtraProduction {
    private int id;
    private ArrayList<Resource> entryResources;


    public DummyExtraProduction(ArrayList<Resource> entryResources, int id) {
        this.entryResources = entryResources;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Resource> getEntryResources() {
        return entryResources;
    }
}

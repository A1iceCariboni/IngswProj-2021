package it.polimi.ingsw.client.DummyModel;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class DummyDepot {
    protected int id;
    protected int dimension;
    protected ArrayList<Resource> resources;

    public DummyDepot(int id, int dimension, ArrayList<Resource> resources) {
        this.id = id;
        this.dimension = dimension;
        this.resources = resources;
    }
}

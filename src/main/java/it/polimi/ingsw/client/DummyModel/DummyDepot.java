package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyDepot {
    protected int id;
    protected int dimension;
    protected ArrayList<String> resources;

    public DummyDepot(int id, int dimension, ArrayList<String> resources) {
        this.id = id;
        this.dimension = dimension;
        this.resources = resources;
    }


    public void addResource(String resource){
        resources.add(resource);
    }

    public ArrayList<String> getResources(){
        return resources;
    }

    public int getId(){
        return id;
    }


    public int getDimension() {
        return dimension;
    }

    public void setResources(ArrayList<String> resources) {
        this.resources = resources;
    }
}

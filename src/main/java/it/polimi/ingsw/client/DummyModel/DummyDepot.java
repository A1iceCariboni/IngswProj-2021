package it.polimi.ingsw.client.DummyModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

import static it.polimi.ingsw.enumerations.Constants.*;
import static it.polimi.ingsw.enumerations.Constants.ANSI_RESET;

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

package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyExtraDepot extends DummyDepot{
    private final String resourceType;

    public DummyExtraDepot(int id, int dimension,ArrayList<String> resources,String resourceType){
        super(id, dimension, resources);
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }





}

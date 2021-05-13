package it.polimi.ingsw.client.DummyModel;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class DummyExtraDepot extends DummyDepot{
    private ResourceType resourceType;

    public DummyExtraDepot(int id, int dimension, ArrayList<String> resources, ResourceType resourceType) {
        super(id, dimension, resources);
        this.resourceType = resourceType;
    }

}

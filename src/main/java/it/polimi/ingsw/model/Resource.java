package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.ResourceType;

import java.io.Serializable;

public class Resource implements Serializable {
    private static final long serialVersionUID = -8267728156211412994L;

    private final ResourceType resourceType;


    public Resource(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Resource)) {
            return false;
        }
        Resource otherResource = (Resource)other;
        return otherResource.getResourceType().equals(getResourceType());
    }

    @Override
    public String toString() {
        return  resourceType.name();
    }
}

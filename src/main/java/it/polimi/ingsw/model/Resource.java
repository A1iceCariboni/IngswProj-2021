package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.ResourceType;

public class Resource {
    private ResourceType resourceType;


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

}

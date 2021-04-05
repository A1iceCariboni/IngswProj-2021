package it.polimi.ingsw.model.cards.requirements;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/**
 *  this class represent the requirement of a leader card that needs a certain number of resources owned by the player who wants
 *  to activate it
 * @author Alice Cariboni
 */
public class ResourceReq implements Requirement{
    private final ResourceType resourceType;
    private final int quantity;

    public ResourceReq(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    @Override
    public boolean hasEnough(PlayerBoard b) {
        ArrayList<Resource> res = b.getResources();
        long c = res.stream()
                    .filter(r -> r.getResourceType().equals(resourceType))
                    .count();
        return c >= quantity;
    }
}
package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.*;

import java.io.Serializable;

/**
 * this effect is an extra slot in the wharehouse only for resource of type resourceType
 */
public class ExtraSlot implements LeaderEffect, Serializable {
    private static final long serialVersionUID = -3261195465205181917L;

    private final ResourceType resourceType;
    private final int quantity;


    public ExtraSlot(ResourceType resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    @Override
    public void applyEffect(Player p, PlayerBoard b) {
        ExtraDepot extraDepot = new ExtraDepot(quantity,b.getIdForDepot(),resourceType);
        b.getWareHouse().setExtraDepot(extraDepot);
    }

    @Override
    public String getEffectName() {
        return "EXTRA_SLOT";
    }

    @Override
    public String getType() {
        return resourceType.name();
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ExtraSlot{" +
                "resourceType=" + resourceType.toString() +
                ", quantity=" + quantity +
                '}';
    }
}

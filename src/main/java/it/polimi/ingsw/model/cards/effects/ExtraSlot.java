package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.*;

/**
 * this effect is an extra slot in the wharehouse only for resource of type resourceType
 */
public class ExtraSlot implements LeaderEffect{
    private final Resource resourceType;
    private final int quantity;


    public ExtraSlot(Resource resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    @Override
    public void applyEffect(Player p, PlayerBoard b) {
        ExtraDepot extraDepot = new ExtraDepot(quantity,resourceType);
        b.addExtraDepot(extraDepot);
    }

    public Resource getResourceType() {
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

package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this effect is an extra production power that produces a resource resourcetype and a faith point with any resource type as entry resource
 * @author Alice Cariboni
 */
public class ExtraProductionPower implements LeaderEffect, Serializable {
    private static final long serialVersionUID = 2437025212007881053L;

    private final int id;
    private final Resource resourceType;
    private final int quantity;


    public ExtraProductionPower(Resource resourceType, int quantity, int id) {
        this.resourceType = resourceType;
        this.quantity = quantity;
        this.id = id;
    }

    @Override
    public void applyEffect(Player p, PlayerBoard b) {
        ArrayList<Resource> entryResources = new ArrayList<>();
        for(int i = 0; i<quantity; i++){
            entryResources.add(resourceType);
        }
        ExtraProduction extraProduction = new ExtraProduction(entryResources,id);
        p.addExtraProductionPowers(extraProduction);
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ExtraProductionPower{" + "id: " + id +
                "productResources=" + resourceType.toString() + " + 1 faith point " +
                ", quantity=" + quantity +
                '}';
    }

    public int getId() {
        return this.id;
    }
}

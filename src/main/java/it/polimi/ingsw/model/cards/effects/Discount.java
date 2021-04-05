package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

/**
 * this effect is a discount on the resource resourceType applied when the player wants to buy a developent card
 * author Alice Cariboni
 */
public class Discount implements LeaderEffect{
    private final Resource resourceType;
    private final int quantity;


    public Discount(Resource resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

    @Override
    public void applyEffect(Player p) {

    }

    public Resource getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }
}

package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.enumerations.PlayerMove;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.io.Serializable;

/**
 * this effect is a discount on the resource resourceType applied when the player wants to buy a developent card
 * author Alice Cariboni
 */
public class Discount implements LeaderEffect, Serializable {
    private static final long serialVersionUID = 5266471142496650037L;

    private final Resource resourceType;
    private final int quantity;


    public Discount(Resource resourceType, int quantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
    }

   @Override
    public void applyEffect(Player p, PlayerBoard b) {
        for(int i = 0; i<quantity; i++) {
            p.addDiscountedResource(resourceType);
        }
    }

    @Override
    public String getEffectName() {
        return "DISCOUNT";
    }

    @Override
    public String getType() {
        return (resourceType.getResourceType()).name();
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "resourceType=" + resourceType.toString() +
                ", quantity=" + quantity +
                '}';
    }
}

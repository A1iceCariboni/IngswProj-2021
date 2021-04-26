package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.enumerations.PlayerMove;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;

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
    public void applyEffect(Player p, PlayerBoard b) {
        for(int i = 0; i<quantity; i++) {
            p.addDiscountedResource(resourceType);
        }
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }
}

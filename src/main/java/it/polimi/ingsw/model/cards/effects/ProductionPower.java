package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Alice Cariboni
 * This class represents the production power of a development card
 */
public class ProductionPower implements Serializable {
    private static final long serialVersionUID = 8727605790420008920L;

    private ArrayList<Resource> entryResources;
    private ArrayList<Resource> productResources;

    public ProductionPower(ArrayList<Resource> entryResources, ArrayList<Resource> productResources) {
        this.entryResources = entryResources;
        this.productResources = productResources;
    }

    public ProductionPower() {

    }


    /**
     * this method checks if a player has enough resources in his warehouse or strongbox to activate the production power
     *
     * @param b is the player board of the player who wants to activate the power
     * @return true is the player can activate the power, false if he hasn't enough resources
     */
    public boolean isActivable(PlayerBoard b) {
        ArrayList<Resource> res = b.getResources();
        for (ResourceType resource : ResourceType.values()) {
            long playerHas = res.stream()
                    .filter(r -> r.getResourceType().equals(resource))
                    .count();
            long req = entryResources.stream()
                    .filter(r -> r.getResourceType().equals(resource))
                    .count();
            if (playerHas < req) {
                return false;
            }
        }
        return true;
    }



    public ArrayList<Resource> getEntryResources() {
        return entryResources;
    }

    public ArrayList<Resource> getProductResources() {
        return productResources;
    }
}

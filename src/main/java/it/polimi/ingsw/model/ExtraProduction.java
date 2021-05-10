package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.cards.effects.ProductionPower;

import java.util.ArrayList;

/**
 * an extra production power is a special production power given by a leadercard effect
 * the player can choose th resource he wants to produce
 */
public class ExtraProduction extends ProductionPower {

    private ArrayList<Resource> entryResources;


    public ExtraProduction(ArrayList<Resource> entryResources) {
        this.entryResources = entryResources;
    }


    /**
     * this method apply the production to a playerboard, it removes the resources that are required to start the production
     * and add to the strongbox the resources that are produced and a faith point
     * @param b playerboard in which che power is applied
     * @param p player who has activated the power
     * @param productResource the resources that the player wants to produce
     */
    public void startProduction(PlayerBoard b, Player p, Resource productResource) {
        p.addVictoryPoints(1);
        ArrayList<Resource> productResources = new ArrayList<>();
        productResources.add(productResource);
        for (Resource productResourceElem : productResources) {
            b.getStrongBox().addResources(productResourceElem);
        }
    }

    @Override
    public ArrayList<Resource> getEntryResources() {
        return entryResources;
    }
}

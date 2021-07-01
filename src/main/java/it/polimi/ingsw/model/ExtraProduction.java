package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.cards.effects.ProductionPower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * an extra production power is a special production power given by a leadercard effect
 * the player can choose th resource he wants to produce
 */
public class ExtraProduction implements  Serializable{
    private static final long serialVersionUID = -1219837209023544815L;
    private final int id;
    private final ArrayList<Resource> entryResources;


    public ExtraProduction(ArrayList<Resource> entryResources, int id) {
        this.entryResources = entryResources;
        this.id = id;
    }


    /**
     * this method apply the production to a playerboard, it removes the resources that are required to start the production
     * and add to the strongbox the resources that are produced and a faith point
     * @param b playerboard in which che power is applied
     * @param p player who has activated the power
     * @param productResource the resources that the player wants to produce
     */
    public void startProduction(PlayerBoard b, Player p, Resource productResource) {
        p.getPlayerBoard().moveFaithMarker(1);
        ArrayList<Resource> productResources = new ArrayList<>();
        productResources.add(productResource);
        for (Resource productResourceElem : productResources) {
            b.getStrongBox().addResources(productResourceElem);
        }
    }

    public ArrayList<Resource> getEntryResources() {
        return entryResources;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraProduction that = (ExtraProduction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entryResources);
    }
}

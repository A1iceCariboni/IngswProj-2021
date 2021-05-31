package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;

import java.io.Serializable;

/**
 * this effect gives the indicated resource when the chosen column/row of the market gives white marbles
 * @author Alice Cariboni
 */
public class JollyMarble implements LeaderEffect, Serializable {
    private static final long serialVersionUID = -2626652660894446194L;

    private final Resource resourceType;


    public JollyMarble(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public void applyEffect(Player p, PlayerBoard b) {
    p.addPossibleWhiteMarbles(resourceType);
    }

    public Resource getResourceType() {
        return resourceType;
    }


    @Override
    public String toString() {
        return "JollyMarble{" +
                "resourceType=" + resourceType +
                '}';
    }
}

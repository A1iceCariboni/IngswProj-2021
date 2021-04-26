package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;

/**
 * this effect gives the indicated resource when the chosen column/row of the market gives white marbles
 * @author Alice Cariboni
 */
public class JollyMarble implements LeaderEffect{
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

}

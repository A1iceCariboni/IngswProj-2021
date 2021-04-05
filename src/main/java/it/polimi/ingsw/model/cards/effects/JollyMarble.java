package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

/**
 * this effect gives the indicated resource when the chosen column/row of the market gives white marbles
 * @author Alice Cariboni
 */
public class JollyMarble implements LeaderEffect{
    private final Resource resourceType;
    private boolean useThis;


    public JollyMarble(Resource resourceType, int quantity) {
        this.resourceType = resourceType;
        this.useThis = false;
    }

    @Override
    public void applyEffect(Player p) {

    }

    public Resource getResourceType() {
        return resourceType;
    }

    public void useThis(){
        this.useThis = true;
    }

    public void dontUseThis(){
        this.useThis = false;
    }
}

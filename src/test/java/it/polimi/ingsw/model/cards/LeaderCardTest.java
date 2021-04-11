package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.effects.JollyMarble;
import it.polimi.ingsw.model.cards.effects.LeaderEffect;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import it.polimi.ingsw.model.cards.requirements.ColorReq;
import it.polimi.ingsw.model.cards.requirements.LevelReq;
import it.polimi.ingsw.model.cards.requirements.Requirement;
import it.polimi.ingsw.model.cards.requirements.ResourceReq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Cariboni
 */
class TempPlayerBoard2 extends PlayerBoard{
    private ArrayList<Resource> res;
    private ArrayList<DevelopmentCard> dev;


    public TempPlayerBoard2(ArrayList<Resource> res, ArrayList<DevelopmentCard> dev) {
        this.res = res;
        this.dev = dev;
    }
    @Override
    public ArrayList<DevelopmentCard> getDevCards() {
        return dev;
    }

    @Override
    public void removeResources(ArrayList<Resource> entryResources) {
        this.res.removeAll(entryResources);
    }

    @Override
    public ArrayList<Resource> getResources() {
        return res;
    }

    @Override
    public void addStrongBox(ArrayList<Resource> productResources) {
        this.res.addAll(productResources);
    }
}


class LeaderCardTest {
    private static PlayerBoard b;
    private static LeaderCard leaderCard;

    @BeforeAll
    public static void init(){
        ArrayList<Resource> cost = new ArrayList<>();
        ArrayList<Resource> entry = new ArrayList<>();
        ArrayList<Resource> prod= new ArrayList<>();
        ArrayList<Resource> res = new ArrayList<>();
        ProductionPower pp;
        ArrayList<DevelopmentCard> dev = new ArrayList<>();
        res.add(new Resource(ResourceType.COIN));
        res.add(new Resource(ResourceType.COIN));
        res.add(new Resource(ResourceType.SERVANT));
        res.add(new Resource(ResourceType.SHIELD));
        pp = new ProductionPower(entry,prod);
        dev.add(new DevelopmentCard(1, cost, 2, CardColor.GREEN, pp, 10 ));
        dev.add(new DevelopmentCard(2, cost, 2, CardColor.GREEN, pp, 10 ));
        dev.add(new DevelopmentCard(3, cost, 3, CardColor.YELLOW, pp, 10));
        dev.add(new DevelopmentCard(4, cost, 3, CardColor.GREEN, pp, 10 ));
        b = new TempPlayerBoard2(res,dev);
    }


    /**
     * the leadercard is not activable because the player hasn't enough yellow development card with level 3
     */
    @Test
    public void isNotActivableLevel(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1);
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new LevelReq(3, CardColor.YELLOW, 2));
        leaderCard = new LeaderCard(effect, 10, req);
        assertFalse(leaderCard.isActivableBy(b));
    }
/**
 * the leadercard is not activable beacuse the player hasn't enough resources
 */

    @Test
    public void isNotActivableResources(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1 );
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new ResourceReq(ResourceType.SHIELD, 2));
        req.add(new ResourceReq(ResourceType.COIN,1));
        leaderCard = new LeaderCard(effect, 10, req);
        assertFalse(leaderCard.isActivableBy(b));
    }

    /**
     * the leader card is not activable because the player hasn't enough blue developent card
     */
    @Test
    public void isNotActivableColor(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1);
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new ColorReq(CardColor.BLUE,3));
        leaderCard = new LeaderCard(effect, 10, req);
        assertFalse(leaderCard.isActivableBy(b));
    }

    /**
     * the leader card isn't activable because the player hasn't the exact combination of requirements
     */

    @Test
    public void isNotActivable(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1);
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new ColorReq(CardColor.GREEN,3));
        req.add(new ResourceReq(ResourceType.COIN,2));
        req.add(new LevelReq(1, CardColor.GREEN, 2));
        leaderCard = new LeaderCard(effect, 10, req);
        assertFalse(leaderCard.isActivableBy(b));
    }

    /**
     * the leader card is activable
     */
    @Test
    public void isActivable(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1 );
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new ColorReq(CardColor.GREEN,3));
        req.add(new ResourceReq(ResourceType.COIN,2));
        req.add(new LevelReq(1, CardColor.YELLOW, 1));
        req.add(new ResourceReq(ResourceType.SERVANT,1));
        leaderCard = new LeaderCard(effect, 10, req);
        assertTrue(leaderCard.isActivableBy(b));
    }
    /**
     * the requirement arraylist is empty
     */
    @Test
        public void emptyArray(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1);
        ArrayList<Requirement> req = new ArrayList<>();
        leaderCard = new LeaderCard(effect, 10, req);
        assertTrue(leaderCard.isActivableBy(b));
    }


    /**
     * checks if the method active works correctly
     */
    @Test
    public void activeTest(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1 );
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new ColorReq(CardColor.GREEN,3));
        req.add(new ResourceReq(ResourceType.COIN,2));
        req.add(new LevelReq(1, CardColor.YELLOW, 1));
        req.add(new ResourceReq(ResourceType.SERVANT,1));
        leaderCard = new LeaderCard(effect, 10, req);
        assertFalse(leaderCard.isActive());
        leaderCard.active();
        assertTrue(leaderCard.isActive());
    }
}
package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.effects.Discount;
import it.polimi.ingsw.model.cards.effects.JollyMarble;
import it.polimi.ingsw.model.cards.effects.LeaderEffect;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import it.polimi.ingsw.model.cards.requirements.ColorReq;
import it.polimi.ingsw.model.cards.requirements.LevelReq;
import it.polimi.ingsw.model.cards.requirements.Requirement;
import it.polimi.ingsw.model.cards.requirements.ResourceReq;
import it.polimi.ingsw.utility.LeaderCardParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice Cariboni
 */




class LeaderCardTest {
    private static PlayerBoard b;
    private static LeaderCard leaderCard;

    @BeforeAll
    public static void init() throws CannotAdd {
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
        dev.add(new DevelopmentCard(1, cost, 1, CardColor.GREEN, pp, 10 ));
        dev.add(new DevelopmentCard(2, cost, 2, CardColor.GREEN, pp, 10 ));
        dev.add(new DevelopmentCard(3, cost, 1, CardColor.YELLOW, pp, 10));
        dev.add(new DevelopmentCard(4, cost, 3, CardColor.GREEN, pp, 10 ));
        b = new PlayerBoard(new WareHouse(),new StrongBox());
        b.addDevCard(dev.get(0),0);
        b.addDevCard(dev.get(1),0);
        b.addDevCard(dev.get(2),1);
        b.addDevCard(dev.get(3),0);
        for(int i = 0; i < 4; i++) {
            b.getStrongBox().addResources(res.get(i));
        }
    }


    @Test
    public void getterTest(){
        leaderCard = new LeaderCard(1, new Discount(new Resource(ResourceType.SERVANT), 1), 2, new ArrayList<>());
        assertEquals(leaderCard.getId(), 1);
        assertEquals(leaderCard.getLeaderEffect().getEffectName(), "DISCOUNT");
        assertEquals(leaderCard.getLeaderEffect().getType(), "SERVANT");
        assertEquals(leaderCard, new LeaderCard(1, new Discount(new Resource(ResourceType.SERVANT), 1),2, new ArrayList<>()));
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
        leaderCard = new LeaderCard(1,effect, 10, req);
        assertFalse(leaderCard.isActivableBy(b));
        assertTrue(leaderCard.isActive() == false);
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
        leaderCard = new LeaderCard(1,effect, 10, req);
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
        leaderCard = new LeaderCard(1,effect, 10, req);
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
        leaderCard = new LeaderCard(1,effect, 10, req);
        assertFalse(leaderCard.isActivableBy(b));
    }

    /**
     * the leader card is activable
     */
    @Test
    public void isActivable(){
        Player p = new Player(false, "ali", 0 , new PlayerBoard(new WareHouse(), new StrongBox()));
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1 );
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new ColorReq(CardColor.GREEN,3));
        req.add(new ResourceReq(ResourceType.COIN,2));
        req.add(new LevelReq(1, CardColor.YELLOW, 1));
        req.add(new ResourceReq(ResourceType.SERVANT,1));
        leaderCard = new LeaderCard(1,effect, 10, req);
        assertTrue(leaderCard.isActivableBy(b));
        leaderCard.active(p, b);
        assertTrue(leaderCard.isActive());
    }
    /**
     * the requirement arraylist is empty
     */
    @Test
        public void emptyArray(){
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1);
        ArrayList<Requirement> req = new ArrayList<>();
        leaderCard = new LeaderCard(1,effect, 10, req);
        assertTrue(leaderCard.isActivableBy(b));
    }


    /**
     * checks if the method active works correctly
     */
    @Test
    public void activeTest(){
        Player p = new Player(false, "ali", 0 , new PlayerBoard(new WareHouse(), new StrongBox()));

        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble( r1 );
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new ColorReq(CardColor.GREEN,3));
        req.add(new ResourceReq(ResourceType.COIN,2));
        req.add(new LevelReq(1, CardColor.YELLOW, 1));
        req.add(new ResourceReq(ResourceType.SERVANT,1));
        leaderCard = new LeaderCard(1,effect, 10, req);
        assertEquals(leaderCard.getRequirements().get(0),req.get(0));
        assertFalse(leaderCard.isActive());
        leaderCard.active(p, p.getPlayerBoard());
        assertTrue(leaderCard.isActive());
    }


   @Test
    public void equalsTest() throws JsonFileNotFoundException {
        ArrayList<LeaderCard> leaderCards = LeaderCardParser.parseLeadCards();
        LeaderCard l1 = leaderCards.get(0);
        LeaderCard l2 = leaderCards.get(0);
        LeaderCard l3 = leaderCards.get(1);
        assertEquals(l1,l2);
        assertFalse(l1.equals(l3));
    }

    @Test
    public void deactivate() throws JsonFileNotFoundException, NullCardException {
        Player p = new Player(true, "Alice", 0 , new PlayerBoard(new WareHouse(), new StrongBox()));
        LeaderDeck leaderDeck = new LeaderDeck(LeaderCardParser.parseLeadCards());
        LeaderCard leaderCard = null;
        for(LeaderCard lc: leaderDeck.getCardDeck()){
            if(lc.getLeaderEffect().getEffectName().equals("EXTRA_PRODUCTION")){
                leaderCard = lc;
                p.addLeaderCard(lc);
                break;
            }
        }
        p.activateLeader(leaderCard, p.getPlayerBoard(), p);
        assertEquals(p.getExtraProductionPowers().size() ,1);
        p.discardLeader(leaderCard);
        assertEquals(p.getExtraProductionPowers().size(), 0);
        for(LeaderCard lc: leaderDeck.getCardDeck()){
            if(lc.getLeaderEffect().getEffectName().equals("EXTRA_SLOT")){
                leaderCard = lc;
                p.addLeaderCard(lc);
                break;
            }
        }
        p.activateLeader(leaderCard, p.getPlayerBoard(), p);
        assertDoesNotThrow(() -> p.getDepotById(4));
        p.discardLeader(leaderCard);
        assertThrows(NotPossibleToAdd.class, () -> p.getDepotById(4));
        for(LeaderCard lc: leaderDeck.getCardDeck()){
            if(lc.getLeaderEffect().getEffectName().equals("JOLLY_MARBLE")){
                leaderCard = lc;
                p.addLeaderCard(lc);
                break;
            }
        }
        p.activateLeader(leaderCard, p.getPlayerBoard(), p);
        assertEquals(p.getPossibleWhiteMarbles().size() ,1);
        p.discardLeader(leaderCard);
        assertEquals(p.getPossibleWhiteMarbles().size(), 0);
        for(LeaderCard lc: leaderDeck.getCardDeck()){
            if(lc.getLeaderEffect().getEffectName().equals("DISCOUNT")){
                leaderCard = lc;
                p.addLeaderCard(lc);
                break;
            }
        }
        p.activateLeader(leaderCard, p.getPlayerBoard(), p);
        assertEquals(p.getDiscountedResource().size() ,1);
        p.discardLeader(leaderCard);
        assertEquals(p.getDiscountedResource().size(), 0);
    }
}
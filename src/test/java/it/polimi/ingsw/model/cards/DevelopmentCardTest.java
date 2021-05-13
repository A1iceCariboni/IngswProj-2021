package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import it.polimi.ingsw.utility.DevelopentCardParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;




class DevelopmentCardTest {
    private static ArrayList<Resource> entry;
    private static ArrayList<Resource> prod;
    private static ArrayList<Resource> res;
    private static ProductionPower pp;
    private static PlayerBoard b;
    private static DevelopmentCard dev, dev2;
    private static Player p;


    @BeforeAll
    public static void init(){
        ArrayList<Resource> cost = new ArrayList<>();
        entry = new ArrayList<>();
        prod = new ArrayList<>();
        res = new ArrayList<>();
        Resource e1 = new Resource(ResourceType.COIN);
        Resource e2 = new Resource(ResourceType.COIN);
        Resource e3 = new Resource(ResourceType.COIN);
        Resource e4 = new Resource(ResourceType.SERVANT);
        Resource p1 = new Resource(ResourceType.SHIELD);
        Resource p2 = new Resource(ResourceType.STONE);
        entry.add(e1);
        entry.add(e2);
        entry.add(e3);
        entry.add(e4);
        prod.add(p1);
        prod.add(p2);
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SERVANT);
        cost.add(r1);
        cost.add(r1);
        cost.add(r2);
        pp = new ProductionPower(entry,prod);
        dev = new DevelopmentCard(1, cost, 2, CardColor.GREEN, pp, 10 );
        dev2 = new DevelopmentCard(1, cost, 1, CardColor.GREEN, pp, 10 );

    }

    /**
     * The development card is correctly instantiated
     */
    @Test
    void getterTest(){
        assertEquals(dev.getColor(), CardColor.GREEN);
        assertEquals(dev.getLevel(),2);
        assertEquals(dev.getVictoryPoints(),10);
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SERVANT);
        assertEquals(dev.getCost().get(0),r1);
        assertEquals(dev.getCost().get(1),r1);
        assertEquals(dev.getCost().get(2),r2);
    }

    @AfterEach
    void clearRes(){
        res.clear();
    }

    /**
     * the card is not buyable because the playerboard doesn't have enough resources
     */

    @Test
    void isNotBuyable() {
        Resource e1 = new Resource(ResourceType.COIN);
        Resource e2 = new Resource(ResourceType.SERVANT);
        res.add(e1);
        res.add(e2);
        b = new PlayerBoard(new WareHouse(), new StrongBox());
        p = new Player(false, "ali", 0,b );

        assertFalse(dev.isBuyable(p));
    }

    /**
     * the card is not buyable because the playerboard has no resources
     */
    @Test
    void emptyRes() {
        b = new PlayerBoard(new WareHouse(), new StrongBox());
        p = new Player(false, "ali", 0,b );

        assertFalse(dev.isBuyable(p));
    }

    /**
     * the player can buy the card
     */
     @Test
     void canBuy(){
         Resource e1 = new Resource(ResourceType.COIN);
         Resource e2 = new Resource(ResourceType.SERVANT);

         b = new PlayerBoard(new WareHouse(), new StrongBox());
         b.getStrongBox().addResources(e1);
         b.getStrongBox().addResources(e1);
         b.getStrongBox().addResources(e2);
         p = new Player(false, "ali", 0,b );

         assertTrue(dev2.isBuyable(p));
     }
    /**
     * add points to player
     */

    @Test
    void addPoints(){
        ArrayList<Resource> res = new ArrayList<>();
        PlayerBoard b = new PlayerBoard(new WareHouse(), new StrongBox());
        p = new Player(false,"ali",2,b);
        dev.addPointsTo(p);
        assertEquals(p.getVictoryPoints(),2+dev.getVictoryPoints());
    }

    /**
     * Remove all the resource in res and add shield and stone
     */
    @Test
    void activateWithEmptyRes(){
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.COIN);
        Resource r3 = new Resource(ResourceType.COIN);
        Resource r4 = new Resource(ResourceType.SERVANT);
        res.add(r1);
        res.add(r2);
        res.add(r3);
        res.add(r4);
        b = new PlayerBoard(new WareHouse(), new StrongBox());
        Player p = new Player(false, "ali", 0, b);
        dev.startProduction(b,p);
        assertTrue(b.getResources().contains(new Resource(ResourceType.SHIELD)));
        assertEquals(b.getResources().get(1).getResourceType(),ResourceType.STONE);
    }

    @Test
    void getByColor(){
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck(DevelopentCardParser.parseDevCards());
        assertFalse(developmentCardDeck.isEmpty());
         int size1 = developmentCardDeck.getCardDeck().size();
        DevelopmentCard dev = developmentCardDeck.getByColor(CardColor.GREEN);
        assertTrue(dev.getColor() == CardColor.GREEN);
        int size2 = developmentCardDeck.getCardDeck().size();
        assertTrue(size1 == size2 + 1);
    }

    @Test
    void getByLevel(){
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck(DevelopentCardParser.parseDevCards());
        assertFalse(developmentCardDeck.isEmpty());
        int size1 = developmentCardDeck.getCardDeck().size();
        DevelopmentCard dev = developmentCardDeck.getByLevel(2);
        assertTrue(dev.getLevel() == 2);
        int size2 = developmentCardDeck.getCardDeck().size();
        assertTrue(size1 == size2 + 1);
    }

    @Test
    void getByLevelAndColor(){
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck(DevelopentCardParser.parseDevCards());
        assertFalse(developmentCardDeck.isEmpty());
        int size1 = developmentCardDeck.getCardDeck().size();
        DevelopmentCard dev = developmentCardDeck.getByColorAndLevel(CardColor.BLUE,3);
        assertTrue(dev.getLevel() == 3);
        assertTrue(dev.getColor() == CardColor.BLUE);
        int size2 = developmentCardDeck.getCardDeck().size();
        assertTrue(size1 == size2 + 1);
    }
}
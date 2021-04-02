package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Playerboard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
class TempPlayer extends Player {
    private int victoryPoints;

    public TempPlayer(int victoryPoints){
        this.victoryPoints = victoryPoints;
    }

    @Override
    public void addVictoryPoints(int victoryPoints) {
        this.victoryPoints = this.victoryPoints + victoryPoints;
    }
    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }
}

class TempPlayerBoard extends Playerboard {
    private ArrayList<Resource> res;
    public TempPlayerBoard(ArrayList<Resource> res){
        this.res = res;
    }
    @Override
    public ArrayList<Resource> getResources() {
        return res;
    }
    @Override
    public void removeResources(ArrayList<Resource> entryResources) {
        this.res.removeAll(entryResources);
    }
    @Override
    public void addStrongBox(ArrayList<Resource> productResources) {
        this.res.addAll(productResources);
    }
}


class DevelopmentCardTest {
    private static ArrayList<Resource> entry;
    private static ArrayList<Resource> prod;
    private static ArrayList<Resource> res;
    private static ProductionPower pp;
    private static Playerboard b;
    private static DevelopmentCard dev;
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
        dev = new DevelopmentCard(CardColor.GREEN,2, cost,10,pp );
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
        b = new TempPlayerBoard(res);
        assertFalse(dev.isBuyable(b));
    }

    /**
     * the card is not buyable because the playerboard has no resources
     */
    @Test
    void emptyRes() {
        b = new TempPlayerBoard(res);
        assertFalse(dev.isBuyable(b));
    }

    /**
     * the player can buy the card
     */
     @Test
     void canBuy(){
         Resource e1 = new Resource(ResourceType.COIN);
         Resource e2 = new Resource(ResourceType.SERVANT);
         res.add(e1);
         res.add(e1);
         res.add(e2);
         b = new TempPlayerBoard(res);
         assertTrue(dev.isBuyable(b));
     }
    /**
     * add points to player
     */

    @Test
    void addPoints(){
        p = new TempPlayer(2);
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
        b = new TempPlayerBoard(res);
        dev.startProduction(b);
        assertEquals(b.getResources().get(0).getResourceType(),ResourceType.SHIELD);
        assertEquals(b.getResources().get(1).getResourceType(),ResourceType.STONE);
    }
}
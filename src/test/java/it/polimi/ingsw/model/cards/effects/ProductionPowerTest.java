package it.polimi.ingsw.model.cards.effects;


import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.StrongBox;
import it.polimi.ingsw.model.WareHouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TempPlayerBoard extends PlayerBoard {
    private final ArrayList<Resource> res;
    public TempPlayerBoard(ArrayList<Resource> res){
        super(new WareHouse(), new StrongBox());
        this.res = res;
    }
    @Override
    public ArrayList<Resource> getResources() {
        return res;
    }
}

class ProductionPowerTest {
    private static ArrayList<Resource> entry;
    private static ArrayList<Resource> prod;
    private static ArrayList<Resource> res;
    private static ProductionPower pp;
    private static PlayerBoard b;

    @BeforeAll
    public static void init(){
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
        pp = new ProductionPower(entry,prod);
    }

    @AfterEach
    void clearRes(){
        res.clear();
    }

    /**
     * The production power is correctly instantiated
      */
    @Test
    void getterTest(){
        assertEquals(pp.getEntryResources(), entry);
        assertEquals(pp.getEntryResources(), entry);
        assertEquals(pp.getEntryResources().get(0).getResourceType(),ResourceType.COIN);
        assertEquals(pp.getEntryResources().get(1).getResourceType(),ResourceType.COIN);
        assertEquals(pp.getEntryResources().get(2).getResourceType(),ResourceType.COIN);
        assertEquals(pp.getEntryResources().get(3).getResourceType(),ResourceType.SERVANT);
        assertEquals(pp.getProductResources().get(0).getResourceType(),ResourceType.SHIELD);
        assertEquals(pp.getProductResources().get(1).getResourceType(),ResourceType.STONE);
    }

    /**
     * The production is not activable with the resources on the player board b
     */
    @Test
    void notActivable() {
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.COIN);
        Resource r3 = new Resource(ResourceType.COIN);
        Resource r4 = new Resource(ResourceType.SHIELD);
        res.add(r1);
        res.add(r2);
        res.add(r3);
        res.add(r4);
        b = new TempPlayerBoard(res);
        assertFalse(pp.isActivable(b));
    }
    /**
     * The production is not activable because playboard has no resources
     */
   @Test
   void emptyRes(){
       b = new TempPlayerBoard(res);
       assertFalse(pp.isActivable(b));
       assertTrue(b.getResources().isEmpty());
   }

    /**
     * the production is activable because res has the exact amount of resources
     */
    @Test
    void exactAmount(){
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.COIN);
        Resource r3 = new Resource(ResourceType.COIN);
        Resource r4 = new Resource(ResourceType.SERVANT);
        res.add(r1);
        res.add(r2);
        res.add(r3);
        res.add(r4);
        b = new TempPlayerBoard(res);
        assertTrue(pp.isActivable(b));
    }



}
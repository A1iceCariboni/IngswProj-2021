package it.polimi.ingsw;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Resource;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alessandra Atria
 */

public class DepotTest {

    private static Depot depot1;
    private static Depot depot2;

    @Before
    public void setUp() {
        ArrayList<Resource> r1 = new ArrayList<>();
        Resource r2 = new Resource(ResourceType.COIN);
        Resource r3 = new Resource(ResourceType.SERVANT);
        depot1 = new Depot( 3, r1);
        depot2 = new Depot(2, r1);
    }

    /**
     * checks if depot can get resources and its dimension
     */
    @Test
    public void getterTest() {
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SERVANT);
        Resource r3 = new Resource(ResourceType.STONE);
        resources.add(r1);
        resources.add(r2);
        resources.add(r3);
        depot1 = new Depot( 1, resources);
        depot2 = new Depot(2, resources);


        assertEquals(depot2.getDepot(), resources);

        assertEquals(depot1.getDimension(), 1);
        assertEquals(depot2.getDimension(), 2);


    }

    /**
     * checks if resources can be removed from depot
     */
    @Test
    public void removeRes(){
        int nRes;
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SERVANT);
        depot1 = new Depot( 3, resources);
        resources.add(r1);
        resources.add(r2);
        nRes = resources.size();
        assertTrue(nRes == 2);
        assertTrue(depot1.getDepot().contains(r1));
        depot1.removeResource(r1);
        assertFalse(depot1.getDepot().contains(r1));
        nRes = resources.size();
        assertTrue(nRes == 1);
        assertFalse(nRes == 2);
    }

    /**
     * checks if resources can be added to depot
     */
    @Test
    public void addRes(){
        int nRes;
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SERVANT);
        Resource r3 = new Resource(ResourceType.STONE);
        Resource r4 = new Resource(ResourceType.SHIELD);
        depot1 = new Depot( 3, resources);
        depot1.addResource(r1);
        depot1.addResource(r2);
        depot1.addResource(r3);
        nRes = resources.size();
        assertTrue(nRes == 3);
        assertTrue(depot1.getDepot().contains(r1));
        assertTrue(depot1.getDepot().contains(r2));
        assertTrue(depot1.getDepot().contains(r3));
        depot1.addResource(r4);
        nRes = resources.size();
        assertFalse(nRes == 4);
    }


    /**
     * checks if the depot is empty
     */

    @Test
    public void isEmpty(){
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        depot1 = new Depot( 3, resources);
        depot1.addResource(r1);
        assertFalse(depot1.isEmpty());
        depot1.removeResource(r1);
        assertTrue(depot1.isEmpty());
    }


}

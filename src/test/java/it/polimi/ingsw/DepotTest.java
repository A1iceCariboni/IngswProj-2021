package it.polimi.ingsw;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
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
        depot1 = new Depot( 3,1, r1);
        depot2 = new Depot(2,1, r1);
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
        depot1 = new Depot(1,1, resources);
        depot2 = new Depot(2, 2,resources);


        assertEquals(depot2.getDepot(), resources);

        assertEquals(depot1.getDimension(), 1);
        assertEquals(depot2.getDimension(), 2);


    }

    /**
     * checks if resources can be removed from depot
     */
    @Test
    public void removeRes(){
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        resources.add(r1);
        depot1 = new Depot( 3,1, resources);
        assertTrue(depot1.getDepot().contains(r1));
        depot1.removeResource();
        assertFalse(depot1.getDepot().contains(r1));
        assertTrue(depot1.isEmpty());
    }

    /**
     * checks if resources can be added to depot
     * @throws  NotPossibleToAdd if the resource can't be added to vthe depot
     *
     */
    @Test
    public void addRes() throws NotPossibleToAdd {
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.COIN);
        Resource r3 = new Resource(ResourceType.STONE);
        Resource r4 = new Resource(ResourceType.COIN);
        Resource r5 = new Resource(ResourceType.COIN);
        depot1 = new Depot( 3, 1,resources);
        ArrayList<Resource> res = new ArrayList<>();
        depot2 = new Depot(2, 2, res);
        res.add(r1);
        res.add(r2);
        depot2.setDepot(res);
        assertTrue(depot2.getDepot().containsAll(res));
        depot1.addResource(r1);
        assertTrue(depot1.getDepot().contains(r1));
        depot1.addResource(r2);
        assertTrue(depot1.getDepot().contains(r2));
        assertThrows(NotPossibleToAdd.class, () -> depot1.addResource(r3));
        depot1.addResource(r4);
        assertTrue(depot1.getDepot().contains(r4));
        assertThrows(NotPossibleToAdd.class, () -> depot1.addResource(r5));

    }

    @Test
    public void possibleToAddTest() throws NotPossibleToAdd {
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.COIN);
        resources.add(r1);
        resources.add(r2);
        depot1 = new Depot( 3, 1,resources);

        assertFalse(depot1.possibleToAdd(new Resource(ResourceType.SHIELD)));
        assertTrue(depot1.possibleToAdd(new Resource(ResourceType.COIN)));

        depot1.addResource(new Resource(ResourceType.COIN));
        assertFalse(depot1.possibleToAdd(new Resource(ResourceType.COIN)));

    }

    @Test
    public void equalsTest(){
        ArrayList<Resource> resources = new ArrayList<>();
        depot1 = new Depot( 3, 1,resources);
        assertTrue(depot1.possibleResource(new Resource(ResourceType.SHIELD)));
        assertTrue(depot1.possibleResource(new Resource(ResourceType.COIN)));
        assertTrue(depot1.possibleResource(new Resource(ResourceType.SERVANT)));
        assertTrue(depot1.possibleResource(new Resource(ResourceType.STONE)));
        depot2 = new Depot(3, 1, resources);
        assertTrue(depot1.equals(depot2));



    }


    /**
     * checks if the depot is empty
     */

    @Test
    public void isEmpty() throws NotPossibleToAdd {
        ArrayList<Resource> resources = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.COIN);
        depot1 = new Depot( 3, 1,resources);
        depot1.addResource(r1);
        assertFalse(depot1.isEmpty());
        depot1.removeResource();
        assertTrue(depot1.isEmpty());
    }

    @Test
    public void removeResourceTest(){}


}

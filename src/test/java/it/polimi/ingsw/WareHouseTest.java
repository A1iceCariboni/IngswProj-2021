package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.model.ExtraDepot;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.WareHouse;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.enumerations.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alessandra Atria
 */

public class WareHouseTest {
    private WareHouse wareHouse;


    @Before
    public void setUp() {
        wareHouse = new WareHouse();
    }

    /**
     * checks if warehouse can get depots
     */
    @Test
    public void getDepots() throws NotPossibleToAdd {
        assertTrue(wareHouse.canAddToDepot(new Resource(SERVANT), wareHouse.getDepots().get(0)));
        wareHouse.addToDepot(new Resource(SERVANT), wareHouse.getDepots().get(0));
        assertTrue(wareHouse.hasResource(new Resource(SERVANT)));
        assertFalse(wareHouse.canAddToDepot(new Resource(SERVANT), wareHouse.getDepots().get(1)));

        assertThrows(NotPossibleToAdd.class, ()-> wareHouse.addToDepot(new Resource(SERVANT), wareHouse.getDepots().get(0) ));
        assertThrows(NotPossibleToAdd.class, ()-> wareHouse.addToDepot(new Resource(SERVANT), wareHouse.getDepots().get(1) ));
        assertThrows(NotPossibleToAdd.class, ()-> wareHouse.addToDepot(new Resource(SHIELD), wareHouse.getDepots().get(0) ));
    }



    /**
     * checks if resources can be moved from a depot to another one
     */
    @Test
    public void moveRes() throws NotPossibleToAdd {
        Resource r1 = new Resource(COIN);
        wareHouse.addToDepot(r1, wareHouse.getDepots().get(0));
        wareHouse.moveResouces(wareHouse.getDepots().get(0), wareHouse.getDepots().get(1), r1);
        assertTrue(wareHouse.getDepots().get(1).getDepot().contains(r1));
        assertFalse(wareHouse.getDepots().get(0).getDepot().contains(r1));
        wareHouse.addToDepot(new Resource(SHIELD), wareHouse.getDepots().get(0));
        assertThrows(NotPossibleToAdd.class, () -> wareHouse.moveResouces(wareHouse.getDepots().get(0), wareHouse.getDepots().get(1),new Resource(SHIELD)));
        assertThrows(NotPossibleToAdd.class, () -> wareHouse.moveResouces(wareHouse.getDepots().get(0), wareHouse.getDepots().get(1),new Resource(COIN)));
    }




    /**
     * checks if depots have the resource
     */
    @Test
    public void hasRes() throws NotPossibleToAdd {
        wareHouse.addToDepot(new Resource(SHIELD), wareHouse.getDepots().get(0));
        assertTrue(wareHouse.hasResource(new Resource(SHIELD)));
        wareHouse.remove(new Resource(SHIELD));
        assertFalse(wareHouse.hasResource(new Resource(SHIELD)));
        assertThrows(NotPossibleToAdd.class, ()-> wareHouse.remove(new Resource(SHIELD)) );
    }


    @Test
    public void extraDepotTest() throws NotPossibleToAdd {
        wareHouse.setExtraDepot(new ExtraDepot(2, 4, (SERVANT)));
        assertTrue(wareHouse.canAddToDepot(new Resource(SERVANT), wareHouse.getDepots().get(3)));
        wareHouse.addToDepot(new Resource(SERVANT), wareHouse.getDepots().get(0));
        wareHouse.addToDepot(new Resource(SERVANT), wareHouse.getDepots().get(3));
        assertThrows(NotPossibleToAdd.class, () -> wareHouse.addToDepot(new Resource(SHIELD), wareHouse.getDepots().get(3)));

    }
}

package it.polimi.ingsw;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.model.ExtraDepot;
import it.polimi.ingsw.model.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.enumerations.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alessandra Atria
 */

public class ExtraDepotTest {

    private static ExtraDepot extraDepot1;
    private static ExtraDepot extraDepot2;

    @Before
    public void setUp() {
        extraDepot1 = new ExtraDepot(2,1, new Resource(COIN));
        extraDepot2 = new ExtraDepot(2,1, new Resource(SERVANT));

    }


    /**
     * checks extra depot's dimension and if it can gets resources
     */
    @Test
    public void getter() {
        ArrayList<Resource> res = new ArrayList<>();
        Resource r1 = new Resource(COIN);
        extraDepot1 = new ExtraDepot( 2,1,new Resource(COIN));
        extraDepot1.getDepot().add(r1);
        assertTrue(extraDepot1.getDepot().contains(r1));
        assertEquals(extraDepot1.getDimension(), 2);


    }

    /**
     * checks if resources can be removed from depot
     */
    @Test
    public void removeRes() {
        ArrayList<Resource> res = new ArrayList<>();
        Resource r1 = new Resource(COIN);
        extraDepot1 = new ExtraDepot(2,1,new Resource(COIN));
        extraDepot1.getDepot().add(r1);
        assertTrue(extraDepot1.getDepot().contains(r1));
        extraDepot1.removeResource(r1);
        assertFalse(extraDepot1.getDepot().contains(r1));

    }

    /**
     * checks if resources can be added to depot
     * @throws NotPossibleToAdd if it's not possible
     */
    @Test
        public void addRes() throws NotPossibleToAdd {
            Resource r1 = new Resource(ResourceType.COIN);
            Resource r2 = new Resource(ResourceType.SERVANT);
            extraDepot1 = new ExtraDepot( 3,1,new Resource(COIN));
            extraDepot2 = new ExtraDepot( 3,1,new Resource(SERVANT));
            assertEquals(r1.getResourceType(), COIN);
        assertNotEquals(r1.getResourceType(), SERVANT);
            assertThrows(NotPossibleToAdd.class, () -> extraDepot1.addResource(r2));
            extraDepot1.addResource(r1);
            extraDepot2.addResource(r2);

    }

    }


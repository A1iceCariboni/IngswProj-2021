package it.polimi.ingsw;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.StrongBox;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** author Alessandra Atria */

public class StrongBoxTest {
    private static StrongBox s;

    @Before
    public void setUp(){
        Resource r1 = new Resource(ResourceType.COIN);
        s = new StrongBox();

    }





    @Test
    public void getRes(){
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SHIELD);
        s = new StrongBox();
        s.addResources(r1);
        assertTrue(s.getRes().contains(r1));
        assertFalse(s.getRes().contains(r2));

    }

    /** checks if resources can be added */
    @Test
    public void addRes(){
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SERVANT);
        s = new StrongBox();
        s.addResources(r1);
        assertTrue(s.getRes().contains(r1));
        assertFalse(s.getRes().contains(r2));
        s.addResources(r2);
        assertTrue(s.getRes().contains(r2));
    }


    /** checks if resources can be removed */
    @Test
    public void removeRes(){
        Resource r1 = new Resource(ResourceType.COIN);
        s = new StrongBox();
        s.addResources(r1);
        assertTrue(s.getRes().contains(r1));
        s.removeResources(r1);
        assertFalse(s.getRes().contains(r1));

    }

}



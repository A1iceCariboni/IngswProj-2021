package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.ResourceType;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExtraProductionTest {

    static ExtraProduction extraProduction;
    static Player player;

    @BeforeAll
    public static void init(){
        ArrayList<Resource> res = new ArrayList<>();
        res.add(new Resource(ResourceType.COIN));
        extraProduction = new ExtraProduction(res, 1);
        player = new Player();
    }

    @Test
    void startProduction() {
        extraProduction.startProduction(player.getPlayerBoard(), player, new Resource(ResourceType.SHIELD));
        assertTrue(player.getPlayerBoard().getStrongBox().getRes().contains(new Resource(ResourceType.SHIELD)));
        assertEquals(player.getPlayerBoard().getFaithMarker(), 1);
    }

    @Test
    void getEntryResources() {
        assertEquals(extraProduction.getEntryResources().size(),1);
        assertEquals(extraProduction.getEntryResources().get(0),new Resource(ResourceType.COIN));

    }

    @Test
    void getId() {
        assertEquals(extraProduction.getId(), 1);
    }
}
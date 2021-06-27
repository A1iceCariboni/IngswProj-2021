package it.polimi.ingsw;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderDeck;
import it.polimi.ingsw.model.cards.effects.JollyMarble;
import it.polimi.ingsw.model.cards.effects.LeaderEffect;
import it.polimi.ingsw.model.cards.requirements.Requirement;
import it.polimi.ingsw.utility.LeaderCardParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alessandra Atria
 */

public class PlayerTest {

     static Player player1;
     static Player player2;
     static LeaderDeck leaderDeck;


    @Before
    public void setUp() throws JsonFileNotFoundException {
        leaderDeck = new LeaderDeck(LeaderCardParser.parseLeadCards());

        player1 = new Player(true, "Ale", 10, new PlayerBoard(new WareHouse(), new StrongBox()));
        player2 = new Player(false, "Sofi", 5, new PlayerBoard(new WareHouse(), new StrongBox()));

    }

    @Test
    public void getNickname() {
        assertEquals("Ale", player1.getNickName());
        assertEquals("Sofi", player2.getNickName());
    }

    /**
     * checks getter and setter methods
     */
    @Test
    public void getterAndSetterTest() throws NullCardException {


        assertEquals(player1.getVictoryPoints(), 10);
        assertEquals(player2.getVictoryPoints(), 5);

        player1.addLeaderCard(leaderDeck.popCard());
        player1.addLeaderCard(leaderDeck.popCard());
        player2.addLeaderCard(leaderDeck.popCard());

        assertEquals(2, player1.getLeadercards().size());
        assertEquals(1, player2.getLeadercards().size());

        assertEquals(0, player1.getActiveLeaderCards().size());
        assertEquals(0, player2.getActiveLeaderCards().size());
        int id = player1.getLeadercards().get(0).getId();

        assertEquals(player1.getLeaderCardById(id), player1.getLeadercards().get(0));
        assertThrows(NullCardException.class,() -> player1.getLeaderCardById(-1));

        assertTrue(player1.getInkwell());
        assertFalse(player2.getInkwell());
        assertTrue(player1.getPossibleWhiteMarbles().isEmpty());
        assertTrue(player1.getDiscountedResource().isEmpty());
        assertTrue(player1.getExtraProductionPowers().isEmpty());

        LeaderCard card = player1.getLeadercards().get(0);
        player1.discardLeader(card);
        assertFalse(player1.getLeadercards().contains(card));
        assertThrows(NullCardException.class, () -> player1.discardLeader(card));

    }

    /**
     * checks if points can be added to player
     */

    @Test
    public void addVpoints() {
        int oldPoints;
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerBoard p = new PlayerBoard(new WareHouse(), new StrongBox());
        player1 = new Player(false, "Ali", 2, p);
        oldPoints = player1.getVictoryPoints();
        player1.addVictoryPoints(10);
        assertEquals(player1.getVictoryPoints(), 10 + oldPoints);
    }




    @Test
    public void addTest(){
        player1.addPossibleWhiteMarbles(new Resource(ResourceType.COIN));
        player1.addDiscountedResource(new Resource(ResourceType.SERVANT));
        player1.addExtraProductionPowers(new ExtraProduction(new ArrayList<>(), 2));
        assertTrue(player1.getDiscountedResource().contains(new Resource(ResourceType.SERVANT)));
        assertTrue(player1.getPossibleWhiteMarbles().contains(new Resource(ResourceType.COIN)));
        assertEquals(player1.getExtraProductionPowers().size(), 1);
    }

    @Test
    public void getDepotById() throws NotPossibleToAdd {
        assertEquals(player1.getDepotById(1), player1.getPlayerBoard().getWareHouse().getDepots().get(0));
        assertThrows(NotPossibleToAdd.class, () -> player1.getDepotById(6));

    }


}

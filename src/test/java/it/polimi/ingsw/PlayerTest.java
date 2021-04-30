package it.polimi.ingsw;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.effects.ExtraProductionPower;
import it.polimi.ingsw.model.cards.effects.JollyMarble;
import it.polimi.ingsw.model.cards.effects.LeaderEffect;
import it.polimi.ingsw.model.cards.requirements.LevelReq;
import it.polimi.ingsw.model.cards.requirements.Requirement;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alessandra Atria
 */

public class PlayerTest {

    private static Player player1;
    private static Player player2;

    @Before
    public void setUp() {
        PlayerBoard pb = new PlayerBoard();
        ArrayList<LeaderCard> lcard = new ArrayList<>();
        player1 = new Player(true, "Ale", 10, pb);
        player2 = new Player(false, "Sofi", 5, pb);
        ArrayList<Resource> entry = new ArrayList<>();
        ArrayList<Resource> prod = new ArrayList<>();
        ArrayList<Resource> res = new ArrayList<>();
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


    }

    @Test
    public void getNickname() {
        assertEquals("Ale", player1.getNickName());
        assertEquals("Sofi", player2.getNickName());
    }

    /**
     * checks if players can get victory points, leader cards, and player boards
     */
    @Test
    public void getterTest() {
        ArrayList<LeaderCard> l1 = new ArrayList<>();
        ArrayList<LeaderCard> l2 = new ArrayList<>();


        PlayerBoard pb1 = new PlayerBoard();
        PlayerBoard pb2 = new PlayerBoard();
        player1 = new Player(true, "Ale", 10, pb1);
        player2 = new Player(false, "Sofi", 5,  pb2);

        assertEquals(player1.getVictoryPoints(), 10);
        assertEquals(player2.getVictoryPoints(), 5);

        assertEquals(pb1, player1.getPlayerBoard());
        assertEquals(pb2, player2.getPlayerBoard());
        assertEquals(false, pb1.equals(pb2));


        assertEquals(l1, player1.getActiveLeaderCards());
        assertEquals(l2, player2.getActiveLeaderCards());

    }

    /**
     * checks if points can be added to player
     */

    @Test
    public void addVpoints() {
        int oldPoints;
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerBoard p = new PlayerBoard();
        player1 = new Player(false, "Ali", 2, p);
        oldPoints = player1.getVictoryPoints();
        player1.addVictoryPoints(10);
        assertEquals(player1.getVictoryPoints(), 10 + oldPoints);
    }


    /**
     * checks if the card is correctly removed
     * @throws NullCardException when the player doesn't have the leader card
     */
    @Test
    public void discLcard() throws NullCardException {
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        Resource r1 = new Resource(ResourceType.SHIELD);
        LeaderEffect effect = new JollyMarble(r1);
        ArrayList<Requirement> req = new ArrayList<>();
        LeaderCard card = new LeaderCard(effect, 10, req);
        PlayerBoard p = new PlayerBoard(new WareHouse(), new StrongBox());
        leaderCards.add(card);
        assertTrue(leaderCards.contains(card));
        player1 = new Player(false, "Bob", 5, p);
        player1.addLeaderCard(card);
        player1.discardLeader(card);
        assertFalse(player1.getLeadercards().contains(card));
        assertThrows(NullCardException.class, () -> player1.discardLeader(card));
    }



}

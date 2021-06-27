package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.InvalidNickname;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiGameTest {
    static MultiGame game;

    @BeforeAll
    static void addPlayer() throws JsonFileNotFoundException {
        game = new MultiGame();
        game.addPlayer(new Player(true, "Alice", 0, new PlayerBoard(new WareHouse(), new StrongBox())));
        game.addPlayer(new Player(false, "Ale", 0, new PlayerBoard(new WareHouse(), new StrongBox())));
        game.addPlayer(new Player(false, "Sofi", 0, new PlayerBoard(new WareHouse(), new StrongBox())));
        assertEquals(3, game.getPlayers().size());
        assertEquals(game.getPlayers().get(0).getLeadercards().size(), 0);
        assertEquals(game.getPlayers().get(1).getLeadercards().size(), 0);
        assertEquals(game.getPlayers().get(2).getLeadercards().size(), 0);
        game.startGame();
        assertEquals(game.getPlayers().get(0).getLeadercards().size(), 4);
        assertEquals(game.getPlayers().get(1).getLeadercards().size(), 4);
        assertEquals(game.getPlayers().get(2).getLeadercards().size(), 4);
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(game.getCurrentPlayer() , game.getFirstPlayer());
        game.nextPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
    }


    @Test
    void getters() {
        assertEquals(4, game.getDeckDevelopment()[0][0].getDevelopmentCards().size());
        game.getCardFrom(0, 0);
        assertEquals(3, game.getDeckDevelopment()[0][0].getDevelopmentCards().size());
        assertEquals(4, game.getMarketTray().getRow(1).size());
        assertEquals(game.getFirstPlayer(), game.getPlayers().get(0));
    }

    @Test
    void checkPopeSpace() {
        game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(8);
        assertTrue(game.checkPopeSpace());
        game.getPopePoints();
        assertNotEquals(game.getCurrentPlayer().getVictoryPoints(), 0);
    }




    @Test
    void getPlayerByNickname() throws InvalidNickname {
        assertEquals(game.getPlayerByNickname("Alice"), game.getFirstPlayer());
        assertThrows(InvalidNickname.class, ()->game.getPlayerByNickname("Ciao"));
    }

    @Test
    void getFakePlayer() {
        assertThrows(InvalidNickname.class, ()-> game.getFakePlayer());
    }

    @Test
    void checkEndGame() {
        game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(Constants.winFaith);
        game.getPopePoints();
        assertTrue(game.checkEndGame());
    }

    @Test
    void getWinners() {
        game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(Constants.winFaith);
        game.getPopePoints();
        assertEquals(game.getWinners().size() , 1);
        assertEquals(game.getWinners().get(0), game.getCurrentPlayer());
    }
}


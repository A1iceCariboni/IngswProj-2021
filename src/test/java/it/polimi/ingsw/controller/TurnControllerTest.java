package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnControllerTest {
    private static GameController gameController;
    private static TurnController turnController;

    @BeforeEach
    void init(){
        gameController = new SingleGameController();
        gameController.addConnectedClient("Alice", new FakeVirtualView("Alice"));
        gameController.addPlayer("Alice");
        gameController.startGame();
        turnController = new TurnController(gameController, gameController.getPlayers(), gameController.getGame().getCurrentPlayer().getNickName(), gameController.getGame());
    }

    @Test
    void getActivePlayer() {
        assertEquals("Alice", turnController.getActivePlayer());
    }

    @Test
    void nextTurn() {
        turnController.nextTurn();
        assertEquals("Alice", turnController.getActivePlayer());
        gameController.setGamePhase(GamePhase.IN_GAME);
        turnController.nextTurn();
        assertEquals("Alice", turnController.getActivePlayer());
    }

    @Test
    void changeGamePhase() {
        assertEquals(gameController.getGamePhase(), GamePhase.FIRST_ROUND);
    }

    @Test
    void goTo() {
    }
}
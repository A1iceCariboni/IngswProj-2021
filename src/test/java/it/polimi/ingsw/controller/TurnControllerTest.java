package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TurnControllerTest {
    private static GameController gameController;
    private static TurnController turnController;

    @BeforeEach
    void init() {
        gameController = new SingleGameController();
        gameController.addConnectedClient("Alice", new FakeVirtualView("Alice"));
        gameController.addPlayer("Alice");
        gameController.startGame();
        turnController = gameController.getTurnController();
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
        Player p = gameController.getGame().getCurrentPlayer();
        assertEquals(gameController.getGamePhase(), GamePhase.FIRST_ROUND);
        turnController.changeGamePhase();
        assertEquals(gameController.getGamePhase(), GamePhase.IN_GAME);
        p.getPlayerBoard().moveFaithMarker(Constants.winFaith);
        turnController.changeGamePhase();
        assertEquals(gameController.getGamePhase(), GamePhase.LAST_ROUND);
        turnController.changeGamePhase();
        assertEquals(gameController.getGamePhase(), GamePhase.END);

    }


    @Test
    void randomFirstRound(){
        Player p = gameController.getGame().getCurrentPlayer();

        turnController.randomFirstRound();
        assertEquals(p.getLeadercards().size(), 2);
    }
    @Test
    void goTo() {
      turnController.goTo("Alice");
      assertEquals(turnController.getActivePlayer(), "Alice");
        gameController = new MultiGameController();
        gameController.addPlayer("Alice");
        gameController.addPlayer("Sofia");
        gameController.addPlayer("Alessandra");
        gameController.addConnectedClient("Alice", new FakeVirtualView("Alice"));
        gameController.addConnectedClient("Sofia", new FakeVirtualView("Sofia"));
        gameController.addConnectedClient("Alessandra", new FakeVirtualView("Alessandra"));


        gameController.startGame();
        turnController = gameController.getTurnController();

        turnController.goTo("Sofia");
        assertEquals(turnController.getActivePlayer(), "Sofia");

        turnController.goTo("Alice");
        assertEquals(turnController.getActivePlayer(), "Alice");

        turnController.goTo("Alessandra");
        assertEquals(turnController.getActivePlayer(), "Alessandra");
    }


    @Test
    void nextPlayer(){
        turnController.nextPlayer();
        assertEquals(turnController.getActivePlayer(), "Alice");
        gameController.addDisconnectedClient("Alice");
        turnController.nextPlayer();
        assertEquals(turnController.getActivePlayer(), "Alice");

        gameController = new MultiGameController();
        gameController.addPlayer("Alice");
        gameController.addPlayer("Sofia");
        gameController.addPlayer("Alessandra");
        gameController.addConnectedClient("Alice", new FakeVirtualView("Alice"));
        gameController.addConnectedClient("Sofia", new FakeVirtualView("Sofia"));
        gameController.addConnectedClient("Alessandra", new FakeVirtualView("Alessandra"));
        gameController.setNumberOfPlayers(3);

        gameController.startGame();
        turnController = gameController.getTurnController();

        assertEquals(turnController.getActivePlayer(), "Alice");
        turnController.nextPlayer();
        assertEquals(turnController.getActivePlayer(), "Sofia");
        gameController.addDisconnectedClient("Alessandra");
        turnController.nextPlayer();
        assertEquals(turnController.getActivePlayer(), "Alice");
        gameController.addDisconnectedClient("Sofia");
        turnController.nextPlayer();
        assertEquals(turnController.getActivePlayer(), "Alice");
        gameController.addDisconnectedClient("Alice");
        turnController.nextPlayer();

    }
}


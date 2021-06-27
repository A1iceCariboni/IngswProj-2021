package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.InvalidNickname;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderDeck;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sofia Canestraci
 */

class SingleGameTest {
    static SingleGame game;

    @BeforeAll
    static void init() throws JsonFileNotFoundException {
        game = new SingleGame();
        game.addPlayer(new Player(true, "p1", 0, new PlayerBoard(new WareHouse(), new StrongBox())));
        game.startGame();
        assertEquals(1, game.getPlayers().size());
    }

    /**
     * it controls if the method assign rightly player to winner
     */
    @Test
    void endGameTest() {
        game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(Constants.winFaith);
        assertEquals(game.getWinners().get(0), game.getPlayers().get(0));
    }

    /**
     * it controls if the method add accurately the victory points to the player when is report section is true
     */
    @Test
    void getPopePointsTest()  {
        game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(13);

        game.getPopePoints();
        assertEquals(game.getCurrentPlayer().getVictoryPoints(), 2);
    }

    /**
     * it controls if the method return true when the requests are satisfied
     * @throws JsonFileNotFoundException
     * @throws CannotAdd
     */
    @Test
    void checkEndGameTest() throws JsonFileNotFoundException {
        game.getPlayers().get(0).getPlayerBoard().moveFaithMarker(Constants.winFaith);
        assertTrue(game.checkEndGame());


    }

    @Test
    void checkEndGame2() throws CannotAdd {
        ProductionPower productionPower = new ProductionPower();
        DevelopmentCard developmentCard1 = new DevelopmentCard(0, new ArrayList<>(), 1, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard2 = new DevelopmentCard(1, new ArrayList<>(), 2, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard3 = new DevelopmentCard(2, new ArrayList<>(), 3, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard4 = new DevelopmentCard(3, new ArrayList<>(), 1, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard5 = new DevelopmentCard(4, new ArrayList<>(), 2, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard6 = new DevelopmentCard(5, new ArrayList<>(), 3, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard7 = new DevelopmentCard(6, new ArrayList<>(), 1, CardColor.GREEN, productionPower, 0 );

        game.getCurrentPlayer().getPlayerBoard().addDevCard(developmentCard1, 0);
        game.getCurrentPlayer().getPlayerBoard().addDevCard(developmentCard2, 0);
        game.getCurrentPlayer().getPlayerBoard().addDevCard(developmentCard3, 0);
        game.getCurrentPlayer().getPlayerBoard().addDevCard(developmentCard4, 1);
        game.getCurrentPlayer().getPlayerBoard().addDevCard(developmentCard5, 1);
        game.getCurrentPlayer().getPlayerBoard().addDevCard(developmentCard6, 1);
        game.getCurrentPlayer().getPlayerBoard().addDevCard(developmentCard7, 2);

        assertTrue(game.checkEndGame());
    }

    /**
     * it controls if the method return accurately the arraylist contains the column required
     * @throws JsonFileNotFoundException
     */
    @Test
    void getColDevCardsTest()  {
        game.discardCard(CardColor.BLUE, 12);
        assertTrue(game.getColDevCards(3).get(0).isEmpty());
        assertTrue(game.getColDevCards(3).get(1).isEmpty());
        assertTrue(game.getColDevCards(3).get(2).isEmpty());
        assertTrue(game.checkEndGame());
    }

    /**
     * it controls if the method return accurately the nexy player, that is always the single player
     */
    @Test
    void nextPlayerTest() {
        game.nextPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        assertEquals(game.getFirstPlayer(), game.getPlayers().get(0));
    }




    @Test
    void discardCard(){
        game.discardCard(CardColor.GREEN,4);
        assertTrue(game.getDeckDevelopment()[0][0].isEmpty());
        assertFalse(game.getDeckDevelopment()[1][0].isEmpty());
        assertFalse(game.getDeckDevelopment()[2][0].isEmpty());
        game.discardCard(CardColor.GREEN,4);
        assertTrue(game.getDeckDevelopment()[0][0].isEmpty());
        assertTrue(game.getDeckDevelopment()[1][0].isEmpty());
        assertFalse(game.getDeckDevelopment()[2][0].isEmpty());
        game.discardCard(CardColor.GREEN,4);
        assertTrue(game.getDeckDevelopment()[0][0].isEmpty());
        assertTrue(game.getDeckDevelopment()[1][0].isEmpty());
        assertTrue(game.getDeckDevelopment()[2][0].isEmpty());

    }

    @Test
    void fakePlayerGetter(){
        assertDoesNotThrow(() -> game.getFakePlayer());
    }
}
package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.exceptions.CannotAdd;
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
    private static Player player;
    private static FaithTrack faithTrack;
    private static LeaderDeck LeaderDeck;
    private static FakePlayer fakePlayer;
    private static MarketTray marketTray;
    private static DevelopmentCardDeck[][] deckDevelopment;
    private static Player winner;

    @BeforeAll
    static void init(){
        String playersing = "player1";
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerBoard playerBoard = new PlayerBoard(new WareHouse(), new StrongBox());
        player = new Player(true, playersing, 0, playerBoard);
    }

    /**
     * it controls if the method assign rightly player to winner
     * @throws JsonFileNotFoundException
     */
    @Test
    void endGameTest() throws JsonFileNotFoundException {
        SingleGame singleGame = new SingleGame();
        singleGame.addPlayer(player);
        singleGame.endGame(player);
        assertEquals(singleGame.getWinner(), player);
    }

    /**
     * it controls if the method add the player correctly
     * @throws JsonFileNotFoundException
     */
    @Test
    void addPlayerTest() throws JsonFileNotFoundException {
        SingleGame singleGame = new SingleGame();
        singleGame.addPlayer(player);
        assertEquals(player, singleGame.getPlayer());
    }

    /**
     * it controls if the method return true when the requests are satisfied
     * @throws JsonFileNotFoundException
     * @throws CannotAdd
     */
    @Test
    void checkEndGameTest() throws JsonFileNotFoundException, CannotAdd {
        SingleGame singleGame = new SingleGame();
        singleGame.addPlayer(player);
        singleGame.getPlayer().getPlayerBoard().moveFaithMarker(23);
        assertTrue(singleGame.checkEndGame());
        singleGame.getFakePlayer().moveBlackCross(23);
        assertTrue(singleGame.checkEndGame());

        ArrayList<Resource> cost = new ArrayList<>();
        ProductionPower productionPower = new ProductionPower();
        DevelopmentCard developmentCard1 = new DevelopmentCard(0, cost, 1, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard2 = new DevelopmentCard(1, cost, 2, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard3 = new DevelopmentCard(2, cost, 3, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard4 = new DevelopmentCard(3, cost, 1, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard5 = new DevelopmentCard(4, cost, 2, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard6 = new DevelopmentCard(5, cost, 3, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard7 = new DevelopmentCard(6, cost, 1, CardColor.GREEN, productionPower, 0 );

        player.getPlayerBoard().addDevCard(developmentCard1, 0);
        player.getPlayerBoard().addDevCard(developmentCard2, 0);
        player.getPlayerBoard().addDevCard(developmentCard3, 0);
        player.getPlayerBoard().addDevCard(developmentCard4, 1);
        player.getPlayerBoard().addDevCard(developmentCard5, 1);
        player.getPlayerBoard().addDevCard(developmentCard6, 1);
        player.getPlayerBoard().addDevCard(developmentCard7, 2);

        assertTrue(singleGame.checkEndGame());

//manca questo assert per controllare che nel caso siano finite tutte le carte di una colonna checkEndGame restituisce true
        assertTrue(singleGame.checkEndGame());
    }

    /**
     * it controls if the method return accurately the arraylist contains the column required
     * @throws JsonFileNotFoundException
     */
    @Test
    void getColDevCardsTest() throws JsonFileNotFoundException {
        SingleGame singleGame = new SingleGame();
        ArrayList<DevelopmentCardDeck> developmentCardDeckA = new ArrayList<>();
        DevelopmentCardDeck[][] developmentCardDecksM;
        developmentCardDecksM = singleGame.getDevelopmentCardDeck();
        developmentCardDeckA.add(developmentCardDecksM[0][0]);
        developmentCardDeckA.add(developmentCardDecksM[0][1]);
        developmentCardDeckA.add(developmentCardDecksM[0][2]);

        assertEquals(developmentCardDeckA.get(0).getCardDeck().get(0), singleGame.getColDevCards(0).get(0).getCardDeck().get(0));
       // assertEquals(developmentCardDeckA.get(1).getCardDeck().get(0), singleGame.getColDevCards(0).get(1).getCardDeck().get(0));
       // assertEquals(developmentCardDeckA.get(2), singleGame.getColDevCards(0).get(2));
    }

    /**
     * it controls if the method return accurately the nexy player, that is always the single player
     * @throws JsonFileNotFoundException
     */
    @Test
    void nextPlayerTest() throws JsonFileNotFoundException {
        SingleGame singleGame = new SingleGame();
        singleGame.addPlayer(player);
        assertEquals(singleGame.getPlayer(), player);
    }

    /**
     * it controls if the method add accurately the victory points to the player when is report section is true
     * @throws JsonFileNotFoundException
     */
    @Test
    void getPopePointsTest() throws JsonFileNotFoundException {
        SingleGame singleGame = new SingleGame();
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        PlayerBoard playerBoard = new PlayerBoard(new WareHouse(), new StrongBox());
        Player player1 = new Player(true, "player", 0, playerBoard);
        singleGame.addPlayer(player1);
        player1.getPlayerBoard().moveFaithMarker(13);
        FaithTrack faithTrack = new FaithTrack();
        assertEquals(player1.getPlayerBoard().getFaithMarker(), 14);
        assertTrue(faithTrack.isReportSection(player1.getPlayerBoard().getFaithMarker()));
        singleGame.getPopePoints();
        assertEquals(player1.getVictoryPoints(), 3);
    }

}
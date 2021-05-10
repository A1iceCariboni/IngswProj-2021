package it.polimi.ingsw.model;


import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.LeaderDeck;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sofia Canestraci
 */

public class MultiGameTest {
    private static ArrayList<Player> players1;
    private static LeaderDeck deckLeader;
    private static DevelopmentCardDeck[][] deckDevelopment;
    private static FaithTrack faithTrack;
    private static MarketTray marketTray;
    private static ArrayList<Player> winners;
    private static int currentPlayer;

    @BeforeEach
    public void init()   {
        players1 = new ArrayList<>();

        String player_1 = "player1";
        String player_2 = "player2";
        String player_3 = "player3";
        String player_4 = "player4";
        PlayerBoard playerBoard1 = new PlayerBoard(new WareHouse(),new StrongBox());
        PlayerBoard playerBoard2 = new PlayerBoard(new WareHouse(), new StrongBox());
        PlayerBoard playerBoard3 = new PlayerBoard(new WareHouse(), new StrongBox());
        PlayerBoard playerBoard4 = new PlayerBoard(new WareHouse(),new StrongBox());
        Player player1 = new Player(true, player_1, 0, playerBoard1);
        Player player2 = new Player(false, player_2, 1, playerBoard2);
        Player player3 = new Player(false, player_3, 2, playerBoard3);
        Player player4 = new Player(false, player_4, 3, playerBoard4);
        players1.add(player1);
        players1.add(player2);
        players1.add(player3);
        players1.add(player4);
    }

    /**
     * it controls if MultiGame is correctly instantiated
     */
    @Test
    void getterTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        ArrayList<Player> players2;
        players2 = multiGame.getPlayers();
        assertEquals(players1.get(0).getNickName(), players2.get(0).getNickName());
        assertEquals(players1.get(1).getNickName(), players2.get(1).getNickName());
        assertEquals(players1.get(2).getNickName(), players2.get(2).getNickName());
        assertEquals(players1.get(3).getNickName(), players2.get(3).getNickName());
        assertEquals(players1.get(0).getVictoryPoints(), players2.get(0).getVictoryPoints());

        assertEquals(players1.get(0), multiGame.getCurrentPlayer());
        multiGame.nextPlayer();
        assertEquals(players1.get(1), multiGame.getCurrentPlayer());
    }

    @Test
    void startGameTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        int p3 = players1.get(2).getPlayerBoard().getFaithMarker();
        int p4 = players1.get(3).getPlayerBoard().getFaithMarker();
        multiGame.startGame();
        assertEquals(players1.get(2).getPlayerBoard().getFaithMarker(), p3 );
        assertEquals(players1.get(3).getPlayerBoard().getFaithMarker(), p4);
    }

    /**
     * it controls that the players are correctly added in the array list
     */
    @Test
    void addPlayerTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        ArrayList<Player> players2 = multiGame.getPlayers();

        assertEquals(players1.get(0), players2.get(0));
        assertEquals(players1.get(1), players2.get(1));
        assertEquals(players1.get(2), players2.get(2));
        assertEquals(players1.get(3), players2.get(3));

    }

    /**
     * it controls that the class return the right player as next player
     */
    @Test
    void nextPlayerTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        multiGame.getPlayers().get(0).setInkwell(true);
        assertTrue(multiGame.getPlayers().get(0).getInkwell());
        assertEquals(multiGame.nextPlayer().getNickName(),players1.get(1).getNickName());
        assertEquals(multiGame.nextPlayer().getNickName(),players1.get(2).getNickName());
        assertEquals(multiGame.nextPlayer().getNickName(),players1.get(3).getNickName());
        assertEquals(multiGame.nextPlayer().getNickName(),players1.get(0).getNickName());

    }

    /**
     * it controls that the class return the right players as winners
     */
    @Test
    void addWinnerTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        multiGame.addWinner();
        assertEquals(multiGame.addWinner(), multiGame.getWinners());
        assertEquals(multiGame.addWinner().size(), 1);
        assertEquals(players1.get(3).getNickName(),(multiGame.addWinner().get(0).getNickName()));

        ArrayList<Player> players2 = new ArrayList<>();
        String player_1 = "player1";
        String player_2 = "player2";
        PlayerBoard playerBoard1 = new PlayerBoard(new WareHouse(), new StrongBox());
        PlayerBoard playerBoard2 = new PlayerBoard(new WareHouse(), new StrongBox());
        Player player1 = new Player(true, player_1, 1, playerBoard1);
        Player player2 = new Player(false, player_2, 1, playerBoard2);
        players2.add(player1);
        players2.add(player2);

        MultiGame multiGame2 = new MultiGame();

        multiGame2.addPlayer(players2.get(0));
        multiGame2.addPlayer(players2.get(1));
        assertEquals(multiGame2.addWinner().size(), 2);
        assertEquals(players2.get(0), multiGame2.addWinner().get(0));
        assertEquals(players2.get(1), multiGame2.addWinner().get(1));

        MultiGame multiGame3 = new MultiGame();
        Resource res = new Resource(ResourceType.COIN);
        players2.get(1).getPlayerBoard().getStrongBox().addResources(res);
        players2.get(1).getPlayerBoard().getStrongBox().addResources(res);
        players2.get(1).getPlayerBoard().getStrongBox().addResources(res);
        players2.get(1).getPlayerBoard().getStrongBox().addResources(res);
        players2.get(1).getPlayerBoard().getStrongBox().addResources(res);
        multiGame3.addPlayer(players2.get(0));
        multiGame3.addPlayer(players2.get(1));
        assertEquals(multiGame3.addWinner().size(), 1);
        assertEquals(players2.get(1), multiGame3.addWinner().get(0));

        players2.get(0).getPlayerBoard().moveFaithMarker(20);
        assertEquals(multiGame3.addWinner().size(), 1);
        assertEquals(players2.get(0), multiGame3.addWinner().get(0));
    }

    /**
     *
     * @throws JsonFileNotFoundException
     */
    @Test
    void getDevCardTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        DevelopmentCard developmentCard = multiGame.getCardFrom(0,0);
        assertFalse(multiGame.getDeckDevelopment()[0][0].getCardDeck().contains(developmentCard));
    }


    /**
     * it controls if the method return true when the requests are satisfied
     * @throws JsonFileNotFoundException
     * @throws CannotAdd
     */
    @Test
    void checkEndGameTest() throws JsonFileNotFoundException, CannotAdd {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        ArrayList<Resource> cost = new ArrayList<>();
        ProductionPower productionPower = new ProductionPower();
        DevelopmentCard developmentCard1 = new DevelopmentCard(0, cost, 1, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard2 = new DevelopmentCard(1, cost, 2, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard3 = new DevelopmentCard(2, cost, 3, CardColor.PURPLE, productionPower, 0 );
        DevelopmentCard developmentCard4 = new DevelopmentCard(3, cost, 1, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard5 = new DevelopmentCard(4, cost, 2, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard6 = new DevelopmentCard(5, cost, 3, CardColor.YELLOW, productionPower, 0 );
        DevelopmentCard developmentCard7 = new DevelopmentCard(6, cost, 1, CardColor.GREEN, productionPower, 0 );

        players1.get(1).getPlayerBoard().addDevCard(developmentCard1, 0);
        players1.get(1).getPlayerBoard().addDevCard(developmentCard2, 0);
        players1.get(1).getPlayerBoard().addDevCard(developmentCard3, 0);
        players1.get(1).getPlayerBoard().addDevCard(developmentCard4, 1);
        players1.get(1).getPlayerBoard().addDevCard(developmentCard5, 1);
        players1.get(1).getPlayerBoard().addDevCard(developmentCard6, 1);
        players1.get(1).getPlayerBoard().addDevCard(developmentCard7, 2);

        assertEquals(players1.get(1).getPlayerBoard().getCountDevCards(), 7);
        assertTrue(multiGame.checkEndGame());

        players1.get(1).getPlayerBoard().moveFaithMarker(22);
        assertEquals(players1.get(1).getPlayerBoard().getFaithMarker(), 22);
        assertTrue(multiGame.checkEndGame());
    }

    /**
     * it controls the last player to play the game is the last player for players
     * @throws JsonFileNotFoundException
     */
    @Test
    void endGameTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));
        multiGame.endGame(players1.get(0));
        assertEquals(multiGame.getCurrentPlayer(), players1.get(3));
    }

    @Test
    void getPopePointsTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame();

        Player player1 = new Player(true, "player_1", 0, new PlayerBoard(new WareHouse(),new StrongBox()));
        Player player2 = new Player(false, "player_2", 1, new PlayerBoard(new WareHouse(),new StrongBox()));
        Player player3 = new Player(false, "player_3", 2, new PlayerBoard(new WareHouse(),new StrongBox()));
        Player player4 = new Player(false, "player_4", 3, new PlayerBoard(new WareHouse(),new StrongBox()));
        multiGame.addPlayer(player1);
        multiGame.addPlayer(player2);
        multiGame.addPlayer(player3);
        multiGame.addPlayer(player4);
        multiGame.startGame();
        multiGame.getPlayers().get(0).getPlayerBoard().moveFaithMarker(8);
        int points = multiGame.getPlayers().get(0).getVictoryPoints();
        int points1 = multiGame.getPlayers().get(1).getVictoryPoints();
        assertEquals(multiGame.getPlayers().get(0).getPlayerBoard().getFaithMarker(),8);
        multiGame.getPlayers().get(1).getPlayerBoard().moveFaithMarker(4);
        assertEquals(multiGame.getPlayers().get(1).getPlayerBoard().getFaithMarker(),4);
        multiGame.getPopePoints();
        assertTrue(multiGame.getPlayers().get(0).getVictoryPoints()>points);
        multiGame.getPlayers().get(1).getPlayerBoard().moveFaithMarker(4);
        assertEquals(multiGame.getPlayers().get(1).getPlayerBoard().getFaithMarker(),8);
        assertEquals(multiGame.getPlayers().get(1).getVictoryPoints(),points1);
    }
}


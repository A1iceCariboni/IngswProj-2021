package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.MarketTray;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.MultiGame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sofia Canestraci
 */

public class MultiGameTest {
    private static FaithTrack faithTrack;
    private static MarketTray marketTray;
    private static ArrayList<Player> players1;

    @BeforeAll
    public static void init() throws JsonFileNotFoundException {
        faithTrack = new FaithTrack();
        marketTray = new MarketTray();
        players1 = new ArrayList<Player>();

        String player_1 = "player1";
        String player_2 = "player2";
        String player_3 = "player3";
        String player_4 = "player4";
        ArrayList<LeaderCard> leaderCards1 = new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> leaderCards2= new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> leaderCards3 = new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> leaderCards4 = new ArrayList<LeaderCard>();
        PlayerBoard playerBoard1 = new PlayerBoard();
        PlayerBoard playerBoard2 = new PlayerBoard();
        PlayerBoard playerBoard3 = new PlayerBoard();
        PlayerBoard playerBoard4 = new PlayerBoard();
        Player player1 = new Player(true, player_1, 0, leaderCards1, playerBoard1);
        Player player2 = new Player(false, player_2, 1, leaderCards2, playerBoard2);
        Player player3 = new Player(false, player_3, 2, leaderCards3, playerBoard3);
        Player player4 = new Player(false, player_4, 3, leaderCards4, playerBoard4);
        players1.add(player1);
        players1.add(player2);
        players1.add(player3);
        players1.add(player4);
    }

    /**
     * it controls if MultiGame is correctly instantiated
     */
    @Test
    void getterTest(){
        assertEquals(players1.get(0).getNickName(), "player1");
        assertEquals(players1.get(1).getNickName(), "player2");
        assertEquals(players1.get(2).getNickName(), "player3");
        assertEquals(players1.get(3).getNickName(), "player4");
        assertEquals(players1.get(0).getVictoryPoints(), 0);
    }

    /**
     * it controls that the players are correctly added in the array list
     */
    @Test
    void addPlayerTest(){
        ArrayList<Player> players2 = new ArrayList<>();
        MultiGame multiGame = new MultiGame(players2, faithTrack, marketTray);
        multiGame.addPlayer(players1.get(0));
        multiGame.addPlayer(players1.get(1));
        multiGame.addPlayer(players1.get(2));
        multiGame.addPlayer(players1.get(3));

        assertEquals(players1.get(0), players2.get(0));
        assertEquals(players1.get(1), players2.get(1));
        assertEquals(players1.get(2), players2.get(2));
        assertEquals(players1.get(3), players2.get(3));
    }

    /**
     * it controls that the class return the right player as next player
     */
    @Test
    void nextPlayerTest(){
        MultiGame multiGame = new MultiGame(players1, faithTrack, marketTray);
        Player p2 = multiGame.nextPlayer(players1.get(0));
        assertEquals(p2, players1.get(1));
        Player p3 = multiGame.nextPlayer(players1.get(1));
        assertEquals(p3, players1.get(2));
        Player p4 = multiGame.nextPlayer(players1.get(2));
        assertEquals(p4, players1.get(3));
        Player p1 = multiGame.nextPlayer(players1.get(3));
        assertEquals(p1, players1.get(0));
    }

    /**
     * it controls that the class return the right players as winners
     */
    @Test
    void addWinnerTest(){
        MultiGame multiGame = new MultiGame(players1, faithTrack, marketTray);
        multiGame.addWinner();
        assertEquals(multiGame.addWinner().size(), 1);
        assertEquals(players1.get(3), multiGame.addWinner().get(0));

        ArrayList<Player> players2 = new ArrayList<>();
        String player_1 = "player1";
        String player_2 = "player2";
        ArrayList<LeaderCard> leaderCards1 = new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> leaderCards2= new ArrayList<LeaderCard>();
        PlayerBoard playerBoard1 = new PlayerBoard();
        PlayerBoard playerBoard2 = new PlayerBoard();
        Player player1 = new Player(true, player_1, 1, leaderCards1, playerBoard1);
        Player player2 = new Player(false, player_2, 1, leaderCards2, playerBoard2);
        players2.add(player1);
        players2.add(player2);

        MultiGame multiGame2 = new MultiGame(players2, faithTrack, marketTray);
        assertEquals(multiGame2.addWinner().size(), 2);
        assertEquals(players2.get(0), multiGame2.addWinner().get(0));
        assertEquals(players2.get(1), multiGame2.addWinner().get(1));
    }

}

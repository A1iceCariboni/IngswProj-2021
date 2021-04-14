package it.polimi.ingsw.model;


import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sofia Canestraci
 */

public class MultiGameTest {
    private static ArrayList<Player> players1;

    @BeforeAll
    public static void init()   {
        players1 = new ArrayList<>();

        String player_1 = "player1";
        String player_2 = "player2";
        String player_3 = "player3";
        String player_4 = "player4";
        ArrayList<LeaderCard> leaderCards1 = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards2= new ArrayList<>();
        ArrayList<LeaderCard> leaderCards3 = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards4 = new ArrayList<>();
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
    void addPlayerTest() throws JsonFileNotFoundException {
        ArrayList<Player> players2 = new ArrayList<>();
        MultiGame multiGame = new MultiGame(players2);
        Gson gson = new Gson();
        System.out.println(gson.toJson(multiGame.getDeckDevelopment()));
        System.out.println(gson.toJson(multiGame.getDeckLeader()));

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
    void nextPlayerTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame(players1);
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
    void addWinnerTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame(players1);
        multiGame.addWinner();
        assertEquals(multiGame.addWinner().size(), 1);
        assertEquals(players1.get(3), multiGame.addWinner().get(0));

        ArrayList<Player> players2 = new ArrayList<>();
        String player_1 = "player1";
        String player_2 = "player2";
        ArrayList<LeaderCard> leaderCards1 = new ArrayList<>();
        ArrayList<LeaderCard> leaderCards2= new ArrayList<>();
        PlayerBoard playerBoard1 = new PlayerBoard();
        PlayerBoard playerBoard2 = new PlayerBoard();
        Player player1 = new Player(true, player_1, 1, leaderCards1, playerBoard1);
        Player player2 = new Player(false, player_2, 1, leaderCards2, playerBoard2);
        players2.add(player1);
        players2.add(player2);

        MultiGame multiGame2 = new MultiGame(players2);
        assertEquals(multiGame2.addWinner().size(), 2);
        assertEquals(players2.get(0), multiGame2.addWinner().get(0));
        assertEquals(players2.get(1), multiGame2.addWinner().get(1));
    }

    @Test
    void getDevCardTest() throws JsonFileNotFoundException {
        MultiGame multiGame = new MultiGame(players1);
        DevelopmentCard developmentCard = multiGame.getCardFrom(0,0);
        assertFalse(multiGame.getDeckDevelopment()[0][0].getCardDeck().contains(developmentCard));
    }

}

package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.MarketTray;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Deck;

/**
 * @autor Sofia Canestraci
 * the class controls the game and makes possible that it starts and ends
 */
public abstract class Game {
    private Deck deckLeader;
    private Deck deckDevelopment[][];
    private FaithTrack faithTrack;
    private MarketTray marketTray;

    /**
     * it starts the game and creates the principal classes
     */
    public void startGame() throws JsonFileNotFoundException {
        faithTrack = new FaithTrack();
        deckDevelopment = new Deck[3][4];
        marketTray = new MarketTray();

    }

    /**
     * it controls which player is the winner after the last player plays
     */
    public abstract void endGame(Player player);

    /**
     * it adds a Player for the game
     * @param p player to add
     */
    public abstract void addPlayer(Player p);

    /**
     * it decides who is the player that has to play next
     * @param player the player that ends the turn
     * @return the player that has to play next
     */
    public abstract Player nextPlayer(Player player);

    public abstract MarketTray getMarketTray();

    public abstract FaithTrack getFaithTrack();
}


package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.LeaderDeck;

import java.util.ArrayList;

public class MultiGame {
    private ArrayList<Player> players;
    private LeaderDeck leaderDeck;
    private FaithTrack faithTrack;
    private MarketTray marketTray;
    private Deck[][] developmentDecks;

    public MultiGame(ArrayList<Player> players) throws JsonFileNotFoundException {
        this.players = players;
        this.leaderDeck = new LeaderDeck();
        this.faithTrack = new FaithTrack();
        this.marketTray = new MarketTray();
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck();

    }


}

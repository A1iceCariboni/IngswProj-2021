package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.utility.LeaderCardParser;

import java.util.ArrayList;

public class LeaderDeck {
    private ArrayList<LeaderCard> leaderCards;
    public LeaderDeck(ArrayList<LeaderCard> leaderCards)  {
        this.leaderCards = leaderCards;
    }
}

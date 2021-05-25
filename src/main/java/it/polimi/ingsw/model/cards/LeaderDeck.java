package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.utility.LeaderCardParser;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderDeck extends Deck {
    private final ArrayList<LeaderCard> cardDeck;

    public LeaderDeck(ArrayList<LeaderCard> cardDeck) {
        this.cardDeck = cardDeck;
    }

    /**
     * @return first card of the deck and remove the card from the deck
     */
    public LeaderCard popCard() {
        LeaderCard c = cardDeck.get(0);
        cardDeck.remove(c);
        return c;
    }

    /**
     * @return true if it's empty
     */
    public boolean isEmpty() {
        return cardDeck.isEmpty();
    }

    /**
     * @param card add card to the deck
     */
    public void addCard(LeaderCard card){
        this.cardDeck.add(card);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.cardDeck);
    }

    public ArrayList<LeaderCard> getCardDeck() {
        return cardDeck;
    }
}

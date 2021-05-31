package it.polimi.ingsw.model.cards;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderDeck extends Deck {
    private final ArrayList<LeaderCard> leaderCards;

    public LeaderDeck(ArrayList<LeaderCard> cardDeck) {
        this.leaderCards = cardDeck;
    }

    /**
     * @return first card of the deck and remove the card from the deck
     */
    public LeaderCard popCard() {
        LeaderCard c = leaderCards.get(0);
        leaderCards.remove(c);
        return c;
    }

    /**
     * @return true if it's empty
     */
    public boolean isEmpty() {
        return leaderCards.isEmpty();
    }

    /**
     * @param card add card to the deck
     */
    public void addCard(LeaderCard card){
        this.leaderCards.add(card);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.leaderCards);
    }

    public ArrayList<LeaderCard> getCardDeck() {
        return leaderCards;
    }
}

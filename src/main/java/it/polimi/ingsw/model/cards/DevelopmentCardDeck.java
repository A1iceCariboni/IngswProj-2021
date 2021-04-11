package it.polimi.ingsw.model.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * this class represents a deck of any type of card : leader card ora development card
 * @author Alice Cariboni
 */
public class DevelopmentCardDeck extends Deck{
   private ArrayList<DevelopmentCard> cardDeck;

    public DevelopmentCardDeck(ArrayList<DevelopmentCard> cardDeck) {
        this.cardDeck = cardDeck;
    }

    /**
     * @return first card of the deck and remove the card from the deck
     */
    public DevelopmentCard popCard(){
        DevelopmentCard c = cardDeck.get(0);
        cardDeck.remove(c);
        return c;
    }

    /**
     * @param card add card to the deck
     */
    public void addCard(DevelopmentCard card){
        cardDeck.add(card);
    }

    /**
     * @return true if it's empty
     */
    public boolean isEmpty(){
        return cardDeck.isEmpty();
    }

    /**
     * shuffle the deck
     */
    public void shuffle(){
        Collections.shuffle(this.cardDeck);
    }
}

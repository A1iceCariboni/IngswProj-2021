package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.exceptions.EmptyDeck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * this class represents a deck of any type of card : leader card ora development card
 * @author Alice Cariboni
 */
public class DevelopmentCardDeck extends Deck{
   private final ArrayList<DevelopmentCard> developmentCards;

    public DevelopmentCardDeck(ArrayList<DevelopmentCard> cardDeck) {
        this.developmentCards = cardDeck;
    }

    public DevelopmentCardDeck() {
     this.developmentCards = new ArrayList<>();
    }

    /**
     *
     * @return the first card of the deck without removing it
     */
    public DevelopmentCard getCard() throws EmptyDeck {
        if(developmentCards.isEmpty()) throw new EmptyDeck();
        return developmentCards.get(0);
    }
    /**
     * @return first card of the deck and remove the card from the deck
     */
    public DevelopmentCard popCard(){
        DevelopmentCard c = developmentCards.get(0);
        developmentCards.remove(c);
        return c;
    }

    /**
     *
     * @param cardColor color of the card i want to get
     * @return the first occurency of the card with the required color and remove the card from the deck
     */
    public DevelopmentCard getByColor(CardColor cardColor){
        List<DevelopmentCard> filtered = developmentCards.stream()
                                                    .filter(card -> card.getColor() == cardColor)
                                                    .collect(Collectors.toList());
        DevelopmentCard card = filtered.get(0);
        this.developmentCards.remove(card);
        return card;
    }


    /**
     *
     * @param cardLevel level of the card i want to get
     * @return the first occurency in the deck with the required level and remove the card from the deck
     */
    public DevelopmentCard getByLevel(int cardLevel){
        List<DevelopmentCard> filtered = developmentCards.stream()
                .filter(card -> card.getLevel() == cardLevel)
                .collect(Collectors.toList());
        DevelopmentCard card = filtered.get(0);
        this.developmentCards.remove(card);
        return card;
    }


    /**
     *
     * @param cardColor color of the card i want to get
     * @param cardLevel level of the card i want to get
     * @return the first occurency with the required level and color and remove the card from the deck
     */
    public DevelopmentCard getByColorAndLevel(CardColor cardColor, int cardLevel){
        List<DevelopmentCard> filtered = developmentCards.stream()
                .filter(card -> card.getColor() == cardColor && card.getLevel() == cardLevel)
                .collect(Collectors.toList());
        DevelopmentCard card = filtered.get(0);
        this.developmentCards.remove(card);
        return card;
    }

    /**
     * @param card add card to the deck
     */
    public void addCard(DevelopmentCard card){
        this.developmentCards.add(card);
    }

    /**
     * @return true if it's empty
     */
    public boolean isEmpty(){
        return developmentCards.isEmpty();
    }

    /**
     * shuffle the deck
     */
    public void shuffle(){
        Collections.shuffle(this.developmentCards);
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }
}

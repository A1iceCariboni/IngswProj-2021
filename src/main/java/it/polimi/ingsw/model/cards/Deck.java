package it.polimi.ingsw.model.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Deck implements Serializable {
    private static final long serialVersionUID = -1347342949075836759L;

    private ArrayList<Card> cardDeck;




        public abstract void shuffle();


}

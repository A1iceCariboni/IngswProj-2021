package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.TokenDeck;

public class FakePlayer {
    int blackCross;
    TokenDeck tokenDeck;

    public FakePlayer(int blackCross) {
        this.blackCross = blackCross;
    }

    public void moveBlackCross(int pos) {
    }

    public void shuffleToken() {
    }
}

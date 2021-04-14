package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.TokenDeck;
/**
 * @author Alessandra Atria
 */
public class FakePlayer {
    private int blackCross;
    TokenDeck tokenDeck;

    public FakePlayer(int blackCross) {
        this.blackCross = blackCross;
    }

    public int getBlackCross() {
        return blackCross;
    }

    /** This method moves black cross a number pos positions
     * @param pos represents the number of position that black cross is moved
     */
    public void moveBlackCross(int pos) {
        this.blackCross = this.blackCross + pos;
    }

    public void shuffleToken() {
        this.tokenDeck.shuffle();
    }
}

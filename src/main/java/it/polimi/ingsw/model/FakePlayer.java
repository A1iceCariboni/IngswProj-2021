package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.TokenDeck;
/**
 * @author Alessandra Atria
 */
public class FakePlayer extends Player{
    private int blackCross;
    private TokenDeck tokenDeck;

    public FakePlayer() throws JsonFileNotFoundException {
        this.blackCross = 0;
        this.tokenDeck = new TokenDeck();
    }

    @Override
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

    @Override
    public ActionToken getToken() {
        return this.tokenDeck.pickToken();
    }

    public TokenDeck getTokenDeck() {
        return tokenDeck;
    }
}

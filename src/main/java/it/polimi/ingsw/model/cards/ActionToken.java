package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.TokenType;
import it.polimi.ingsw.model.FakePlayer;
import it.polimi.ingsw.model.SingleGame;
import it.polimi.ingsw.model.cards.effects.TokenEffect;

import java.io.Serializable;

/**
 * this class represents an action token, for the single play
 * @author Alice Cariboni
 */
public class ActionToken implements Serializable {
    private static final long serialVersionUID = 7619821908717431984L;

    private final TokenType tokenType;
    private final TokenEffect tokenEffect;


    public ActionToken(TokenType tokenType, TokenEffect tokenEffect) {
        this.tokenType = tokenType;
        this.tokenEffect = tokenEffect;
    }

    public void applyEffect(SingleGame singleGame, FakePlayer fakePlayer){
        this.tokenEffect.applyEffect(singleGame,fakePlayer);
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}

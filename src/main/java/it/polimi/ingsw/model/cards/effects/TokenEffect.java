package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.FakePlayer;
import it.polimi.ingsw.model.SingleGame;

public interface TokenEffect {
   void applyEffect(SingleGame singleGame, FakePlayer fakePlayer);
}

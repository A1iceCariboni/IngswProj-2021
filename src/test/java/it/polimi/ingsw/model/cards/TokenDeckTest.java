package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FakePlayer;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.SingleGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TokenDeckTest {
static TokenDeck tokenDeck;
@BeforeEach
public void init() throws JsonFileNotFoundException{
        tokenDeck = new TokenDeck();
    }

    /**
     * if i pick 6 tokens the deck is empty
     */
   @Test
    public void pickAll(){
    assertEquals(tokenDeck.getActionTokens().size(),6);
    tokenDeck.pickToken();
    tokenDeck.pickToken();
    tokenDeck.pickToken();
    tokenDeck.pickToken();
    tokenDeck.pickToken();
    tokenDeck.pickToken();
    assertEquals(tokenDeck.getPickedTokens().size(),6);
    assertEquals(tokenDeck.getActionTokens().size(),0);
    tokenDeck.shuffle();
    assertEquals(tokenDeck.getActionTokens().size(),6);
}

    /**
     * the tokens i pick are the same in the picked tokens deck
     */
    @Test
    public void pickedTokens(){
    ActionToken actionToken = tokenDeck.pickToken();
    assertEquals(actionToken,tokenDeck.getPickedTokens().get(0));
    assertNotEquals(actionToken,tokenDeck.getActionTokens().get(0));
}

   @Test
    public void actionTokens() throws JsonFileNotFoundException {
       ActionToken actionToken;
       Player player = new Player(false , "ali", 2, new ArrayList<>(), new PlayerBoard());
       SingleGame singleGame= new SingleGame();
       singleGame.addPlayer(player);
       FakePlayer fakePlayer = new FakePlayer(0);
       actionToken = tokenDeck.pickToken();
       actionToken.applyEffect(singleGame, fakePlayer);
       actionToken = tokenDeck.pickToken();
       actionToken.applyEffect(singleGame, fakePlayer);
       actionToken = tokenDeck.pickToken();
       actionToken.applyEffect(singleGame, fakePlayer);
       actionToken = tokenDeck.pickToken();
       actionToken.applyEffect(singleGame, fakePlayer);
       actionToken = tokenDeck.pickToken();
       actionToken.applyEffect(singleGame, fakePlayer);
       actionToken = tokenDeck.pickToken();
       actionToken.applyEffect(singleGame, fakePlayer);
       assertEquals(tokenDeck.getActionTokens().size(),0);
       assertEquals(tokenDeck.getPickedTokens().size(),6);
   }

}
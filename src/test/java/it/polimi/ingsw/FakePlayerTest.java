package it.polimi.ingsw;
import it.polimi.ingsw.enumerations.TokenType;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FakePlayer;
import it.polimi.ingsw.model.cards.TokenDeck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
     * @author Alessandra Atria
     */

    public class FakePlayerTest {

        private static FakePlayer f;

        @Before
        public void setUp() throws JsonFileNotFoundException {
            f = new FakePlayer();
        }


    /**
     * checks if black cross can be moved of n pos
     */
        @Test
        public void moveBCross()  {
            assertEquals( f.getBlackCross(), 0);
            f.moveBlackCross(2);
            assertEquals( f.getBlackCross(), 2);

        }

        @Test
        public void tokenTest(){
            TokenDeck tokenDeck = f.getTokenDeck();
            f.shuffleToken();
            assertEquals(tokenDeck.getActionTokens().size(), f.getTokenDeck().getActionTokens().size());
            f.getToken();
            assertEquals(1, f.getTokenDeck().getPickedTokens().size());

        }
}



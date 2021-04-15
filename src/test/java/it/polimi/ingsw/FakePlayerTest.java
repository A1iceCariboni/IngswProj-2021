package it.polimi.ingsw;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FakePlayer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
     * @author Alessandra Atria
     */

    public class FakePlayerTest {

        private static FakePlayer f;
        private static int b;

        @Before
        public void setUp() throws JsonFileNotFoundException {
            f = new FakePlayer(b);
        }


    /**
     * checks if black cross can be moved of n pos
     */
        @Test
        public void moveBCross() throws JsonFileNotFoundException {
            b = 5;
            f = new FakePlayer(b);
            assertEquals( f.getBlackCross(), 5 );
            f.moveBlackCross(2);
            assertEquals( f.getBlackCross(), 7 );

        }
}



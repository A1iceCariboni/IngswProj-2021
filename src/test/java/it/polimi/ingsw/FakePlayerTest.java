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
            f = new FakePlayer();
        }


    /**
     * checks if black cross can be moved of n pos
     */
        @Test
        public void moveBCross() throws JsonFileNotFoundException {

            f = new FakePlayer();
            assertEquals( f.getBlackCross(), 0);
            f.moveBlackCross(2);
            assertEquals( f.getBlackCross(), 2);

        }
}



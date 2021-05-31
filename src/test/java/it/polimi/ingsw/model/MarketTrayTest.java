package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.MarbleColor;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketTray;
import it.polimi.ingsw.utility.MarketTrayParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sofia Canestraci
 */

public class MarketTrayTest {
    private static Marble slidingMarble;
    private static Marble[][] marbles;


    /**
     * it tests if the method change correctly the column selected
     */
    @Test
    void GetColTest() throws JsonFileNotFoundException {
        Marble[][] marble1 = new Marble[3][4];
        ArrayList<Marble> colMarble2;

        MarketTray marketTray1 = MarketTrayParser.parseMarket();
        marble1 = marketTray1.getMarketTrayMarbles();
        int c = 0;
        Marble freeMarble1 = marketTray1.getSlidingMarble();
        colMarble2 = marketTray1.getCol(c);
        Marble freeMarble2 = marketTray1.getSlidingMarble();

        assertTrue(colMarble2.get(2).equals(freeMarble2));
        assertTrue(marble1[0][c].equals(freeMarble1));
        assertTrue(marble1[1][c].equals(colMarble2.get(0)));
        assertTrue(marble1[2][c].equals(colMarble2.get(1)));
    }

    /**
     * it tests if the method change correctly the row selected
     */
    @Test
    void GetRowTest() throws JsonFileNotFoundException {
        Marble[][] marble1 = new Marble[3][4];
        ArrayList<Marble> rowMarble2;

        MarketTray marketTray1 =  MarketTrayParser.parseMarket();
        int r = 1;
        marble1 = marketTray1.getMarketTrayMarbles();
        Marble freeMarble1 = marketTray1.getSlidingMarble();
        rowMarble2 = marketTray1.getRow(1);
        Marble freeMarble2 = marketTray1.getSlidingMarble();

        assertTrue(marble1[r][0].equals(freeMarble1));
        assertTrue(marble1[r][1].equals(rowMarble2.get(0)));
        assertTrue(marble1[r][2].equals(rowMarble2.get(1)));
        assertTrue(marble1[r][3].equals(rowMarble2.get(2)));
        assertTrue(freeMarble2.equals(rowMarble2.get(3)));
    }
}

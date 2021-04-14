package it.polimi.ingsw.utility;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.TempMarketTray;
import it.polimi.ingsw.utility.DevelopentCardParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParsersTest {

    @Test
    public void initDev() {
        DevelopentCardParser.parseDevCards();
    }


    @Test
    public void initLead() throws JsonFileNotFoundException {
        LeaderCardParser.parseLeadCards();
    }

    @Test
    public void tempMarketTray() throws JsonFileNotFoundException {
        TempMarketTray tempMarketTray = new TempMarketTray();
        Marble[][] marbles1;
        marbles1 = tempMarketTray.getMarbles();
        System.out.println("mercato iniziale");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                System.out.print(" " + marbles1[r][c].getMarbleColor());
            }
            System.out.println();
        }

        Marble slid = tempMarketTray.getSlidingMarble();
        System.out.println("slid " + slid.getMarbleColor());


        ArrayList<Marble> rowMarble = tempMarketTray.getRow(0);
        System.out.println("mercato finale");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                System.out.print(" " + marbles1[r][c].getMarbleColor());
            }
            System.out.println();
        }
        Marble newslid = tempMarketTray.getSlidingMarble();

        assertTrue(marbles1[0][0].equals(slid));
        assertTrue(marbles1[0][1].equals(rowMarble.get(0)));
        assertTrue(marbles1[0][2].equals(rowMarble.get(1)));
        assertTrue(marbles1[0][3].equals(rowMarble.get(2)));
        assertTrue(newslid.equals(rowMarble.get(3)));



        System.out.println("vettore colonna ");

        for (int i = 0; i < 4; i++) {
            System.out.println(rowMarble.get(i).getMarbleColor());
        }
        slid = tempMarketTray.getSlidingMarble();
        System.out.println("slid " + slid.getMarbleColor());

    }
}
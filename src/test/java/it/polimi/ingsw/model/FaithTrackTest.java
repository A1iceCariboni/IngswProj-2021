package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sofia Canestraci
 */

class FaithTrackTest {
    private static boolean[] isPopeSpace = new boolean[24];
    private static int[] victoryPoints = new int[24];
    private static int[] pointsForPopeSpace = new int[3];
    private static boolean isReportSection1;
    private static boolean isReportSection2;
    private static boolean isReportSection3;

    /**
     * it tests if the method works accurately
     */
    @Test
    void isReportSectionTest(){
        FaithTrack faithTrack = new FaithTrack();
        int position = 2;
        assertFalse(faithTrack.isReportSection(position));
        int position2 = 7;
        int position3 = 15;
        int position4 = 20;
        assertTrue(faithTrack.isReportSection(position2));
        assertTrue(faithTrack.isReportSection(position3));
        assertTrue(faithTrack.isReportSection(position4));
    }

    /**
     * it tests if the method calls the method inside accurately
     */
    @Test
    void IsPopeSpaceTest(){
        FaithTrack faithTrack = new FaithTrack();
        assertFalse(faithTrack.isPopeSpace(4));
        assertTrue(faithTrack.isPopeSpace(7));
        assertTrue(faithTrack.isPopeSpace(15));
        assertFalse(faithTrack.isReportSection(7));
        assertFalse(faithTrack.isPopeSpace(7));
    }

    /**
     * it tests if the method works accurately
     */
    @Test
    void deactivateSectionTest(){
        FaithTrack faithTrack = new FaithTrack();
        faithTrack.deactivateSection(8);
        assertFalse(faithTrack.isReportSection(8));
        faithTrack.deactivateSection(22);
        assertFalse(faithTrack.isReportSection(22));
    }

    /**
     * it tests if the method works accurately
     */
    @Test
    void getPointsForPopeTest(){
        FaithTrack faithTrack = new FaithTrack();
        assertEquals(faithTrack.getPointsForPope(8), 2);
        assertEquals(faithTrack.getPointsForPope(15), 3);
        assertEquals(faithTrack.getPointsForPope(20), 4);
        assertEquals(faithTrack.getPointsForPope(24), 4);
    }

}
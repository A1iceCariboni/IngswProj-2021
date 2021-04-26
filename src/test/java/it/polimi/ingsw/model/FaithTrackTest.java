package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sofia Canestraci
 */

class FaithTrackTest {

    /**
     * it tests if the method works accurately
     */
    @Test
    void isReportSectionTest(){
        FaithTrack faithTrack = new FaithTrack();
        int position = 3;
        assertFalse(faithTrack.isReportSection(position));
        int position2 = 8;
        int position3 = 16;
        int position4 = 21;
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
        assertFalse(faithTrack.isPopeSpace(5));
        assertTrue(faithTrack.isPopeSpace(8));
        assertTrue(faithTrack.isPopeSpace(16));
    }

    /**
     * it tests if the method works accurately
     */
    @Test
    void deactivateSectionTest(){
        FaithTrack faithTrack = new FaithTrack();
        faithTrack.deactivateSection(10);
        assertFalse(faithTrack.isReportSection(10));
        faithTrack.deactivateSection(24);
        assertFalse(faithTrack.isReportSection(24));
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
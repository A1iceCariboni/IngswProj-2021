package it.polimi.ingsw.utility;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.utility.DevelopentCardParser;
import org.junit.jupiter.api.Test;

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
}
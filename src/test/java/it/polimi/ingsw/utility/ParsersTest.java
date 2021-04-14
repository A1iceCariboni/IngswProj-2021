package it.polimi.ingsw.utility;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import org.junit.jupiter.api.Test;

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
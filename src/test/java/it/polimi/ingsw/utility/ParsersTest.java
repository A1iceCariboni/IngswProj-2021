package it.polimi.ingsw.utility;

import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FaithTrack;
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

    @Test
    public void initFaithTrack(){FaithTrackParser.parseFaithTrack();

    }

}
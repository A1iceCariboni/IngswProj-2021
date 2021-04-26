package it.polimi.ingsw.utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.FaithCell;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**load the faith track from json
 * @author Alice Cariboni
 */
public class FaithTrackParser {
    private FaithTrackParser(){

        }
        public static FaithCell[] parseFaithTrack() {
            String path = "/json/faithtrack.json";
            Gson gson = new Gson();
            Reader reader = new BufferedReader(new InputStreamReader(
                    JsonObject.class.getResourceAsStream(path)));
            FaithCell[] faithCells = gson.fromJson(reader, FaithCell[].class);
            return faithCells;
        }
    }


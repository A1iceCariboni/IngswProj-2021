package it.polimi.ingsw.utility;

import com.google.gson.*;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * load a deck of development cards from json
 * @author Alice Cariboni
 */
public class DevelopentCardParser {
private DevelopentCardParser(){

}
    public static DevelopmentCardDeck parseDevCards() {
        String path = "/json/developmentcards.json";

        Gson gson = new Gson();
        Reader reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));
        DevelopmentCard[] developmentCards = gson.fromJson(reader, DevelopmentCard[].class);
        ArrayList<DevelopmentCard> developmentCardArrayList = new ArrayList<>(Arrays.asList(developmentCards));
        return new DevelopmentCardDeck(developmentCardArrayList);
    }
}

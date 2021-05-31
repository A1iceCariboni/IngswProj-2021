package it.polimi.ingsw.utility;

import com.google.gson.*;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.MarbleColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarbleEffect;
import it.polimi.ingsw.model.MarketTray;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MarketTrayParser {

    public static MarketTray parseMarket() throws JsonFileNotFoundException {
        Marble[][] marbles = new Marble[Constants.rows][Constants.cols];
        String path = "/json/marbles.json";


        InputStream is = MarketTrayParser.class.getResourceAsStream(path);

        if (is == null) throw new JsonFileNotFoundException("File " + path + " not found");

        JsonParser parser = new JsonParser();


        JsonArray json = parser.parse(new InputStreamReader(is)).getAsJsonArray();


        ArrayList<Marble> marbles1 = new ArrayList<>();
        MarbleEffect marbleEffect;

        for (JsonElement jsonElement : json) {

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String color = jsonObject.get("marbleColor").getAsString();
            MarbleColor marbleColor = MarbleColor.valueOf(color);
            switch(marbleColor){
                case WHITE:
                    marbleEffect = (MarbleEffect & Serializable) (playerBoard) -> {
                    };
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case BLUE:
                    marbleEffect = (MarbleEffect & Serializable) playerBoard -> {
                        playerBoard.addUnplacedResource(new Resource(ResourceType.SHIELD));};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case GREY:
                    marbleEffect =(MarbleEffect & Serializable) playerBoard ->{
                            playerBoard.addUnplacedResource(new Resource(ResourceType.STONE));};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case YELLOW:
                    marbleEffect = (MarbleEffect & Serializable) playerBoard -> {playerBoard.addUnplacedResource(new Resource(ResourceType.COIN));};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case PURPLE:
                    marbleEffect = (MarbleEffect & Serializable) playerBoard -> {playerBoard.addUnplacedResource(new Resource(ResourceType.SERVANT));};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case RED:
                    marbleEffect = (MarbleEffect & Serializable)  playerBoard -> playerBoard.moveFaithMarker(1);
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
            }

        }

        Collections.shuffle(marbles1);
        int cont = 0 ;
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 4; c++){
                marbles[r][c] = marbles1.get(cont);
                cont++;
            }
        }
        Marble slidingMarble;

        slidingMarble = marbles1.get(cont);
    return new MarketTray(marbles, slidingMarble);
    }

}

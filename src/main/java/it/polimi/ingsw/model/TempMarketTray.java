package it.polimi.ingsw.model;

import com.google.gson.*;
import it.polimi.ingsw.enumerations.MarbleColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class TempMarketTray {
   private Marble[][] marbles = new Marble[3][4];
   private Marble slidingMarble;

    public TempMarketTray() throws JsonFileNotFoundException {
        String path = "/json/marbles.json";


        InputStream is = TempMarketTray.class.getResourceAsStream(path);

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
                    marbleEffect = playerBoard -> {
                    };
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case BLUE:
                    marbleEffect = playerBoard -> {
                        ArrayList<Resource> res = new ArrayList<>();
                        res.add(new Resource(ResourceType.SHIELD));
                        playerBoard.addStrongBox(res);};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case GREY:
                    marbleEffect = playerBoard -> {
                        ArrayList<Resource> res = new ArrayList<>();
                        res.add(new Resource(ResourceType.STONE));
                        playerBoard.addStrongBox(res);};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case YELLOW:
                    marbleEffect = playerBoard -> {
                        ArrayList<Resource> res = new ArrayList<>();
                        res.add(new Resource(ResourceType.COIN));
                        playerBoard.addStrongBox(res);};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case PURPLE:
                    marbleEffect = playerBoard -> {
                        ArrayList<Resource> res = new ArrayList<>();
                        res.add(new Resource(ResourceType.SERVANT));
                        playerBoard.addStrongBox(res);};
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
                case RED:
                    marbleEffect = playerBoard -> playerBoard.moveFaithMarker(1);
                    marbles1 .add(new Marble(marbleColor,marbleEffect));
                    break;
            }

        }

        Collections.shuffle(marbles1);
        int cont = 0 ;
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 4; c++){
                this.marbles[r][c] = marbles1.get(cont);
                cont++;
            }
        }
        this.slidingMarble = marbles1.get(cont);

    }
}

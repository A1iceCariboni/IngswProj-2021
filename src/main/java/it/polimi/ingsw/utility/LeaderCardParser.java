package it.polimi.ingsw.utility;

import com.google.gson.*;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderDeck;
import it.polimi.ingsw.model.cards.TokenDeck;
import it.polimi.ingsw.model.cards.effects.*;
import it.polimi.ingsw.model.cards.requirements.ColorReq;
import it.polimi.ingsw.model.cards.requirements.LevelReq;
import it.polimi.ingsw.model.cards.requirements.Requirement;
import it.polimi.ingsw.model.cards.requirements.ResourceReq;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderCardParser {

        private LeaderCardParser(){
        }
        public static ArrayList<LeaderCard> parseLeadCards() throws JsonFileNotFoundException {
            ArrayList<LeaderCard> leaderCards = new ArrayList<>();
            String path = "/json/leadercards.json";

            InputStream is = LeaderCardParser.class.getResourceAsStream(path);

            if (is == null) throw new JsonFileNotFoundException("File " + path + " not found");

            JsonParser parser = new JsonParser();
            JsonArray json = parser.parse(new InputStreamReader(is)).getAsJsonArray();


            for (JsonElement leaderCardElem : json) {
                JsonObject leaderCard = leaderCardElem.getAsJsonObject();

                boolean isActive = leaderCard.get("isActive").getAsBoolean();
                int victoryPoints = leaderCard.get("victoryPoints").getAsInt();
                JsonArray jsonRequirements = leaderCard.getAsJsonArray("requirements");
                int id = leaderCard.get("id").getAsInt();

                int quantity;
                String resource;
                String color;
                ResourceType resourceType;
                CardColor cardColor;
                ArrayList<Requirement> requirements = new ArrayList<>();

                int level;
                for(JsonElement jsonRequirementElem : jsonRequirements){
                    JsonObject jsonRequirement = jsonRequirementElem.getAsJsonObject();
                    String name = jsonRequirement.get("name").getAsString();
                    switch (name){
                        case "COLORREQ":
                            color = jsonRequirement.get("color").getAsString();
                            cardColor = CardColor.valueOf(color);
                            quantity = jsonRequirement.get("quantity").getAsInt();
                            requirements.add(new ColorReq(cardColor,quantity));
                            break;
                        case "LEVELREQ":
                            color = jsonRequirement.get("color").getAsString();
                            cardColor = CardColor.valueOf(color);
                            quantity = jsonRequirement.get("quantity").getAsInt();
                            level = jsonRequirement.get("level").getAsInt();
                            requirements.add(new LevelReq(level,cardColor,quantity));
                            break;
                        case "RESOURCEREQ":
                            resource = jsonRequirement.get("resourceType").getAsString();
                            resourceType = ResourceType.valueOf(resource);
                            quantity = jsonRequirement.get("quantity").getAsInt();
                            requirements.add(new ResourceReq(resourceType,quantity));
                            break;
                    }
                }
                JsonObject jsonLeaderEffect = leaderCard.getAsJsonObject("leaderEffect");

                String name = jsonLeaderEffect.get("name").getAsString();
                LeaderEffect leaderEffect;

                switch(name){
                    case "DISCOUNT":
                        resource = jsonLeaderEffect.get("resourceType").getAsString();
                        resourceType = ResourceType.valueOf(resource);
                        quantity = jsonLeaderEffect.get("quantity").getAsInt();
                        leaderEffect = new Discount(new Resource(resourceType),quantity);
                        leaderCards.add(new LeaderCard(id,leaderEffect,victoryPoints,requirements));

                        break;
                    case "EXTRA_PRODUCTION_POWER":
                        resource = jsonLeaderEffect.get("resourceType").getAsString();
                        resourceType = ResourceType.valueOf(resource);
                        quantity = jsonLeaderEffect.get("quantity").getAsInt();
                        id = jsonLeaderEffect.get("id").getAsInt();
                        leaderEffect = new ExtraProductionPower(new Resource(resourceType),quantity,id);
                        leaderCards.add(new LeaderCard(id,leaderEffect,victoryPoints,requirements));

                        break;
                    case "EXTRA_SLOT":
                        resource = jsonLeaderEffect.get("resourceType").getAsString();
                        resourceType = ResourceType.valueOf(resource);
                        quantity = jsonLeaderEffect.get("quantity").getAsInt();
                        leaderEffect = new ExtraSlot(new Resource(resourceType),quantity);
                        leaderCards.add(new LeaderCard(id,leaderEffect,victoryPoints,requirements));

                        break;
                    case "JOLLY_MARBLE":
                        resource = jsonLeaderEffect.get("resourceType").getAsString();
                        resourceType = ResourceType.valueOf(resource);
                        leaderEffect = new JollyMarble(new Resource(resourceType));
                        leaderCards.add(new LeaderCard(id,leaderEffect,victoryPoints,requirements));

                        break;
                }

            }
            return leaderCards;
        }
    }


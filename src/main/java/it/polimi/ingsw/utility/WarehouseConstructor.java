package it.polimi.ingsw.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.client.DummyModel.DummyDepot;
import it.polimi.ingsw.client.DummyModel.DummyExtraDepot;
import it.polimi.ingsw.client.DummyModel.DummyWareHouse;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.ExtraDepot;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class WarehouseConstructor {

    public static Depot[] parse(String json) throws NotPossibleToAdd {

        JsonParser parser = new JsonParser();
        JsonObject j1 = parser.parse(json).getAsJsonObject();
        JsonArray j = j1.getAsJsonArray("dummyDepots");

        Depot[] depots = new Depot[5];
        int i = 0;
        for (JsonElement deps : j) {
            JsonObject dep = deps.getAsJsonObject();

            int id = dep.get("id").getAsInt();
            int dimension = dep.get("dimension").getAsInt();
            JsonArray res = dep.getAsJsonArray("resources");
            ArrayList<Resource> resources = new ArrayList<>();
            for (JsonElement r : res) {
                String resource = r.getAsString();
                resources.add(new Resource(ResourceType.valueOf(resource)));
            }
            if ((id == 1) || (id == 2) || (id == 3)) {
                depots[i] = (new Depot(id, dimension, resources));
            } else {
                String type = dep.get("resourceType").getAsString();
                ResourceType resourceType;
                if(type.equals("")) {
                    resourceType = ResourceType.NONE;
                }else {
                    resourceType = ResourceType.valueOf(type);
                }
                depots[i] = (new ExtraDepot (dimension, id, resourceType));
                for(Resource r: resources){
                    depots[i].addResource(r);

                }
            }
            i++;
        }

        return depots;
    }
}

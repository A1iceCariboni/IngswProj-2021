package it.polimi.ingsw.utility;

import com.google.gson.*;
import it.polimi.ingsw.client.DummyModel.DummyDepot;
import it.polimi.ingsw.client.DummyModel.DummyExtraDepot;
import it.polimi.ingsw.client.DummyModel.DummyWareHouse;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DummyWarehouseConstructor {

    public static DummyWareHouse parse(String json) {

        JsonParser parser = new JsonParser();
        JsonObject j1 = parser.parse(json).getAsJsonObject();
        JsonArray j = j1.getAsJsonArray("dummyDepots");

        ArrayList<DummyDepot> dummyDepots = new ArrayList<>();
        for (JsonElement deps : j) {
            JsonObject dep = deps.getAsJsonObject();

            int id = dep.get("id").getAsInt();
            int dimension = dep.get("dimension").getAsInt();
            JsonArray res = dep.getAsJsonArray("resources");
            ArrayList<String> resources = new ArrayList<>();
            for (JsonElement r : res) {
                String resource = r.getAsString();
                resources.add(resource);
            }
            if ((id == 1) || (id == 2) || (id == 3)) {
                dummyDepots.add(new DummyDepot(id, dimension, resources));
            } else {
                String type = dep.get("resourceType").getAsString();
                dummyDepots.add(new DummyExtraDepot(id, dimension, resources, type));
            }
        }

        return new DummyWareHouse(dummyDepots);
    }

    public static DummyWareHouse parseVoid() throws JsonFileNotFoundException {

        String path = "/json/dummywarehousevoid.json";

        InputStream is = LeaderCardParser.class.getResourceAsStream(path);

        if (is == null) throw new JsonFileNotFoundException("File " + path + " not found");

        JsonParser parser = new JsonParser();
        JsonObject j1 = parser.parse(new InputStreamReader(is)).getAsJsonObject();
        JsonArray j = j1.getAsJsonArray("dummyDepots");

        ArrayList<DummyDepot> dummyDepots = new ArrayList<>();
        for (JsonElement deps : j) {
            JsonObject dep = deps.getAsJsonObject();

            int id = dep.get("id").getAsInt();
            int dimension = dep.get("dimension").getAsInt();
            JsonArray res = dep.getAsJsonArray("resources");
            ArrayList<String> resources = new ArrayList<>();
            for (JsonElement r : res) {
                String resource = r.getAsString();
                resources.add(resource);
            }
            if ((id == 1) || (id == 2) || (id == 3)) {
                dummyDepots.add(new DummyDepot(id, dimension, resources));
            } else {
                String type = dep.get("resourceType").getAsString();
                dummyDepots.add(new DummyExtraDepot(id, dimension, resources, type));
            }
        }

        return new DummyWareHouse(dummyDepots);

    }


}

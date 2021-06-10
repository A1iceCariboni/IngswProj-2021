package it.polimi.ingsw.utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class StrongBoxParser {

    public static ArrayList<Resource> parseFull(){

        String path = "/json/strongbox.json";

        Gson gson = new Gson();
        Reader reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));
        ResourceType[] res = gson.fromJson(reader, ResourceType[].class);
        ArrayList<Resource> resources = new ArrayList<>();
        for(ResourceType type: res){
            resources.add(new Resource(type));
        }
        return resources;
    }
}

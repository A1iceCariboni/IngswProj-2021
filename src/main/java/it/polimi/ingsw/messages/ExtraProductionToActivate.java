package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Resource;

public class ExtraProductionToActivate extends Message{
    public ExtraProductionToActivate(int id, String res) {
        super(MessageType.EXTRA_PRODUCTION, "");
        Gson gson = new Gson();
        String[] command = new String[2];
        command[0] = Integer.toString(id);
        command[1] = res;
        setPayload(gson.toJson(command));
    }

    public int getId(){
        Gson gson = new Gson();

        return Integer.parseInt(gson.fromJson(getPayload(), String[].class)[0]);
    }

    public Resource getResource(){
        Gson gson = new Gson();

        return new Resource(ResourceType.valueOf(gson.fromJson(getPayload(), String[].class)[1]));
    }
}

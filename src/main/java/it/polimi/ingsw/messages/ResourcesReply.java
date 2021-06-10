package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/**
 * message sent from the player with the chosen resources
 */
public class ResourcesReply extends Message {
     private String[] res;

    public ResourcesReply(String[] res) {
        super(MessageType.CHOOSE_RESOURCES, "");
        this.res = res;
        Gson gson = new Gson();
        setPayload(gson.toJson(res));
    }

    public ArrayList<Resource> getRes(){
        ArrayList<Resource> resources = new ArrayList<>();
        for(int i = 0; i < res.length; i++){
            if(res[i]!= null){
                resources.add(new Resource(ResourceType.valueOf(res[i])));
            }
        }
        return resources;
    }


}

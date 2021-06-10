package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

/**
 * message sent to client to choose a certain number of resources
 */
public class ResourceRequest extends Message{

        private int num;

        public ResourceRequest(int num) {
            super(MessageType.CHOOSE_RESOURCES, Integer.toString(num));
            this.num = num;
        }

        public int getNum(){
            return Integer.parseInt(getPayload());
        }


    }


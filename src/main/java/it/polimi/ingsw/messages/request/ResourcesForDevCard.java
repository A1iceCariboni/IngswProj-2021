package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;

/**
 * message from the client to notify the server about the resources to discard to buy the development card
 */
public class ResourcesForDevCard extends Message {

    private ArrayList<Resource> resources;

    /**
     * constructor to notify the server about the resources to discard to buy the development card
     * @param resources resources to use for buying the development card
     * @param payload
     */
    public ResourcesForDevCard(ArrayList<Resource> resources, String payload) {
        super(MessageType.RESOURCES_FOR_DEV_CARD, payload);
        this.resources = resources;
    }

    public ArrayList<Resource> getResources() {
        return this.resources;
    }
}

package it.polimi.ingsw.messages;

public class RemoveResource extends Message{
    public RemoveResource(int id) {
        super(MessageType.REMOVE_RESOURCES, Integer.toString(id));
    }

    public int getId(){
        return Integer.parseInt(getPayload());
    }
}

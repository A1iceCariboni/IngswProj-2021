package it.polimi.ingsw.messages;

public class GenericMessage extends Message{
    public GenericMessage(String message) {
        super(MessageType.GENERIC_MESSAGE, message);
    }

    public String getMessage(){
        return getPayload();
    }
}

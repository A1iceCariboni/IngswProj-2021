package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

/**
 * Is the message sent by the client to server with a nickname of his choice for the game
 * @author Alice Cariboni
 */
public class SetupMessage extends Message{

    public SetupMessage(String nickname){super(MessageType.SETUP,nickname);
    }

}

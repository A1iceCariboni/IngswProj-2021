package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

/**
 * message from client containinga the nuber of players
 */
public class NumberOfPlayerReply extends Message {

    public NumberOfPlayerReply(int num) {
        super(MessageType.NUMBER_OF_PLAYERS, Integer.toString(num));
    }

    public int getNumberOfPlayers(){
        return Integer.parseInt(getPayload());
    }
}

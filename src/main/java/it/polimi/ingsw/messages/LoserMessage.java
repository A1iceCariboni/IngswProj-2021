package it.polimi.ingsw.messages;

public class LoserMessage extends Message{


    public LoserMessage() {
        super(MessageType.LOSER, "You lose");
    }
}

package it.polimi.ingsw.messages;

public class WinnerMessage extends Message{
    public WinnerMessage() {
        super(MessageType.WINNER, "You won!");
    }
}

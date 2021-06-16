package it.polimi.ingsw.messages;

public class SeePlayerBoard extends Message{
    public SeePlayerBoard(String nickname) {
        super(MessageType.SEE_PLAYERBOARD, nickname);
    }

    public String getNickname(){
        return getPayload();
    }
}

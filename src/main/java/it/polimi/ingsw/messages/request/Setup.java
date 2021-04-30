package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;

/**
 * Is the message sent by the client to server with a nickname of his choice for the game
 * @author Alice Cariboni
 */
public class Setup implements Message {
    private final String nickname;

    public Setup(String nickname){
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Setup{" +
                "nickname='" + nickname + '\'' +
                '}';
    }
}

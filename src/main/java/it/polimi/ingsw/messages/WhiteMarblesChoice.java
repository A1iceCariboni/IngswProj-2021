package it.polimi.ingsw.messages;

import com.google.gson.Gson;

public class WhiteMarblesChoice extends Message{

    public WhiteMarblesChoice(String[] powers) {
        super(MessageType.WHITE_MARBLES, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(powers));
    }

    public WhiteMarblesChoice(int num) {
        super(MessageType.WHITE_MARBLES, Integer.toString(num));
    }

    public String[] getPowers(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), String[].class);
    }

    public int getNum(){
        return Integer.parseInt(getPayload());
    }
}

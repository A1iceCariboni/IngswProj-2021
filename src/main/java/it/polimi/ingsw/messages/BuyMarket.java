package it.polimi.ingsw.messages;

import com.google.gson.Gson;

public class BuyMarket extends Message{

    public BuyMarket( String roc,  int num) {
        super(MessageType.BUY_MARKET, "");
        Gson gson = new Gson();
        String[] command = new String[2];
        command[0] = roc;
        command[1] = Integer.toString(num);
        setPayload(gson.toJson(command));
    }

    public String getRoc() {

        Gson gson = new Gson();

        return gson.fromJson(getPayload(), String[].class)[0];
    }

    public int getNum() {
        Gson gson = new Gson();

        return Integer.parseInt(gson.fromJson(getPayload(), String[].class)[1]);
    }
}

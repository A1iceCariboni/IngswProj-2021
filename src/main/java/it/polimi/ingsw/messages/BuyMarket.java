package it.polimi.ingsw.messages;

import com.google.gson.Gson;

public class BuyMarket extends Message{
    private String roc;
    private int num;

    public BuyMarket( String roc,  int num) {
        super(MessageType.BUY_MARKET, "");
        Gson gson = new Gson();
        String[] command = new String[2];
        command[0] = roc;
        command[1] = Integer.toString(num);
        setPayload(gson.toJson(command));
        this.roc = roc;
        this.num = num;
    }

    public String getRoc() {
        return this.roc;
    }

    public int getNum() {
        return this.num;
    }
}

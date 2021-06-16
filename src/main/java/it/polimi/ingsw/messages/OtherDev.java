package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.model.cards.DevelopmentCard;

public class OtherDev extends Message {

    public OtherDev(DevelopmentCard[] slots) {
        super(MessageType.OTHER_DEV_SLOTS, "");
        Gson gson = new Gson();
        DummyDev[] dummyDevs = new DummyDev[3];
        for(int i = 0; i < Constants.DEV_SLOTS; i++){
            if(slots[i] != null) {
                dummyDevs[i] = slots[i].getDummy();
            }else{
                dummyDevs[i] = null;
            }
        }
        setPayload(gson.toJson(dummyDevs));
    }

    public DummyDev[] getDummyDevs(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), DummyDev[].class);
    }
}

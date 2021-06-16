package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;

public class OtherLeader extends Message{

    public OtherLeader(ArrayList<LeaderCard> lc) {
        super(MessageType.OTHER_LEADER, "");
        Gson gson = new Gson();
        ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
        for(LeaderCard leaderCard: lc){
            dummyLeaderCards.add(leaderCard.getDummy());
        }
        setPayload(gson.toJson(dummyLeaderCards));
    }

    public DummyLeaderCard[] getDummyLeader(){
        Gson gson = new Gson();
        return gson.fromJson(getPayload(), DummyLeaderCard[].class);
    }

}

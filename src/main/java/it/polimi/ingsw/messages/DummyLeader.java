package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;

public class DummyLeader extends Message{

    public DummyLeader(ArrayList<LeaderCard> lc) {
        super(MessageType.DUMMY_LEADER_CARD, "");
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

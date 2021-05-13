package it.polimi.ingsw.client;

import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.client.DummyModel.DummyPlayerBoard;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.model.FaithTrack;

import java.util.ArrayList;

/**
 * simplified version of the player model in the client
 * @author Sofia Canestraci
 */

public class VirtualModel {
    private final Cli cli;
    private String nickname;
    private DummyPlayerBoard playerBoard;
    private ArrayList<DummyLeaderCard> leaderCards;
    private DummyDev[][] boardDevCard;
    private DummyMarket dummyMarket;

    public VirtualModel(Cli cli, FaithTrack faithTrack) {
        this.cli = cli;
    }

    public VirtualModel(Cli cli){
        this.cli = cli;
        nickname = null;
        playerBoard = new DummyPlayerBoard();
        leaderCards = new ArrayList<>();
        boardDevCard = new DummyDev[3][4];
        dummyMarket = new DummyMarket(null, null);
    }

    public DummyPlayerBoard getPlayerBoard(){
        return this.playerBoard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void addLeaderCard(DummyLeaderCard[] card){
        for(int i=0; i<card.length; i++){
            this.leaderCards.add(card[i]);
        }
    }

    public ArrayList<DummyLeaderCard> getLeaderCards(){
        return leaderCards;
    }

    public void removeLeaderCard(int pos){
            leaderCards.remove(pos);
    }

    public void setBoardDevCard(DummyDev[][] cards){
        this.boardDevCard = cards;
    }

    public DummyDev[][] getBoardDevCard(){
        return this.boardDevCard;
    }

    public DummyMarket getDummyMarket(){
        return this.dummyMarket;
    }

    public void setDummyMarket(DummyMarket market){
        this.dummyMarket = market;
    }
}


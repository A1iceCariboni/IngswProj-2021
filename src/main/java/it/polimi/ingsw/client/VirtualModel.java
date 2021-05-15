package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FaithCell;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.MarketTray;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.LeaderCardParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * simplified version of the player model in the client
 * @author Sofia Canestraci
 */

public class VirtualModel {
    private String nickname;
    private DummyPlayerBoard playerBoard;
    private ArrayList<DummyLeaderCard> leaderCards;
    private DummyDev[][] boardDevCard;
    private DummyMarket dummyMarket;



    public VirtualModel(){
        nickname = null;
        playerBoard = new DummyPlayerBoard();
        leaderCards = new ArrayList<>();
        boardDevCard = new DummyDev[Constants.DEV_ROWS][Constants.DEV_COLS];
    }
   //TODO DA CANCELLARE, SOLO PER TESTARE CLI
    public  void initialize() throws JsonFileNotFoundException {
        //faith track
        String path = "/json/faithtrack.json";
        Gson gson = new Gson();
        Reader reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));
        this.playerBoard.setFaithTrack(new DummyFaithTrack(gson.fromJson(reader, DummyCell[].class)));
        String path1 = "/json/test/testdevelopment.json";

        //development card market
        Reader reader1 = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path1)));
        DevelopmentCard[] developmentCard = gson.fromJson(reader1,DevelopmentCard[].class);
        DummyDev[][] dummyDevs = new DummyDev[Constants.DEV_ROWS][Constants.DEV_COLS];
        int ind = 0;
        for(int i = 0; i < Constants.DEV_ROWS; i++){
            for(int j = 0; j < Constants.DEV_COLS; j++){
                dummyDevs[i][j] = developmentCard[ind].getDummy();
                ind ++;
            }
        }
        this.boardDevCard = dummyDevs;
        //development card slots
        path = "/json/test/testslots.json";
        reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));

        developmentCard = gson.fromJson(reader,DevelopmentCard[].class);
        DummyDev[] dummyDevs1 = new DummyDev[Constants.DEV_SLOTS];
        for(int i = 0; i< Constants.DEV_SLOTS; i++){
            dummyDevs1[i] = developmentCard[i].getDummy();
        }
        this.playerBoard.setDevSections(dummyDevs1);
       //leader cards
        ArrayList<LeaderCard> leaderCards = LeaderCardParser.parseLeadCards();
        for(int i = 0; i < 2; i++){
            this.leaderCards.add(leaderCards.get(i).getDummy());
        }

        //market
        MarketTray marketTray = new MarketTray();
        this.dummyMarket =  marketTray.getDummy();


        //dummy depots warehouse (tutti depot pieni + 1 extradepot di tipo coin vuoto)
        path = "/json/test/testwarehouse.json";
        reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));
        this.playerBoard.setWareHouse(gson.fromJson(reader, DummyWareHouse.class));
        DummyExtraDepot dummyExtraDepot = new DummyExtraDepot(4, 2, new ArrayList<>(),"COIN");
        this.playerBoard.getWareHouse().setExtraDepot1(dummyExtraDepot);
        System.out.println(gson.toJson(this.playerBoard.getWareHouse()));

        //white marble power, extra production power e discount power e strongbox
        ArrayList<String> resources = new ArrayList<>();
        resources.add("COIN");
        resources.add("SERVANT");
        DummyExtraProduction[] dummyExtraProduction = new DummyExtraProduction[]{new DummyExtraProduction(resources,1)};
        this.playerBoard.setExtraProduction(dummyExtraProduction);
        this.playerBoard.setWhiteMarblePower(resources);
        resources.add("SHIELD");
        this.playerBoard.setDiscountedResources(resources);
        resources.add("SHIELD");
        resources.add("SHIELD");
        this.playerBoard.setStrongBox(new DummyStrongbox(resources));
        System.out.println(gson.toJson(this.playerBoard));

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

    public static void main(String[] args) throws JsonFileNotFoundException {
        VirtualModel virtualModel = new VirtualModel();
        virtualModel.initialize();

    }

}


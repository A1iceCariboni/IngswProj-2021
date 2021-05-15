package it.polimi.ingsw.client.DummyModel;

import it.polimi.ingsw.enumerations.Constants;

import java.util.ArrayList;

/**
 * simplify playerBoard with the main things for the client
 */
public class DummyPlayerBoard {

    private DummyWareHouse wareHouse;
    private DummyStrongbox strongBox;
    private DummyFaithTrack faithTrack;
    private int faithMarker;
    private DummyDev[] devSections;
    private DummyExtraProduction[] extraProduction;
    private ArrayList<String> whiteMarblePower;
    private ArrayList<String> discountedResources;

    /**
     * simplified playerboard for the client
     */
    public DummyPlayerBoard(){
        wareHouse = new DummyWareHouse(new DummyDepot(1,1,new ArrayList<>()), new DummyDepot(2,2,new ArrayList<>()), new DummyDepot(3,3,new ArrayList<>()) );
        strongBox = new DummyStrongbox(new ArrayList<>());
        faithMarker = 0;
        devSections = new DummyDev[Constants.DEV_SLOTS];
        extraProduction = new DummyExtraProduction[2];
        whiteMarblePower = new ArrayList<>();
        discountedResources = new ArrayList<>();

    }

    public DummyStrongbox getStrongBox(){
        return this.strongBox;
    }

    public void setStrongBox(DummyStrongbox sbox){
        this.strongBox = sbox;
    }

    public DummyWareHouse getWareHouse(){
        return this.wareHouse;
    }

    public DummyFaithTrack getFaithTrack(){
        return this.faithTrack;
    }

    public void setFaithTrack(DummyFaithTrack newFaithTrack){
        this.faithTrack = newFaithTrack;
    }

    public int getFaithMarker(){
        return this.faithMarker;
    }

    public void moveFaithMarker(int pos){
        this.faithMarker = pos;
    }

    public DummyDev[] getDevSections() {
        return devSections;
    }

    public void setDevSections(DummyDev[] cards) {
        this.devSections = cards;
    }

    public void setExtraProduction(DummyExtraProduction[] extra){
        this.extraProduction = extra;
    }

    public void setWhiteMarblePower(ArrayList<String> whitePowers){
        this.whiteMarblePower = whitePowers;
    }

    public ArrayList<String> getWhiteMarblePower(){
        return this.whiteMarblePower;
    }

    public void setDiscountedResources(ArrayList<String> discounts){
        this.discountedResources = discounts;
    }
}

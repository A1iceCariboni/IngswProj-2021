package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

/**
 * simplify playerBoard with the main things for the client
 */
public class DummyPlayerBoard {

    private DummyWareHouse wareHouse;
    private DummyStrongbox strongBox;
    private DummyFaithTrack faithTrack;
    private int faithMarker;
    private DummyDev devSection1;
    private DummyDev devSection2;
    private DummyDev devSection3;
    private DummyExtraProduction[] extraProduction;
    private ArrayList<String> whiteMarblePower;
    private ArrayList<String> discountedResources;

    /**
     * simplified playerboard for the client
     */
    public DummyPlayerBoard(){
        wareHouse = new DummyWareHouse(new DummyDepot(1,1,null), new DummyDepot(2,2,null), new DummyDepot(3,3,null) );
        strongBox = new DummyStrongbox(null);
        faithTrack = new DummyFaithTrack();
        faithMarker = 0;
        devSection1 = null;
        devSection2 = null;
        devSection3 = null;
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

    public DummyDev getDevSection1() {
        return devSection1;
    }

    public void setDevSection1(DummyDev card) {
        this.devSection1 = card;
    }

    public DummyDev getDevSection2(){
        return devSection2;
    }

    public void setDevSection2(DummyDev card) {
        this.devSection2 = card;
    }

    public DummyDev getDevSection3(){
        return devSection3;
    }

    public void setDevSection3(DummyDev card){
        this.devSection3 = card;
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

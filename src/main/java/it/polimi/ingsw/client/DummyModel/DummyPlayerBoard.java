package it.polimi.ingsw.client.DummyModel;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.utility.DummyWarehouseConstructor;

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


    /**
     * simplified playerboard for the client
     */
    public DummyPlayerBoard(){
        try {
            wareHouse = DummyWarehouseConstructor.parseVoid();
        } catch (JsonFileNotFoundException e) {
            e.printStackTrace();
        }
        strongBox = new DummyStrongbox(new ArrayList<>());
        faithMarker = 0;
        devSections = new DummyDev[Constants.DEV_SLOTS];

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
        devSections = cards;
    }

    public void setWareHouse(DummyWareHouse wareHouse) {
        this.wareHouse.setDepot1(wareHouse.getDepot1());
        this.wareHouse.setDepot2(wareHouse.getDepot2());
        this.wareHouse.setDepot3(wareHouse.getDepot3());
        this.wareHouse.setExtraDepot1(wareHouse.getExtraDepot1());
        this.wareHouse.setExtraDepot2(wareHouse.getExtraDepot2());
    }

    public void setFaithMarker(int faithMarker) {
        this.faithMarker = faithMarker;
    }
}

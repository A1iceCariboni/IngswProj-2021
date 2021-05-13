package it.polimi.ingsw.client.DummyModel;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class DummyWareHouse {
    private DummyDepot depot1;
    private DummyDepot depot2;
    private DummyDepot depot3;
    private DummyExtraDepot extraDepot1 = null;
    private DummyExtraDepot extraDepot2 = null;

    public DummyWareHouse(DummyDepot depot1, DummyDepot depot2, DummyDepot depot3){
        this.depot1 = depot1;
        this.depot2 = depot2;
        this.depot3 = depot3;
    }

    public DummyWareHouse(DummyDepot depot1, DummyDepot depot2, DummyDepot depot3, DummyExtraDepot extraDepot1, DummyExtraDepot extraDepot2){
        this.depot1 = depot1;
        this.depot2 = depot2;
        this.depot3 = depot3;
        this.extraDepot1 = extraDepot1;
        this.extraDepot2 = extraDepot2;
    }

    public DummyDepot getDepot1(){
        return this.depot1;
    }

    public void setDepot1(DummyDepot depot1){
        this.depot1 = depot1;
    }

    public DummyDepot getDepot2(){
        return this.depot2;
    }

    public void setDepot2(DummyDepot depot2){
        this.depot2 = depot2;
    }

    public DummyDepot getDepot3(){
        return this.depot3;
    }

    public void setDepot3(DummyDepot depot3){
        this.depot3 = depot3;
    }

    public DummyExtraDepot getExtraDepot1(){
        return extraDepot1;
    }

    public void setExtraDepot1(DummyExtraDepot extra1){
        this.extraDepot1 = extra1;
    }

    public DummyExtraDepot getExtraDepot2(){
        return extraDepot2;
    }

    public void setExtraDepot2(DummyExtraDepot extra2){
        this.extraDepot2 = extra2;
    }
}

package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyWareHouse {
    private ArrayList<DummyDepot> dummyDepots;

    public DummyWareHouse( ArrayList<DummyDepot> dummyDepots){
      this.dummyDepots = dummyDepots;
    }



    public ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(DummyDepot dummyDepot: dummyDepots){
            if(dummyDepot.getId() != -1){
                ids.add(dummyDepot.getId());
            }
        }
        return ids;
    }

    public DummyDepot getDepot1(){
        return dummyDepots.get(0);
    }

    public void setDepot1(DummyDepot depot1){
        this.dummyDepots.set(0,depot1);
    }

    public DummyDepot getDepot2(){
        return dummyDepots.get(1);
    }

    public void setDepot2(DummyDepot depot2){
        this.dummyDepots.set(1,depot2);
    }

    public DummyDepot getDepot3(){
        return dummyDepots.get(2);
    }

    public void setDepot3(DummyDepot depot3){
        this.dummyDepots.set(2,depot3);
    }

    public DummyExtraDepot getExtraDepot1(){
        return (DummyExtraDepot) dummyDepots.get(3);
    }

    public void setExtraDepot1(DummyExtraDepot extra1){
        this.dummyDepots.set(3,extra1);
    }

    public DummyExtraDepot getExtraDepot2(){
        return (DummyExtraDepot) dummyDepots.get(4);
    }

    public void setExtraDepot2(DummyExtraDepot extra2){
        this.dummyDepots.set(4,extra2);
    }

    public ArrayList<DummyDepot> getDummyDepots() {
        return dummyDepots;
    }
}

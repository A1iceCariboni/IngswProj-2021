package it.polimi.ingsw.model;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.client.DummyModel.DummyDepot;
import it.polimi.ingsw.client.DummyModel.DummyExtraDepot;
import it.polimi.ingsw.client.DummyModel.DummyWareHouse;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * @author Alessandra Atria
 */

public class WareHouse {
    private ArrayList<Depot> depots;

    public WareHouse() {
        this.depots = new ArrayList<>();
        this.depots.add(new Depot(1,1, new ArrayList<>()));
        this.depots.add(new Depot(2,2, new ArrayList<>()));
        this.depots.add(new Depot(3,3, new ArrayList<>()));
        this.depots.add(new ExtraDepot(0, -1, ResourceType.NONE));
        this.depots.add(new ExtraDepot(0, -1, ResourceType.NONE));
    }

    /**
     * Checks if the resource can be moved from one depot to another
     * @param from is the depot where the resource res is taken
     * @param to is the depot where the resource res is moved
     * @param res is the resource moved
     */
    public void moveResouces(Depot from, Depot to, Resource res) throws NotPossibleToAdd {
        from.removeResource();
        to.addResource(res);
    }

    /**  adds a depot to warehouse
     */
    public void addDepot(Depot d){
          this.depots.add(d);
    }


    public ArrayList<Depot> getDepots(){
        return depots;
    }


    /** checks if warehouse has the resource
     */
    public boolean hasResource(Resource res) {
        for (Depot d : this.depots) {
            if (d.getDepot().contains(res)) {
                return true;
            }

        }
        return false;
    }

    /** This method gets the resources from the depot **/
    public ArrayList<Resource> getWarehouse(){
        ArrayList<Resource> res = new ArrayList<>();
        for(Depot d : this.depots){
            if(d.getId() != -1) {
                res.addAll(d.getDepot());
            }
        }
        return res;
    }


    /** This method removes the resource to the depot **/
    public void remove(Resource res){
        for(Depot d : this.depots){
            if(d.getId() != -1) {
                if(d.getDepot().contains(res)) {
                    d.removeResource();
                }
            }
        }
    }

    public void setExtraDepot(ExtraDepot extraDepot){
        if(depots.get(3).getId() == -1) {
            this.depots.set(3, extraDepot);
        }else{
            this.depots.set(4,extraDepot);
        }
    }

    public ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(Depot depot: depots){
            if(depot.getId() != -1){
                ids.add(depot.getId());
            }
        }
        return ids;
    }

    public DummyWareHouse getDummy(){
        ArrayList<DummyDepot>  dummyDepots = new ArrayList<>();
        for(Depot d: depots){
            dummyDepots.add(d.getDummy());
        }
        return new DummyWareHouse(dummyDepots);
    }

    /** This method adds the resource to the depot **/
    public void addRestoDepot(Resource res, Depot d) throws NotPossibleToAdd {
        for (int i = 0; i < depots.size(); i++) {
            if (depots.get(i) != depots.get(depots.indexOf(d))) {
                if (depots.contains(res)) {
                    throw new NotPossibleToAdd();
                } else {
                    depots.get(depots.indexOf(d)).addResource(res);
                }
            }
        }
    }
}

package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;

import java.util.ArrayList;

/**
 * @author Alessandra Atria
 */

public class WareHouse {
    private ArrayList<Depot> depots;

    public WareHouse() {
        this.depots = new ArrayList<>();
    }

    /**
     * Checks if the resource can be moved from one depot to another
     * @param from is the depot where the resource res is taken
     * @param to is the depot where the resource res is moved
     * @param res is the resource moved
     */
    public void moveResouces(Depot from, Depot to, Resource res) throws NotPossibleToAdd {
        from.removeResource(res);
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
}

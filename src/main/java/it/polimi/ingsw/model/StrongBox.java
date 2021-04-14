package it.polimi.ingsw.model;
import java.util.ArrayList;

/** author Alessandra Atria*/
public class StrongBox {
    private ArrayList<Resource> res;


    public StrongBox() {
        this.res = new ArrayList<>();
    }


    public ArrayList<Resource> getRes(){ return this.res;
    }

    /**adds product resource to strong box */
    public void addResources(Resource productResources) {
         res.add(productResources);
    }

    /**removes resource from strong box */
    public void removeResources(Resource Resources) {
        this.res.remove(Resources);
    }
}

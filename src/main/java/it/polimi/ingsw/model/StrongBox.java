package it.polimi.ingsw.model;
import it.polimi.ingsw.client.DummyModel.DummyStrongbox;

import java.io.Serializable;
import java.util.ArrayList;

/** author Alessandra Atria*/
public class StrongBox implements Serializable {
    private static final long serialVersionUID = -8009204854362906931L;

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

    public void removeAllResources(){
        this.res.removeAll(this.res);
    }

    /**removes resource from strong box */
    public void removeResources(Resource Resources) {
        this.res.remove(Resources);
    }

    public DummyStrongbox getDummy(){
        ArrayList<String> dummy = new ArrayList<>();
        for(Resource r: res){
            dummy.add(r.getResourceType().name());
        }
        DummyStrongbox dummyStrongbox = new DummyStrongbox(dummy);
        return dummyStrongbox;
    }

    public void setStrongbox(ArrayList<Resource> res){
        this.res = res;
    }
}

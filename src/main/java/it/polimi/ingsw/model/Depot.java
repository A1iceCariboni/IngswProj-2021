package it.polimi.ingsw.model;
import it.polimi.ingsw.client.DummyModel.DummyDepot;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Alessandra Atria
 */

    public class Depot implements Serializable {
    private static final long serialVersionUID = 7376232902862183189L;

    protected int id;
        protected int dimension;
        protected ArrayList<Resource> resources;

        public Depot(int dimension, int id, ArrayList<Resource> resources) {
            this.id = id;
            this.dimension = dimension;
            this.resources = resources;
        }


    /**
     * This method adds resources to the depot
     * @param res represents the resource to add
     * @throws NotPossibleToAdd if the resource can't be added
     */
        public void addResource(Resource res) throws NotPossibleToAdd {
            int countRes;
                countRes = this.resources.size() + 1 ;
            if(possibleToAdd(res) && countRes <= dimension){
                this.resources.add(res);
            }else{
                throw new NotPossibleToAdd();
            }
        }

        public boolean possibleToAdd(Resource res){
            return (resources.isEmpty() || res.getResourceType() == resources.get(0).getResourceType()) && resources.size() < dimension;
        }

    /**
     * This method removes resources from the depot
     */
        public void removeResource(){
               if(!this.resources.isEmpty()){this.resources.remove(0);}
        }

    /**
     * This method gets the resources that are in the depot
     */

    public ArrayList<Resource> getDepot(){
            return this.resources;
        }

    public void setDepot(ArrayList<Resource> res){
        this.resources = res;
    }
    /**
     * This method gets the maximum number of resources that the depot can contain
     */
        public int getDimension() {
            return this.dimension;
        }

    /**
     * This method checks if the depot doesn't have any resources
     */
        public boolean isEmpty(){
          return  resources.isEmpty();
        }


        public ResourceType getType(){
            if(this.isEmpty()){
                return ResourceType.NONE;
            }
            return this.resources.get(0).getResourceType();
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depot depot = (Depot) o;
        return this.id == depot.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, resources);
    }

    public int getId() {
        return id;
    }

    public DummyDepot getDummy(){
            ArrayList<String> res = new ArrayList<>();
            for(Resource r: resources){
                res.add(r.getResourceType().name());
            }
            return new DummyDepot(id, dimension, res);
    }

    /**
     * tells if this resource is ok for this depot , this is always true for generic depot
     * @return
     */
    public boolean possibleResource(Resource res){
            return true;
    }


}



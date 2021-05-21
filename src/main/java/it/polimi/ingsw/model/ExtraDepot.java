package it.polimi.ingsw.model;

import it.polimi.ingsw.client.DummyModel.DummyDepot;
import it.polimi.ingsw.client.DummyModel.DummyExtraDepot;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;

import java.util.ArrayList;
import java.util.Objects;

/**
     * @author Alessandra Atria
     */

    public class ExtraDepot extends Depot{

        private ResourceType type  ;

        public ExtraDepot(int dimension,int id, ResourceType type) {
            super( dimension,id, new ArrayList<>());

            this.type = type;
        }
        /**
         * This method adds resources of the same type to the depot
         * @param res represents the resource to add
         * @throws NotPossibleToAdd if the depot is full or if the resource is not of the same type
         */
        public void addResource(Resource res) throws NotPossibleToAdd {
            if(!possibleToAdd(res) || this.resources.size() >= dimension){
                throw new NotPossibleToAdd();
            }else{
                this.resources.add(res);
            }
        }


        @Override
        public boolean possibleToAdd(Resource res){
            if(super.possibleToAdd(res) && res.getResourceType() == type){
                return true;
            }
            return false;
        }
        /**
         * This method removes resources from the depot
         * @param res represents the resource to remove
         */
        public void removeResource(Resource res){
            this.resources.remove(res);

        }

        /**
         * This method gets the resources that are in the depot
         */


        public ArrayList<Resource> getDepot(){
            return this.resources;
        }

        /**
         * This method gets the maximum number of resources that the depot can contain
         */
        public int getDimension() {
            return this.dimension;
        }


    public ResourceType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraDepot that = (ExtraDepot) o;
        return dimension == that.dimension && Objects.equals(resources, that.resources) && Objects.equals(type, that.type);
    }
    public DummyExtraDepot getDummy(){
        ArrayList<String> res = new ArrayList<>();
        for(Resource r: resources){
            res.add(r.getResourceType().name());
        }
        return new DummyExtraDepot(id, dimension, res, type.name());
    }
    @Override
    public int hashCode() {
        return Objects.hash(dimension, resources, type);
    }

    @Override
    public boolean possibleResource(Resource res){
        if(res.getResourceType() == type){
            return true;
        }
        return false;
    }
}





package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;

import java.util.ArrayList;

/**
     * @author Alessandra Atria
     */

    public class ExtraDepot{
            private int dimension;
            private ArrayList<Resource> resType;
            private ResourceType type  ;

        public ExtraDepot(int dimension, ResourceType type) {
            this.dimension = dimension;
            this.resType = new ArrayList<>();
            this.type = type;
        }
        /**
         * This method adds resources of the same type to the depot
         * @param res represents the resource to add
         * @throws NotPossibleToAdd if the depot is full or if the resource is not of the same type
         */
        public void addResource(Resource res) throws NotPossibleToAdd {
            if(!res.getResourceType().equals(type) || this.resType.size() >= dimension){
                throw new NotPossibleToAdd();
            }else
                this.resType.add(res);
        }

        /**
         * This method removes resources from the depot
         * @param res represents the resource to remove
         */
        public void removeResource(Resource res){
            this.resType.remove(res);

        }

        /**
         * This method gets the resources that are in the depot
         */


        public ArrayList<Resource> getDepot(){
            return this.resType;
        }

        /**
         * This method gets the maximum number of resources that the depot can contain
         */
        public int getDimension() {
            return this.dimension;
        }


    }





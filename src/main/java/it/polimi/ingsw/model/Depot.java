package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;

import java.util.ArrayList;

/**
 * @author Alessandra Atria
 */

    public class Depot {
        private int dimension;
        private ArrayList<Resource> resources;

        public Depot(int dimension, ArrayList<Resource> resources) {
            this.dimension = dimension;
            this.resources = resources;
        }

    public Depot() {
    }

    /**
     * This method adds resources to the depot
     * @param res represents the resource to add
     */
        public void addResource(Resource res) throws NotPossibleToAdd {
            int countRes;
                countRes = this.resources.size();
                if(countRes < dimension)
                   this.resources.add(res);
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

    /**
     * This method checks if the depot doesn't have any resources
     */
        public boolean isEmpty(){
          return  resources.isEmpty();
        }


    }



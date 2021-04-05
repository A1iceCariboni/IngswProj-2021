package it.polimi.ingsw.model;


import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayerBoard {


    private ArrayList<Resource> res;

    public ArrayList<Resource> getResources() {
        return res;
    }

    public void removeResources(ArrayList<Resource> entryResources) {
    }

    public void addStrongBox(ArrayList<Resource> productResources) {
    }

    public ArrayList<DevelopmentCard> getDevCards() {
        return new ArrayList<>();
    }
}

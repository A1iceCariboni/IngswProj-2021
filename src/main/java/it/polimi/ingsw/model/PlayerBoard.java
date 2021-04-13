package it.polimi.ingsw.model;


import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;

public class PlayerBoard {

    private WareHouse wareHouse;
    private StrongBox strongBox;
    private DevelopmentCard[][] devCardSlots;
    private int faithMarker;
    private int countDevCards;
    private ArrayList<DevelopmentCard> coveredDevCards;

    public PlayerBoard() {
        this.wareHouse = new WareHouse();
        this.strongBox = new StrongBox();
        this.devCardSlots = new DevelopmentCard[3][4];
        this.faithMarker = 1;
        this. countDevCards = 0;
        this.coveredDevCards = new ArrayList<>();
    }


    public void removeResources(ArrayList<Resource> entryResources) {
    }

    public void addStrongBox(ArrayList<Resource> productResources) {
    }
    public void moveFaithMarker(int pos){
        this.faithMarker = this.faithMarker + pos;
    }

    public ArrayList<DevelopmentCard> getDevCards() {
        return new ArrayList<>();
    }

    public ArrayList<Resource> getResources() {
      return new ArrayList<>();
    }
}

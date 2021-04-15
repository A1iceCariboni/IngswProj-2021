package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import java.util.ArrayList;

/** @author Alessandra Atria
*/

public class PlayerBoard {
    private WareHouse wareHouse;
    private StrongBox strongBox;
    private DevelopmentCard[] devCardSlots;
    private int countDevCards;
    private ArrayList<DevelopmentCard> coveredDevCards;
    private ArrayList<DevelopmentCard> DevCards;
    private ArrayList<Resource> res;
    private int faithMarker;

    public PlayerBoard() {}

    public PlayerBoard(WareHouse wareHouse, StrongBox strongBox) {
        this.wareHouse =  wareHouse;
        this.strongBox = strongBox;
        this.devCardSlots = new DevelopmentCard[3];
        this.faithMarker = 1;
        this.countDevCards = 0;
        this.coveredDevCards = new ArrayList<>();
        this.DevCards = new ArrayList<>();
        this.res = new ArrayList<>();
    }


    public ArrayList<Resource> getResources() {
        return res;
    }

    //metodo da togliere
    public void removeResources(ArrayList<Resource> entryResources){
    }

    //metodo da togliere
    public void addStrongBox(ArrayList<Resource> productResources) {

    }


    public int getFaithMarker() {
        return this.faithMarker;
    }


    /** moves the faithmaker of n pos*/
    public void moveFaithMarker(int pos){
        this.faithMarker = this.faithMarker + pos;
    }

    //da togliere//
    public ArrayList<DevelopmentCard> getDevCards() {
        return DevCards;
    }

    public DevelopmentCard[] getDevelopmentCards() {
        return devCardSlots;
    }

    public int getCountDevCards() { return countDevCards;}


    /** adds an extra depot*/
    public void addExtraDepot(Resource resource, int dimension){
        ExtraDepot extraDepot = new ExtraDepot(dimension,resource);
        this.wareHouse.addDepot(extraDepot);
    }

    /** adds a card to Player Board*/
    public void addDevCard(DevelopmentCard card , int slot) throws CannotAdd{
            if(card.getLevel()==1) {
                if (devCardSlots[slot] == null) {
                    this.devCardSlots[slot] = card;
                    this.countDevCards ++;
                }
            }else{
            if(card.getLevel()==2) {
                if (devCardSlots[slot] == null) {
                    throw new CannotAdd();
                } else {
                    if (devCardSlots[slot].getLevel() < 1) {
                        throw new CannotAdd();
                    } else {
                        this.coveredDevCards.add(devCardSlots[slot]);
                        this.devCardSlots[slot] = card;
                        this.countDevCards ++;
                    }
                }
            }else{
            if(card.getLevel()==3){
                if (devCardSlots[slot] == null) {
                    throw new CannotAdd();
                }else{
                    if (devCardSlots[slot].getLevel() < 2) {
                        throw new CannotAdd();
                    } else {
                        this.coveredDevCards.add(devCardSlots[slot]);
                        this.devCardSlots[slot] = card;
                        this.countDevCards ++;
                    }
                }
            }
            }}
    }
}

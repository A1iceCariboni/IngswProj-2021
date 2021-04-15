package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import java.util.ArrayList;
import java.util.Arrays;

/** @author Alessandra Atria
*/

public class PlayerBoard {
    private WareHouse wareHouse;
    private StrongBox strongBox;
    private DevelopmentCard devCardSlots[];
    private int countDevCards;
    private ArrayList<DevelopmentCard> coveredDevCards;
    private ArrayList<DevelopmentCard> devCards;
    private int faithMarker;

    public PlayerBoard() {}

    public PlayerBoard(WareHouse wareHouse, StrongBox strongBox) {
        this.wareHouse =  wareHouse;
        this.strongBox = strongBox;
        this.devCardSlots = new DevelopmentCard[3];
        this.faithMarker =  1;
        this. countDevCards = 0;
        this.coveredDevCards = new ArrayList<>();
        this.devCards = new ArrayList<>();
        this.countDevCards = 0;
    }


    public ArrayList<Resource> getResources() {
        ArrayList<Resource> res = new ArrayList<>();
        res.addAll(this.wareHouse.getWarehouse());
        res.addAll(this.strongBox.getRes());
        return res;
    }


    public int getFaithMarker() {
        return this.faithMarker;
    }


    /** moves the faithmaker of n pos*/
    public void moveFaithMarker(int pos){
        this.faithMarker = this.faithMarker + pos;
    }


    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            if(devCardSlots[i] != null){
                developmentCards.add(devCardSlots[i]);
            }
        }
        return developmentCards;
    }

    /**
     *
     * @return all the development cards covered and not
     */
    public ArrayList<DevelopmentCard> getAllDevelopmentCards(){
        ArrayList<DevelopmentCard> allDev = new ArrayList<>();
        ArrayList<DevelopmentCard> dev = getDevelopmentCards();
        allDev.addAll(dev);
        allDev.addAll(coveredDevCards);
            return allDev;
    }



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
            }
            }
    }

    public ArrayList<DevelopmentCard> getCoveredDevCards() {
        return coveredDevCards;
    }



    public WareHouse getWareHouse() {
        return wareHouse;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }

    public int getCountDevCards() {
        return countDevCards;
    }
}

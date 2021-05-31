package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;

/** @author Alessandra Atria
*/

public class PlayerBoard implements Serializable {
    private static final long serialVersionUID = 7218667999815038982L;

    private WareHouse wareHouse;
    private StrongBox strongBox;
    private DevelopmentCard[] devCardSlots;
    private int countDevCards;
    private ArrayList<DevelopmentCard> coveredDevCards;
    private ArrayList<DevelopmentCard> devCards;
    private int faithMarker;
    private int depotId = 3;
    private final int extraProductionId = 0;
    private ArrayList<Resource> unplacedResources;

    public PlayerBoard() {}

    public PlayerBoard(WareHouse wareHouse, StrongBox strongBox) {
        this.wareHouse =  wareHouse;
        this.strongBox = strongBox;
        this.devCardSlots = new DevelopmentCard[3];
        this.faithMarker =  0;
        this. countDevCards = 0;
        this.coveredDevCards = new ArrayList<>();
        this.devCards = new ArrayList<>();
        this.countDevCards = 0;
        this.unplacedResources = new ArrayList<>();
    }


    public ArrayList<Resource> getResources() {
        ArrayList<Resource> res = new ArrayList<>();
        res.addAll(this.wareHouse.getResources());
        res.addAll(this.strongBox.getRes());
        return res;
    }


    public void addToExtraDepot(Resource resource, ExtraDepot extraDepot) throws NotPossibleToAdd {
        extraDepot.addResource(resource);
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

    /**
     * checks if there is place for this card to be added
     * @param card the card to be added
     * @return true if it is possible false otherwise
     */
    public boolean canAddDevCard(DevelopmentCard card) {
        for (int slot = 0; slot < 3; slot++) {
            if (card.getLevel() == 1) {
                if (devCardSlots[slot] == null) {
                    this.devCardSlots[slot] = card;
                    this.countDevCards++;
                }
            } else {
                if (card.getLevel() == 2) {
                    if (devCardSlots[slot] == null) {
                        return false;
                    } else {
                        if (devCardSlots[slot].getLevel() < 1) {
                            return false;
                        } else {
                            this.coveredDevCards.add(devCardSlots[slot]);
                            this.devCardSlots[slot] = card;
                            this.countDevCards++;
                        }
                    }
                } else {
                    if (card.getLevel() == 3) {
                        if (devCardSlots[slot] == null) {
                            return false;
                        } else {
                            if (devCardSlots[slot].getLevel() < 2) {
                                return false;
                            } else {
                                this.coveredDevCards.add(devCardSlots[slot]);
                                this.devCardSlots[slot] = card;
                                this.countDevCards++;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<DevelopmentCard> getCoveredDevCards() {
        return coveredDevCards;
    }


   public int getIdForDepot(){
        depotId ++;
        return depotId;
   }

   public void addUnplacedResource(Resource resource){
        this.unplacedResources.add(resource);
   }
   public void removeUnplacedResource(int pos){
        this.unplacedResources.remove(pos);
   }

    /**
     * activate the basic production power 2x entry resources 1x product resources on players choice
     * @param prodRes the product resource chosen by the player
     */
   public void activateBasicProduction(Resource prodRes){
        strongBox.addResources(prodRes);
   }
   public ArrayList<Resource> getUnplacedResources(){return unplacedResources;}

    public WareHouse getWareHouse() {
        return wareHouse;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }

    public int getCountDevCards() {
        return countDevCards;
    }

    public DevelopmentCard[] getDevCardSlots() {
        return this.devCardSlots;
    }
}

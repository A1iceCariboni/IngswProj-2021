package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyProductionPower;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.effects.ProductionPower;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Alice Cariboni
 * This class represents a developent card
 */
public class DevelopmentCard extends Card{
    private int id;
    private ArrayList<Resource> cost;
    private int level;
    private CardColor color;
    private ProductionPower productionPower;
    private int victoryPoints;


    public DevelopmentCard(int id, ArrayList<Resource> cost, int level, CardColor color , ProductionPower productionPower,int victoryPoints ) {
        this.id = id;
        this.color = color;
        this.level = level;
        this.cost = cost;
        this.victoryPoints = victoryPoints;
        this.productionPower = productionPower;
    }

    /**
     * Checks ia a player have enough resources to buy the card
     * @param b is the playerboard of the player who wants to buy the card
     * @return true if the player can buy the card, false if he doesn't
     */
    public boolean isBuyable(Player p){
        ArrayList<Resource> personalCost = new ArrayList<>();
        PlayerBoard b = p.getPlayerBoard();
        for(Resource r: p.getDiscountedResource()){
            personalCost.remove(r);
        }
        ArrayList<Resource> res = b.getResources();
        for(ResourceType resourceType : ResourceType.values()) {
            Resource resource = new Resource(resourceType);
            long isRequired = personalCost.stream()
                    .filter(r -> r.equals(resource))
                    .count();
            long playerHas = res.stream()
                    .filter(r -> r.equals(resource))
                    .count();
            if (playerHas < isRequired) {
                return false;
            }
        }
        if(!b.canAddDevCard(this)) {
            return false;
        }
        return true;
    }
    /**
     * this method apply the production to a playerboard, it removes the resources that are required to start the production
     * and add to the strongbox the resources that are produced
     * @param b playerboard in which che power is applied
     */
    public void startProduction(PlayerBoard b, Player p) {
        ArrayList<Resource> productResources = this.productionPower.getProductResources();
        long faithPoints= productResources.stream()
                            .filter(r -> r.equals(new Resource(ResourceType.FAITH)))
                            .count();
        productResources.removeIf(res -> res.equals(new Resource(ResourceType.FAITH)));



        p.addVictoryPoints((int) faithPoints);
        for (Resource productResourceElem : productResources) {
            b.getStrongBox().addResources(productResourceElem);
        }
    }

    public void addPointsTo(Player player){ player.addVictoryPoints(victoryPoints);
    }
    public CardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ProductionPower getProductionPower() {
        return productionPower;
    }

    public ArrayList<Resource> getCost() {
        return cost;
    }

    public DummyDev getDummy(){
        ArrayList<String> c = new ArrayList<>();
        for(Resource resource: cost){
            c.add(resource.toString());
        }
        ArrayList<String> e = new ArrayList<>();
        for(Resource resource: productionPower.getEntryResources()){
            e.add(resource.toString());
        }
        ArrayList<String> p = new ArrayList<>();
        for(Resource resource: productionPower.getProductResources()){
            p.add(resource.toString());
        }

        return new DummyDev(id, c,level, color.name(), new DummyProductionPower(e,p));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevelopmentCard that = (DevelopmentCard) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import it.polimi.ingsw.model.PlayerBoard;

import java.util.ArrayList;

/**
 * @author Alice Cariboni
 * This class represents a developent card
 */
public class DevelopmentCard extends Card{
    private CardColor color;
    private int level;
    private ArrayList<Resource> cost;
    private int victoryPoints;
    private ProductionPower productionPower;


    public DevelopmentCard(CardColor color, int level, ArrayList<Resource> cost, int victoryPoints, ProductionPower productionPower) {
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
    public boolean isBuyable(PlayerBoard b){
        ArrayList<Resource> res = b.getResources();
        for(ResourceType resourceType : ResourceType.values()) {
            Resource resource = new Resource(resourceType);
            long isRequired = cost.stream()
                    .filter(r -> r.equals(resource))
                    .count();
            long playerHas = res.stream()
                    .filter(r -> r.equals(resource))
                    .count();
            if (playerHas < isRequired) {
                return false;
            }
        }

        return true;
    }
    /**
     * this method apply the production to a playerboard, it removes the resources that are required to start the production
     * and add to the strongbox the resources that are produced
     * @param b playerboard in which che power is applied
     */
    public void startProduction(PlayerBoard b) {
        ArrayList<Resource> entryResources = this.productionPower.getEntryResources();
        ArrayList<Resource> productResources = this.productionPower.getProductResources();
        b.removeResources(entryResources);
        b.addStrongBox(productResources);
    }

    public void addPointsTo(Player player){
        player.addVictoryPoints(victoryPoints);
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
}

package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.effects.ProductionPower;
import jdk.internal.module.Resources;

import java.util.List;
import java.util.Map;

public class DevelopmentCard extends Card{
    private String color;
    private int level;
    private Map<Resource,Integer> cost;
    private int victoryPoints;
    private ProductionPower productionPower;


    public DevelopmentCard(String color, int level, Map<Resource,Integer> cost, int victoryPoints, ProductionPower productionPower) {
        this.color = color;
        this.level = level;
        this.cost = cost;
        this.victoryPoints = victoryPoints;
        this.productionPower = productionPower;
    }
    public boolean isBuyable(PlayerBoard b){
        List<Resources> res = b.getResources();


    }
    public String getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public List<Resource> getCost() {
        return cost;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ProductionPower getProductionPower() {
        return productionPower;
    }
}

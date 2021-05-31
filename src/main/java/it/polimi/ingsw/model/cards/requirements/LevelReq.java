package it.polimi.ingsw.model.cards.requirements;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  this class represent the requirement of a leader card that needs a certain level of development cards owned by the player who wants
 *  to activate it
 * @author Alice Cariboni
 */
public class LevelReq implements Requirement, Serializable {
    private final int level;
    private final CardColor color;
    private final int quantity;


    public LevelReq(int level, CardColor color, int quantity) {
        this.level = level;
        this.color = color;
        this.quantity = quantity;
    }


    @Override
    public boolean hasEnough(PlayerBoard b) {
        ArrayList<DevelopmentCard> dev = b.getAllDevelopmentCards();
        long c = dev.stream()
                    .filter(d -> ((d.getColor() == color)&&(d.getLevel() == level)))
                    .count();
        return c >= quantity;
    }

    @Override
    public String toString() {
        return "LevelReq{" +
                "level=" + level +
                ", color=" + color +
                ", quantity=" + quantity +
                '}';
    }
}

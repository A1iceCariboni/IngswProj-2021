package it.polimi.ingsw.model.cards.requirements;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  this class represent the requirement of a leader card that needs a certain color of development cards owned by the player who wants
 *  to activate it
 * @author Alice Cariboni
 */

public class ColorReq implements Requirement{
    private final CardColor color;
    private final int quantity;


    public ColorReq(CardColor color, int quantity) {
        this.color = color;
        this.quantity = quantity;
    }

    @Override
    public boolean hasEnough(PlayerBoard b) {
        ArrayList<DevelopmentCard> devCards  = b.getAllDevelopmentCards();
        long c = devCards.stream()
                .filter(d -> d.getColor() == color)
                .count();
        return c >= quantity;
    }

    @Override
    public String toString() {
        return "ColorReq{" +
                "color=" + color +
                ", quantity=" + quantity +
                '}';
    }
}

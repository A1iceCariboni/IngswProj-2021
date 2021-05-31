package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.MarbleColor;

import java.io.Serializable;

public class Marble implements Serializable {
    private static final long serialVersionUID = -1205174966470572983L;

    private final MarbleColor marbleColor;
    private MarbleEffect marbleEffect;


    public Marble(MarbleColor marbleColor, MarbleEffect marbleEffect) {
        this.marbleColor = marbleColor;
        this.marbleEffect = marbleEffect;
    }

    public void setMarbleEffect(MarbleEffect newMarbleEffect){
        this.marbleEffect = newMarbleEffect;
    }

    public MarbleColor getMarbleColor() {
        return marbleColor;
    }

    public MarbleEffect getMarbleEffect() {
        return marbleEffect;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Marble)) {
            return false;
        }
        Marble otherResource = (Marble)other;
        return otherResource.getMarbleColor() == marbleColor;
    }
}

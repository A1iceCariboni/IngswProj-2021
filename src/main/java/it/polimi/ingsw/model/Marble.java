package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.MarbleColor;

public class Marble{
    private MarbleColor marbleColor;
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
}

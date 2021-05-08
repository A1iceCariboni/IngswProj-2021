package it.polimi.ingsw.exceptions;


public class NullCardException extends Exception {
    public NullCardException() {
        super();
    }

    public String getMessage(){
        return "You don't have this card!";
    }
}
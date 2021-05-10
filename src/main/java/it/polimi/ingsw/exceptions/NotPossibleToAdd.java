package it.polimi.ingsw.exceptions;

public class NotPossibleToAdd extends Exception{


    public NotPossibleToAdd() {
        super();
    }

    public String getMessage(){
        return "Can't add to this depot!";
    }

}

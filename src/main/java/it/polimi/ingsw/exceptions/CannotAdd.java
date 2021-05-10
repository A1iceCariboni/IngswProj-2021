package it.polimi.ingsw.exceptions;

public class CannotAdd extends Exception{

        public CannotAdd() {
            super();
        }
    public String getMessage(){
        return "Can't add to this slot!";
    }

}

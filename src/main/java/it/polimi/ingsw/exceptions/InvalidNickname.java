package it.polimi.ingsw.exceptions;

public class InvalidNickname extends Throwable{
    public InvalidNickname(String s){
        System.out.println(s);
    }
}

package it.polimi.ingsw.observers;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CliObservable {
    private ArrayList<CliObserver> observers = new ArrayList<>();


    /**
     * add an observer
     * @param observer observer that is added
     */
    public void addObserver(CliObserver observer){
        this.observers.add(observer);
    }

    /**
     * remove an observer from list
     * @param observer observer to be removed
     */
    public void removeObserver(CliObserver observer){
        observers.remove(observer);
    }


    /**
     * notify all the observers
     * @param lambda to be applied on the observer
     */
    public void notifyObserver(Consumer<CliObserver> lambda){
        for(CliObserver observer : observers){
            lambda.accept(observer);
        }
    }
}

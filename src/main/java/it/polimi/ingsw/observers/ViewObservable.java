package it.polimi.ingsw.observers;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ViewObservable {
    protected final ArrayList<ViewObserver> observers = new ArrayList<>();


    /**
     * add an observer
     * @param observer observer that is added
     */
    public void addObserver(ViewObserver observer){
        observers.add(observer);
    }

    /**
     * remove an observer from list
     * @param observer observer to be removed
     */
    public void removeObserver(ViewObserver observer){
        observers.remove(observer);
    }

    /**
     * adds more than one observer
     * @param obs arraylist of observers to be added
     */
    public void addAllObservers(ArrayList<ViewObserver> obs){
            this.observers.addAll(obs);
    }
    /**
     * notify all the observers
     * @param lambda to be applied on the observer
     */
    public void notifyObserver(Consumer<ViewObserver> lambda){
        for(ViewObserver observer : observers){
            lambda.accept(observer);
        }
    }
}

package it.polimi.ingsw.observers;

import java.util.ArrayList;
import java.util.function.Consumer;

public class GuiObservable {
    private ArrayList<GuiObserver> observers = new ArrayList<>();


    /**
     * add an observer
     * @param observer observer that is added
     */
    public void addObserver(GuiObserver observer){
        this.observers.add(observer);
    }

    /**
     * remove an observer from list
     * @param observer observer to be removed
     */
    public void removeObserver(GuiObserver observer){
        observers.remove(observer);
    }


    /**
     * notify all the observers
     * @param lambda to be applied on the observer
     */
    public void notifyObserver(Consumer<GuiObserver> lambda){
        for(GuiObserver observer : observers){
            lambda.accept(observer);
        }
    }
}

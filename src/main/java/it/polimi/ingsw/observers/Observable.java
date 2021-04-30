package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class Observable {
   private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * add an observer
     * @param observer observer that is added
     */
   public void addObserver(Observer observer){
       this.observers.add(observer);
   }

    /**
     * remove an observer from list
     * @param observer observer to be removed
     */
   public void removeObserver(Observer observer){
       observers.remove(observer);
   }


    /**
     * notify all the observers
     * @param lambda to be applied on the observer
     */
   public void notifyObserver(Consumer<Observer> lambda){
       for(Observer observer : observers){
         lambda.accept(observer);
       }
   }
}

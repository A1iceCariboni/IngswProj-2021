package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;

import java.util.ArrayList;

public abstract class Observable {
   private final ArrayList<Observer> observers = new ArrayList<>();


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
     * @param line massage to be processed
     */
   public void notifyObserver(String line) {
       for (Observer observer : observers) {
           observer.update(line);
       }
   }
    public void notifyDisconnect(){
           for(Observer observer : observers){
               observer.onDisconnect();
           }
       }

}

package it.polimi.ingsw.observers;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.messages.Message;

import java.util.ArrayList;
import java.util.function.Consumer;

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
     * @param message massage to be processed
     */
   public void notifyObserver(Message message) throws JsonProcessingException {
    for(Observer observer : observers){
        observer.update(message);
    }
   }
}

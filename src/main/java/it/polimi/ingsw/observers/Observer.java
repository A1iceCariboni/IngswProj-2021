package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;

public interface Observer {
    void update(String line) ;
    void update(Message message);
}

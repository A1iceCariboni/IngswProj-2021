package it.polimi.ingsw.observers;

import it.polimi.ingsw.messages.Message;

public interface Observer {
    void update(Message message) ;
}

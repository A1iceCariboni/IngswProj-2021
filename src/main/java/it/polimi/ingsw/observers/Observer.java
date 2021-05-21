package it.polimi.ingsw.observers;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.messages.Message;

public interface Observer {
    void update(Message message) throws JsonProcessingException;
}

package it.polimi.ingsw.model.cards.requirements;

import it.polimi.ingsw.model.PlayerBoard;

public interface Requirement {
   boolean hasEnough(PlayerBoard b);
}

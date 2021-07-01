package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;

public interface LeaderEffect {
    void applyEffect(Player p, PlayerBoard b);
    String getEffectName();
    String getType();
    void deactivateEffect(Player p, PlayerBoard b);
}

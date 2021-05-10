package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.TurnPhase;

import java.util.List;

public class TurnController {
    private GameController gameController;
    private TurnPhase turnPhase;

    private final List<String> nickNamesQueue;

    private String activePlayer;

    public TurnController(GameController gameController, List<String> nickNamesQueue, String activePlayer){
        this.gameController = gameController;
        this.nickNamesQueue = nickNamesQueue;
        this.nickNamesQueue.remove(0);
        this.activePlayer = activePlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void nextTurn(){
        nickNamesQueue.add(activePlayer);
        activePlayer = nickNamesQueue.get(0);
        nickNamesQueue.remove(0);
        if(gameController.getPlayers().get(0).equals(activePlayer)){
            changeGamePhase();
        }
    }

    public void changeGamePhase(){

    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

import java.util.List;

public class TurnController {
    private GameController gameController;
    private TurnPhase turnPhase;
    private int gameActionPerTurn;

    private final List<String> nickNamesQueue;

    private String activePlayer;

    public TurnController(GameController gameController, List<String> nickNamesQueue, String activePlayer){
        this.gameController = gameController;
        this.nickNamesQueue = nickNamesQueue;
        this.nickNamesQueue.remove(0);
        this.activePlayer = activePlayer;
        this.gameActionPerTurn = 0;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void nextTurn(){
        nickNamesQueue.add(activePlayer);
        activePlayer = nickNamesQueue.get(0);
        nickNamesQueue.remove(0);
        gameActionPerTurn = 0;
        if(gameController.getPlayers().get(0).equals(activePlayer)){
            changeGamePhase();
        }
        switch(gameController.getGamePhase()){
            case FIRST_ROUND:
                gameController.getVirtualViewByNickname(activePlayer).update(new Message(MessageType.DISCARD_LEADER, "Choose 2 leader to discard"));
                break;
            case IN_GAME:
                break;
        }
    }



    public void changeGamePhase(){
          switch(gameController.getGamePhase()){
              case FIRST_ROUND:
                  gameController.setGamePhase(GamePhase.IN_GAME);

                  break;
          }
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;


    }

    public void doneGameAction(){
        gameActionPerTurn = 1;
    }

    public boolean isGameActionDone(){
        return gameActionPerTurn == 1;
    }
}

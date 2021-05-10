package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;

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
        gameController.getVirtualViewByNickname(activePlayer).update(new Message(MessageType.NOTIFY_TURN, "It's your turn!"));
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

    public void changeTurnPhase(){
        if(turnPhase == TurnPhase.GAME_ACTION){
            turnPhase = TurnPhase.LEADER_ACTION;
        }
    }

    public void changeGamePhase(){
          switch(gameController.getGamePhase()){
              case FIRST_ROUND:
                  gameController.setGamePhase(GamePhase.IN_GAME);
                  turnPhase = TurnPhase.GAME_ACTION;

                  break;
          }
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }
}

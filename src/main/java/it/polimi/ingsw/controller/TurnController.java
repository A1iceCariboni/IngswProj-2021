package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.server.VirtualView;

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
        VirtualView vv = gameController.getVirtualView(activePlayer);
        switch(gameController.getGamePhase()){
            case IN_GAME:
                if(!((isGameActionDone())&&
                        (vv.getCardsToActivate().isEmpty())&&
                        (vv.getExtraProductionToActivate().isEmpty())&&
                        (vv.getTempDepots().isEmpty())&&
                        (vv.getFreeMarble().isEmpty())&&
                        (vv.getFreeResources().isEmpty())&&
                        (vv.getResourcesToPay().isEmpty())&&
                        (vv.getFreeDevelopment().isEmpty())&&
                        (vv.getResourcesToProduce().isEmpty())&&
                        (vv.getTempStrongBox().getRes().isEmpty())&&
                        (gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty()))){
                    vv.update(new ErrorMessage("You have some things to do first!"));
                }else{
                    nextPlayer();
                }
                break;
            case FIRST_ROUND:
                if((gameController.getGame().getCurrentPlayer().getLeadercards().size() == 2)&&
                    (gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty())){
                    nextPlayer();
                }else{
                    vv.update(new ErrorMessage("You have some things to do first!"));
                }
                break;
        }

    }


    public void nextPlayer(){
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
                gameController.sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's "+activePlayer+" turn, wait!"), gameController.getVirtualView(activePlayer));
                break;
            case IN_GAME:
            case LAST_ROUND:
                gameController.getVirtualView(activePlayer).update(new Message(MessageType.NOTIFY_TURN, "It's your turn!"));
                gameController.sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's "+activePlayer+" turn, wait!"), gameController.getVirtualView(activePlayer));
                break;
        }
    }
    public void changeGamePhase(){
          switch(gameController.getGamePhase()){
              case FIRST_ROUND:
                  gameController.setGamePhase(GamePhase.IN_GAME);
                  break;
              case IN_GAME:
                  if(gameController.getGame().checkEndGame()){
                      gameController.setGamePhase(GamePhase.LAST_ROUND);
                  }
                  break;
              case LAST_ROUND:
                  gameController.endGame();
                  break;
          }
    }



    public void doneGameAction(){
        gameActionPerTurn = 1;
    }

    public boolean isGameActionDone(){
        return gameActionPerTurn == 1;
    }
}

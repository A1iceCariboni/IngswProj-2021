package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.VirtualView;

import java.util.List;

public class TurnController {
    private GameController gameController;
    private TurnPhase turnPhase;
    private int gameActionPerTurn;
    private Game game;




    private final List<String> nickNamesQueue;

    private String activePlayer;

    public TurnController(GameController gameController, List<String> nickNamesQueue, String activePlayer, Game game){
        this.gameController = gameController;
        this.nickNamesQueue = nickNamesQueue;
        this.nickNamesQueue.remove(0);
        this.activePlayer = activePlayer;
        this.gameActionPerTurn = 0;
        this.game = game;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * checks if the turn is completed and if the game has changed phase before proceeding to the next player
     */
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
                        (gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty()))){
                    vv.update(new ErrorMessage("You have some things to do first!"));
                    vv.update(new Message(MessageType.NOTIFY_TURN,""));
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
                    vv.update(new Message(MessageType.NOTIFY_TURN, ""));
                }
                break;
        }

    }

    /**
     * check if the players have completed a round
     */
    public void nextPlayer(){
        nickNamesQueue.add(activePlayer);
        activePlayer = nickNamesQueue.get(0);
        nickNamesQueue.remove(0);
        gameActionPerTurn = 0;
        if(gameController.getPlayers().get(0).equals(activePlayer)){
            changeGamePhase();
        }
        game.nextPlayer();
        gameController.getVirtualViewByNickname(activePlayer).update(new Message(MessageType.NOTIFY_TURN, ""));
        gameController.sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's "+activePlayer+" turn, wait!"), gameController.getVirtualView(activePlayer));
        gameController.getVirtualViewByNickname(nickNamesQueue.get(nickNamesQueue.size()-1)).update(new Message(MessageType.END_TURN, ""));
    }


    public void changeGamePhase(){
          switch(gameController.getGamePhase()){
              case FIRST_ROUND:
                  gameController.setGamePhase(GamePhase.IN_GAME);
                  break;
              case IN_GAME:
                  if(gameController.getGame().checkEndGame()){
                      gameController.setGamePhase(GamePhase.LAST_ROUND);
                      gameController.sendAll(new Message(MessageType.GENERIC_MESSAGE, "Last round!"));
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

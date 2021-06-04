package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.Persistence;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TurnController implements Serializable {

    private static final long serialVersionUID = -4041289575909035845L;

    private  GameController gameController;
    private int gameActionPerTurn;
    private final Game game;


//TODO methods deleteTurn and randomFirstTurn

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
                if(gameController.getDisconnectedClients().contains(activePlayer)){
                    nextPlayer();
                }else {
                    if (!((vv.isGameActionDone()) &&
                            (vv.getCardsToActivate().isEmpty()) &&
                            (vv.getExtraProductionToActivate().isEmpty()) &&
                            (vv.getFreeMarble().isEmpty()) &&
                            (vv.getFreeResources().isEmpty()) &&
                            (vv.getResourcesToPay().isEmpty()) &&
                            (vv.getFreeDevelopment().isEmpty()) &&
                            (vv.getResourcesToProduce().isEmpty()) &&
                            (gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty()))) {

                        vv.update(new ErrorMessage("You have some things to do first!"));
                        vv.update(new Message(MessageType.NOTIFY_TURN, ""));

                    } else {
                        nextPlayer();
                    }
                }
                break;
            case FIRST_ROUND:
                if((gameController.getGame().getCurrentPlayer().getLeadercards().size() <= 2)&&
                    (gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty())){
                    nextPlayer();
                }else{
                    if(gameController.getDisconnectedClients().contains(activePlayer)){
                        randomFirstRound();
                        nextPlayer();
                    }else {
                        vv.update(new ErrorMessage("You have some things to do first!"));
                        vv.update(new Message(MessageType.NOTIFY_TURN, ""));
                    }
                }
                break;
        }

    }

    /**
     * complete the turn of a player who has disconnected on the first round
     */
    private void randomFirstRound() {
        while(game.getCurrentPlayer().getLeadercards().size() > 2){
            try {
                game.getCurrentPlayer().discardLeader(game.getCurrentPlayer().getLeadercards().get(0));
            } catch (NullCardException e) {
                e.printStackTrace();
            }
        }
        int i = 1;
        while(!game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty()) {
            Resource res = game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(0);
            Depot d = game.getCurrentPlayer().getDepotById(i);
            try{
               game.getCurrentPlayer().getPlayerBoard().getWareHouse().addToDepot(res, d);
               game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().remove(res);
            }catch (NotPossibleToAdd notPossibleToAdd) {
            }
        }
    }



    /**
     * check if the players have completed a round
     */
    private void nextPlayer(){
        do{
            nickNamesQueue.add(activePlayer);
            activePlayer = nickNamesQueue.get(0);
            nickNamesQueue.remove(0);
            gameController.getVirtualView(activePlayer).doneGameAction(0);
            if (gameController.getPlayers().get(0).equals(activePlayer)) {
                changeGamePhase();
            }
            game.nextPlayer();
        }while(gameController.getDisconnectedClients().contains(activePlayer)) ;
        gameController.setTurnPhase(TurnPhase.FREE);
        gameController.fakePlayerMove();
        gameController.getVirtualView(activePlayer).update(new Message(MessageType.NOTIFY_TURN, ""));
        gameController.sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's "+activePlayer+" turn, wait!"), gameController.getVirtualView(activePlayer));
        if(nickNamesQueue.size() >= 1) {
            gameController.getVirtualView(nickNamesQueue.get(nickNamesQueue.size() - 1)).update(new Message(MessageType.END_TURN, ""));
        }
        Persistence persistence = new Persistence();
        persistence.store(gameController);
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



}

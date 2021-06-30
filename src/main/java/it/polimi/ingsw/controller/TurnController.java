package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.Persistence;

import java.io.Serializable;
import java.util.List;

public class TurnController implements Serializable {

    private static final long serialVersionUID = -4041289575909035845L;

    private  GameController gameController;
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
            case LAST_ROUND:
                if(gameController.getDisconnectedClients().contains(activePlayer)){
                    Persistence persistence = new Persistence();
                    GameController savedGameController = persistence.restore();
                    gameController.setGame(savedGameController.getGame());
                    game = savedGameController.getGame();

                    for(String name : gameController.getConnectedClients().keySet()) {
                        gameController.sendUpdateMarketDev(gameController.getVirtualView(name),name );
                        gameController.sendUpdateFaithTrack(gameController.getVirtualView(name),name);
                        gameController.sendUpdateMarketTray(gameController.getVirtualView(name));
                    }
                    nextPlayer();
                }else {
                    if (!((vv.isGameActionDone()) &&
                            (vv.getCardsToActivate().isEmpty()) &&
                            (vv.getExtraProductionToActivate().isEmpty()) &&
                            (vv.getFreeMarble().isEmpty()) &&
                            (vv.getResourcesToPay().isEmpty()) &&
                            (game.getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment() == null) &&
                            (vv.getResourcesToProduce().isEmpty()) &&
                            (gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty()))) {

                        vv.update(new ErrorMessage("You have some things to do first!"));
                        vv.update(new NotifyTurn());

                    } else {
                        vv.update(new VictoryPoints(game.getCurrentPlayer().getVictoryPoints()));
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
                        vv.update(new NotifyTurn());
                    }
                }
                break;
        }

    }

    /**
     * complete the turn of a player who has disconnected on the first round
     */
    public void randomFirstRound() {
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
            Depot d = null;
            try {
                d = game.getCurrentPlayer().getDepotById(i);
            } catch (NotPossibleToAdd notPossibleToAdd) {

            }
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
    public void nextPlayer(){
        changeGamePhase();
        do{
            nickNamesQueue.add(activePlayer);
            activePlayer = nickNamesQueue.get(0);
            nickNamesQueue.remove(0);
            if(!gameController.getDisconnectedClients().contains(activePlayer)) {
                gameController.getVirtualView(activePlayer).doneGameAction(0);
            }

            game.nextPlayer();
        }while((gameController.getDisconnectedClients().contains(activePlayer)) && (gameController.getDisconnectedClients().size() < gameController.getNumberOfPlayers())) ;
        if(gameController.getDisconnectedClients().size() < gameController.getNumberOfPlayers()) {
            gameController.setTurnPhase(TurnPhase.FREE);
            gameController.fakePlayerMove();
            gameController.getVirtualView(activePlayer).update(new NotifyTurn());
            gameController.sendAllExcept(new GenericMessage("It's " + activePlayer + " turn, wait!"), gameController.getVirtualView(activePlayer));
            if ((nickNamesQueue.size() >= 1) && (!gameController.getDisconnectedClients().contains(nickNamesQueue.get(nickNamesQueue.size() - 1)))) {
                gameController.getVirtualView(nickNamesQueue.get(nickNamesQueue.size() - 1)).update(new EndTurn());
            }
            if (!gameController.getGamePhase().equals(GamePhase.END)) {
                Persistence persistence = new Persistence();
                persistence.store(gameController);
            }
        }
    }


    public void changeGamePhase(){
          switch(gameController.getGamePhase()){
              case FIRST_ROUND:
                  if (gameController.getPlayers().get(gameController.getPlayers().size()-1).equals(activePlayer)) {
                      gameController.setGamePhase(GamePhase.IN_GAME);
                  }
                  break;
              case IN_GAME:
                  if(gameController.getGame().checkEndGame()){
                      gameController.setGamePhase(GamePhase.LAST_ROUND);
                      gameController.sendAll(new GenericMessage("Last round!"));
                  }
                  break;
              case LAST_ROUND:
                  if (activePlayer.equals(game.getFirstPlayer().getNickName())) {
                      gameController.endGame();
                  }
                  break;
          }
    }

    /**
     * skips the turn until arriving to the first player reconnected
     * @param nickname nickname of the first player who has reconnected
     */
    public void goTo(String nickname){
        while(!game.getCurrentPlayer().getNickName().equals(nickname)){
            nickNamesQueue.add(activePlayer);
            activePlayer = nickNamesQueue.get(0);
            nickNamesQueue.remove(0);
            game.nextPlayer();
        }
        gameController.setTurnPhase(TurnPhase.FREE);
        gameController.getVirtualView(activePlayer).update(new NotifyTurn());
    }



}

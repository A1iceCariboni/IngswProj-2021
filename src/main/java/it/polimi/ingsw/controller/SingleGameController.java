package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.PlayerMove;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.InvalidNickname;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SingleGame;
import it.polimi.ingsw.model.cards.TokenDeck;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.Persistence;

import java.util.ArrayList;

public class SingleGameController extends GameController{



    /**
     * method start game create a new instance of multigame give 4 leadercard to each player and sends
     * them as dummy leader card to the client it also sends all the grid of development card,
     * the market tray structure and the faithtrack, it also gives the initial resources to the players
     */
    @Override
    public void startGame() {
        try{
            Server.LOGGER.info("instantiating game");
            this.game = new SingleGame();
            super.startGame();
        }catch (JsonFileNotFoundException ex){
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }





    @Override
    public synchronized void endGame() {
        ArrayList<Player> winner = game.getWinners();
        if(winner.isEmpty()){
            getVirtualView(turnController.getActivePlayer()).update(new LoserMessage());
        }else{
            getVirtualView(turnController.getActivePlayer()).update(new WinnerMessage());
        }
        getVirtualView(turnController.getActivePlayer()).update(new VictoryPoints(game.getCurrentPlayer().getVictoryPoints()));

        gamePhase = GamePhase.END;
        for(VirtualView view: connectedClients.values()){
            view.close();
        }
        Persistence persistence = new Persistence();
        persistence.delete();
    }

    @Override
    public void fakePlayerMove(){
        TokenDeck tokenDeck = null;
        try {
            tokenDeck = game.getFakePlayer().getTokenDeck();
        } catch (InvalidNickname invalidNickname) {
        }
        switch (tokenDeck.getPickedTokens().get(tokenDeck.getPickedTokens().size() - 1).getTokenType()) {
            case MOVE_2, MOVE_AND_SHUFFLE -> {
                if (game.checkPopeSpace())
                    updateFaith(getVirtualView(turnController.getActivePlayer()), turnController.getActivePlayer());
                sendAll(new GenericMessage("Your opponent picked the " +
                        tokenDeck.getPickedTokens().get(tokenDeck.getPickedTokens().size() - 1).getTokenType() +
                        " token"));
            }
            case DRAW_BLUE, DRAW_GREEN, DRAW_PURPLE, DRAW_YELLOW -> {
                sendUpdateMarketDev(getVirtualView(turnController.getActivePlayer()), turnController.getActivePlayer());
                sendAll(new GenericMessage("Your opponent picked the " +
                        tokenDeck.getPickedTokens().get(tokenDeck.getPickedTokens().size() - 1).getTokenType() +
                        " token"));
            }
        }
    }

    @Override
    public void removeResource(int id) {
        try {
            if(this.game.getCurrentPlayer().getDepotById(id).isEmpty()){
                this.getVirtualView(this.turnController.getActivePlayer()).update(new ErrorMessage("This depot is empty"));
            }else{
                this.game.getCurrentPlayer().getDepotById(id).removeResource();
                game.getFakePlayer().moveBlackCross(1);

                }
        } catch (NotPossibleToAdd | InvalidNickname notPossibleToAdd) {

        }
        sendAllUpdateFaith();
            this.sendDepots(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());

        this.getVirtualView(this.turnController.getActivePlayer()).update(new NotifyTurn());

}

@Override
public void putResource(int[] id) throws NotPossibleToAdd{
    for (int j : id) {
        if (j == -1) {
            try {
                game.getFakePlayer().moveBlackCross(1);
            } catch (InvalidNickname invalidNickname) {
            }

        } else {
            Depot d = game.getCurrentPlayer().getDepotById(j);
            game.getCurrentPlayer().getPlayerBoard().getWareHouse().addToDepot(this.game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(0), d);
        }
        this.game.getCurrentPlayer().getPlayerBoard().removeUnplacedResource(0);
    }
    sendAllUpdateFaith();
    this.sendDepots(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());
    this.sendStrongBox(this.connectedClients.get(this.turnController.getActivePlayer()) , this.turnController.getActivePlayer());
    this.setTurnPhase(TurnPhase.FREE);
    this.sendUpdateMove(PlayerMove.BUY_RESOURCES);
}


}

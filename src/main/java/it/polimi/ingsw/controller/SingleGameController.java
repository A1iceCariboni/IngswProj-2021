package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SingleGame;
import it.polimi.ingsw.model.cards.TokenDeck;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;

public class SingleGameController extends GameController{
        private SingleGame singleGame;



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
    public void endGame() {
        ArrayList<Player> winner = game.getWinners();
        if(winner.isEmpty()){
            getVirtualViewByNickname(turnController.getActivePlayer()).update(new Message(MessageType.LOSER, ""));
        }else{
            getVirtualViewByNickname(turnController.getActivePlayer()).update(new Message(MessageType.WINNER, ""));
        }
    }

    @Override
    public void fakePlayerMove(){
        TokenDeck tokenDeck = game.getFakePlayer().getTokenDeck();
        switch(tokenDeck.getPickedTokens().get(tokenDeck.getPickedTokens().size() - 1).getTokenType()){
            case MOVE_2 :
            case MOVE_AND_SHUFFLE:
                if(game.checkPopeSpace()) updateFaith();
                sendAll(new Message(MessageType.GENERIC_MESSAGE, "Your opponent picked the " +
                        tokenDeck.getPickedTokens().get(tokenDeck.getPickedTokens().size() - 1).getTokenType() +
                        " token"));
                break;
            case DRAW_BLUE:
            case DRAW_GREEN:
            case DRAW_PURPLE:
            case DRAW_YELLOW:
                sendUpdateMarketDev();
                sendAll(new Message(MessageType.GENERIC_MESSAGE, "Your opponent picked the " +
                        tokenDeck.getPickedTokens().get(tokenDeck.getPickedTokens().size() - 1).getTokenType() +
                        " token"));
                break;
        }
    }


}

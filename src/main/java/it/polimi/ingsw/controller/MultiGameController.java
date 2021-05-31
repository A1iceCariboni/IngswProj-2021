package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class MultiGameController extends GameController {


    /**
     * method start game create a new instance of multigame give 4 leadercard to each player and sends
     * them as dummy leader card to the client it also sends all the grid of development card,
     * the market tray structure and the faithtrack, it also gives the initial resources to the players
     */
   public void startGame() {
   try{
       this.game = new MultiGame();
       super.startGame();
   } catch (JsonFileNotFoundException e) {
        sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
    }
   }







    @Override
    public void endGame() {
        ArrayList<Player> winners = game.getWinners();
        if(winners.size() > 1){
            for(Player p: winners){
                getVirtualView(p.getNickName()).update(new Message(MessageType.WINNER,""));
                players.remove(p.getNickName());
            }
            for(String name: players){
                getVirtualView(name).update(new Message(MessageType.LOSER, ""));
            }
        }else{
            getVirtualView(winners.get(0).getNickName()).update(new Message(MessageType.WINNER,""));
            sendAllExcept(new Message(MessageType.LOSER, ""), getVirtualView(winners.get(0).getNickName()));
        }
    }


}






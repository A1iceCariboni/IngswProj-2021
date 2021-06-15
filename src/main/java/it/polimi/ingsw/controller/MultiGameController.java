package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.exceptions.InvalidNickname;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.Persistence;

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
    public synchronized void endGame() {
        ArrayList<Player> winners = game.getWinners();
            for(Player p: winners){
                getVirtualView(p.getNickName()).update(new WinnerMessage());
                try {
                    getVirtualView(p.getNickName()).update(new Message(MessageType.VICTORY_POINTS,gson.toJson(game.getPlayerByNickname(p.getNickName()).getVictoryPoints())));
                } catch (InvalidNickname invalidNickname) {
                    invalidNickname.printStackTrace();
                }


            }
            for(Player player: game.getPlayers()){
                if(!winners.contains(player)) {
                    getVirtualView(player.getNickName()).update(new LoserMessage());
                    try {
                        getVirtualView(player.getNickName()).update(new Message(MessageType.VICTORY_POINTS, gson.toJson(game.getPlayerByNickname(player.getNickName()).getVictoryPoints())));
                    } catch (InvalidNickname invalidNickname) {
                        invalidNickname.printStackTrace();
                    }
                }
            }

        gamePhase = GamePhase.END;
        for(VirtualView view: connectedClients.values()){
            view.close();
        }
        Persistence persistence = new Persistence();
        persistence.delete();
   }



}






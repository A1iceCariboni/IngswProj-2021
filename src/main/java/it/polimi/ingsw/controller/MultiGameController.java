package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;

public class MultiGameController extends GameController {


    /**
     * method start game create a new instance of multigame give 4 leadercard to each player and sends
     * them as dummy leader card to the client it also sends all the grid of development card,
     * the market tray structure and the faithtrack, it also gives the initial resources to the players
     */
    @Override
    public void startGame() {
        try {
            Server.LOGGER.info("instantiating game");
            this.game = new MultiGame();
            this.inputChecker = new InputChecker(this, connectedClients, game);

            for(String name : players) {
                game.addPlayer(new Player(false, name, 0, new PlayerBoard(new WareHouse(), new StrongBox())));
            }

            game.startGame();

            Gson gson = new Gson();

            sendUpdateMarketDev();
            sendUpdateFaithTrack();
            sendUpdateMarketTray();

            ArrayList<String> nickNames = new ArrayList<>();
            ArrayList<Player> players = game.getPlayers();
            for (Player player : players) {
                Server.LOGGER.info("giving cards to player " + player.getNickName());
                VirtualView vv = getVirtualView(player.getNickName());
                nickNames.add(player.getNickName());
                ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
                for (LeaderCard leaderCard : player.getLeadercards()) {
                    dummyLeaderCards.add(leaderCard.getDummy());
                }
                Message message = new Message(MessageType.DUMMY_LEADER_CARD,gson.toJson(dummyLeaderCards));
                vv.update(message);
            }
            turnController = new TurnController(this, nickNames, game.getCurrentPlayer().getNickName());
            setGamePhase(GamePhase.FIRST_ROUND);

            getVirtualViewByNickname(turnController.getActivePlayer()).update(new Message(MessageType.DISCARD_LEADER,"You are the first player, discard 2 leader cards from your hand"));
            sendAllExcept(new Message(MessageType.GENERIC_MESSAGE, "It's " + turnController.getActivePlayer() + "'s turn, wait for your turn!"), getVirtualViewByNickname(turnController.getActivePlayer()));
        } catch (JsonFileNotFoundException ex) {
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }







    @Override
    public void endGame() {
        ArrayList<Player> winners = game.getWinners();
        if(winners.size() > 1){
            for(Player p: winners){
                getVirtualViewByNickname(p.getNickName()).update(new Message(MessageType.WINNER,""));
                players.remove(p.getNickName());
            }
            for(String name: players){
                getVirtualViewByNickname(name).update(new Message(MessageType.LOSER, ""));
            }
        }else{
            getVirtualViewByNickname(winners.get(0).getNickName()).update(new Message(MessageType.WINNER,""));
            sendAllExcept(new Message(MessageType.LOSER, ""), getVirtualViewByNickname(winners.get(0).getNickName()));
        }
    }


}






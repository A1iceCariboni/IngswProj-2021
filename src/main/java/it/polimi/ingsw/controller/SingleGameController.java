package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.answer.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;

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
            this.singleGame = new SingleGame();
            this.inputChecker = new InputChecker(this, connectedClients, game);

            Gson gson = new Gson();

            ArrayList<String> nickNames = new ArrayList<>();

            for(String name : getConnectedClients().keySet()) {
                singleGame.addPlayer(new Player(false, name, 0, new PlayerBoard(new WareHouse(), new StrongBox())));
            }

            singleGame.startGame();


            Player player = singleGame.getPlayers().get(0);
            nickNames.add(player.getNickName());

            VirtualView vv = getVirtualView(player.getNickName());
                ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
                for(LeaderCard leaderCard: player.getLeadercards()){
                    dummyLeaderCards.add(leaderCard.getDummy());
                }

            sendUpdateMarketDev();
            sendUpdateMarketTray();
            sendUpdateFaithTrack();

            Message message = new Message(MessageType.DUMMY_LEADER_CARD,gson.toJson(dummyLeaderCards));
            vv.update(message);


            getVirtualViewByNickname(turnController.getActivePlayer()).update(new Message(MessageType.GENERIC_MESSAGE,"You are the first player, discard 2 leader cards from your hand"));
            getVirtualViewByNickname(turnController.getActivePlayer()).update(new Message(MessageType.NOTIFY_TURN,""));

            TurnController turnController = new TurnController(this, nickNames, singleGame.getCurrentPlayer().getNickName(), this.game);
            setGamePhase(GamePhase.FIRST_ROUND);

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


}

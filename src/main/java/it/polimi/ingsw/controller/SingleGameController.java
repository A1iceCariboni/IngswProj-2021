package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.LeaderCardMessage;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;

public class SingleGameController extends GameController{
        private SingleGame singleGame;

    public SingleGameController() {
    }


    @Override
    public void startGame() {
        try{
            Server.LOGGER.info("instantiating game");
            this.singleGame = new SingleGame();

            for(String name : getConnectedClients().keySet()) {
                singleGame.addPlayer(new Player(false, name, 0, new PlayerBoard(new WareHouse(), new StrongBox())));
            }

            singleGame.startGame();

            Player player = singleGame.getPlayers().get(0);
                VirtualView vv = getVirtualView(player.getNickName());
                ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
                for(LeaderCard leaderCard: player.getLeadercards()){
                    dummyLeaderCards.add(leaderCard.getDummy());
                }
                Gson gson = new Gson();
                Message message = new LeaderCardMessage(gson.toJson(dummyLeaderCards));
                vv.update(message);

        }catch (JsonFileNotFoundException ex){
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }

    @Override
    public void activateLeaderCard(String name, VirtualView virtualView,int id) throws NullCardException {
        int i = singleGame.getPlayers().indexOf(name);
        Player player = singleGame.getPlayers().get(i);
        LeaderCard leaderCard = player.getLeaderCardById(id);
        if (leaderCard.isActivableBy(player.getPlayerBoard())) {
            player.activateLeader(leaderCard);
            virtualView.update(new OkMessage("Card activated successfully!"));
        }else{
            virtualView.update(new ErrorMessage("You don't satisfy the requirements to activate this card"));
        }
    }


}
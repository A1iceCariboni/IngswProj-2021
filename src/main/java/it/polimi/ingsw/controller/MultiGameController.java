package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.LeaderCardMessage;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.messages.request.BuyDevelopmentCard;
import it.polimi.ingsw.messages.request.ResourcesForDevCard;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;

public class MultiGameController extends GameController {
    private MultiGame multiGame;


    /**
     * method start game create a new instance of multigame give 4 leadercard to each player and sends
     * them as dummy leader card to the client
     */
    @Override
    public void startGame() {
        try {
            Server.LOGGER.info("instantiating game");
            this.multiGame = new MultiGame();

            for(String name : getConnectedClients().keySet()) {
                multiGame.addPlayer(new Player(false, name, 0, new PlayerBoard(new WareHouse(), new StrongBox())));
            }

            multiGame.startGame();

            ArrayList<Player> players = multiGame.getPlayers();
            for(int i = 0; i < players.size(); i++){
                Server.LOGGER.info("giving cards to player "+players.get(i).getNickName());
                VirtualView vv = getVirtualView(players.get(i).getNickName());
                ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
                for(LeaderCard leaderCard: players.get(i).getLeadercards()){
                    dummyLeaderCards.add(leaderCard.getDummy());
                }
                Gson gson = new Gson();
                Message message = new LeaderCardMessage(gson.toJson(dummyLeaderCards));
                vv.update(message);
            }
        } catch (JsonFileNotFoundException ex) {
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }

    @Override
    public void activateLeaderCard(String name, VirtualView virtualView, int id) throws NullCardException {
        int i = multiGame.getPlayers().indexOf(name);
        Player player = multiGame.getPlayers().get(i);
        LeaderCard leaderCard = player.getLeaderCardById(id);
        if (leaderCard.isActivableBy(player.getPlayerBoard())) {
            player.activateLeader(leaderCard);
            virtualView.update(new OkMessage("Card activated successfully!"));
        }else{
            virtualView.update(new ErrorMessage("You don't satisfy the requirements to activate this card"));
        }
    }
}

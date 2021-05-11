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

            DummyDev[][] dummyDevs = new DummyDev[Constants.rows][Constants.cols];
            for(int r = 0; r < Constants.rows; r++){
                for(int c = 0; c < Constants.cols; c++){
                    dummyDevs[r][c] = singleGame.getDeckDevelopment()[r][c].getCard().getDummy();
                }
            }

            Message message2 = new DevelopmentMarketMessage(gson.toJson(dummyDevs));

            DummyMarket dummyMarket = singleGame.getMarketTray().getDummy();

            Message message3 = new MarketTrayMessage(gson.toJson(dummyMarket));
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

            Message message = new LeaderCardMessage(gson.toJson(dummyLeaderCards));
            vv.update(message);

            Message message1 = new FaithTrackMessage(gson.toJson(singleGame.getFaithTrack()));
            vv.update(message1);

            vv.update(message2);

            vv.update(message3);

            TurnController turnController = new TurnController(this, nickNames, singleGame.getCurrentPlayer().getNickName());
            setGamePhase(GamePhase.FIRST_ROUND);

        }catch (JsonFileNotFoundException ex){
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }

    @Override
    public void activateLeaderCard(int id) throws NullCardException {
        String name = singleGame.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
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

    @Override
    public void discardLeaderCards(int[] id) throws NullCardException {
        for (int j : id) {
            LeaderCard toDiscard = singleGame.getCurrentPlayer().getLeaderCardById(j);
            singleGame.getCurrentPlayer().discardLeader(toDiscard);
        }
    }






}

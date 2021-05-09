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
     * them as dummy leader card to the client it also sends all the grid of development card,
     * the market tray structure and the faithtrack, it also gives the initial resources to the players
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

            Gson gson = new Gson();

            DummyDev[][] dummyDevs = new DummyDev[Constants.rows][Constants.cols];
            for(int r = 0; r < Constants.rows; r++){
                for(int c = 0; c < Constants.cols; c++){
                    dummyDevs[r][c] = multiGame.getDeckDevelopment()[r][c].getCard().getDummy();
                }
            }

            Message message2 = new DevelopmentMarketMessage(gson.toJson(dummyDevs));

            DummyMarket dummyMarket = multiGame.getMarketTray().getDummy();

            Message message3 = new MarketTrayMessage(gson.toJson(dummyMarket));

            ArrayList<String> nickNames = new ArrayList<>();
            ArrayList<Player> players = multiGame.getPlayers();
            for(int i = 0; i < players.size(); i++){
                Server.LOGGER.info("giving cards to player "+players.get(i).getNickName());
                VirtualView vv = getVirtualView(players.get(i).getNickName());
                nickNames.add(players.get(i).getNickName());
                ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>();
                for(LeaderCard leaderCard: players.get(i).getLeadercards()){
                    dummyLeaderCards.add(leaderCard.getDummy());
                }

                Message message = new LeaderCardMessage(gson.toJson(dummyLeaderCards));
                vv.update(message);

                Message message1 = new FaithTrackMessage(gson.toJson(multiGame.getFaithTrack()));
                vv.update(message1);

                vv.update(message2);

                vv.update(message3);

            }

            TurnController turnController = new TurnController(this, nickNames, multiGame.getCurrentPlayer().getNickName());
            setGamePhase(GamePhase.FIRST_ROUND);

            getVirtualViewByNickname(turnController.getActivePlayer()).update(new Message(MessageType.DISCARD_LEADER,"You are the first player, discard 2 leader cards from your hand"));
        } catch (JsonFileNotFoundException ex) {
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }


    @Override
    public void activateLeaderCard(int id) throws NullCardException {
        String name = multiGame.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
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


    /**
     * discard the selected leader card, if it's the first round proceed to add resources to
     * the players as in the rules, if it's not the turn of the player continues
     * @param id array of ids of the leadercard to discard form the hand of the player
     * @throws NullCardException if the player has not the card but this really shouldn't happen
     */
    @Override
    public void discardLeaderCards(int[] id) throws NullCardException {
        String name = multiGame.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for(int i = 0; i < id.length; i++){
            LeaderCard toDiscard = multiGame.getCurrentPlayer().getLeaderCardById(id[i]);
            multiGame.getCurrentPlayer().discardLeader(toDiscard);
            if(getGamePhase() != GamePhase.FIRST_ROUND){
                multiGame.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
            }
        }
        if(getGamePhase() == GamePhase.FIRST_ROUND){
                virtualView.update(new OkMessage("Cards successfully discarded!"));
                switch(getPlayers().indexOf(getTurnController().getActivePlayer())){
                    case 0:
                        multiGame.nextPlayer();
                        getTurnController().nextTurn();
                    case 1:
                    case 2:
                        virtualView.update(new Message(MessageType.CHOOSE_RESOURCES, "Choose 1 resource to put in your warehouse"));
                        break;
                    case 3:
                        virtualView.update(new Message(MessageType.CHOOSE_RESOURCES, "Choose 2 resources to put in your warehouse"));
                        break;
                }
            }
        }
}






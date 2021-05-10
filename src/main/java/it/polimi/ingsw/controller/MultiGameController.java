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
import it.polimi.ingsw.messages.request.BuyDevelopmentCard;
import it.polimi.ingsw.messages.request.ResourcesForDevCard;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
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

            for(String name : players) {
                game.addPlayer(new Player(false, name, 0, new PlayerBoard(new WareHouse(), new StrongBox())));
            }

            game.startGame();

            Gson gson = new Gson();

            DummyDev[][] dummyDevs = new DummyDev[Constants.rows][Constants.cols];
            for(int r = 0; r < Constants.rows; r++){
                for(int c = 0; c < Constants.cols; c++){
                    dummyDevs[r][c] = game.getDeckDevelopment()[r][c].getCard().getDummy();
                }
            }

            Message message2 = new DevelopmentMarketMessage(gson.toJson(dummyDevs));

            DummyMarket dummyMarket = game.getMarketTray().getDummy();

            Message message3 = new MarketTrayMessage(gson.toJson(dummyMarket));

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

                Message message = new LeaderCardMessage(gson.toJson(dummyLeaderCards));
                vv.update(message);

                Message message1 = new FaithTrackMessage(gson.toJson(game.getFaithTrack()));
                vv.update(message1);

                vv.update(message2);

                vv.update(message3);

            }
            turnController = new TurnController(this, nickNames, game.getCurrentPlayer().getNickName());
            setGamePhase(GamePhase.FIRST_ROUND);

            getVirtualViewByNickname(turnController.getActivePlayer()).update(new Message(MessageType.DISCARD_LEADER,"You are the first player, discard 2 leader cards from your hand"));
        } catch (JsonFileNotFoundException ex) {
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }


    @Override
    public void activateLeaderCard(int id) throws NullCardException {
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        int i = game.getPlayers().indexOf(name);
        Player player = game.getPlayers().get(i);
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
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        for (int j : id) {
            LeaderCard toDiscard = game.getCurrentPlayer().getLeaderCardById(j);
            game.getCurrentPlayer().discardLeader(toDiscard);
            if (getGamePhase() != GamePhase.FIRST_ROUND) {
                game.getCurrentPlayer().getPlayerBoard().moveFaithMarker(1);
            }
        }
        if(getGamePhase() == GamePhase.FIRST_ROUND){
                virtualView.update(new OkMessage("Cards successfully discarded!"));
                switch(getPlayers().indexOf(turnController.getActivePlayer())){
                    case 0:
                        game.nextPlayer();
                        turnController.nextTurn();
                    case 1:
                    case 2:
                        virtualView.update(new Message(MessageType.CHOOSE_RESOURCES, "1"));
                        break;
                    case 3:
                        virtualView.update(new Message(MessageType.CHOOSE_RESOURCES, "2"));
                        break;
                }
            }
        }


    /**
     * if the player has some resources that has not be placed yet , he have to choose were to put them
     * before proceeding with the turn
     */
    public void placeResources(){
        Gson gson = new Gson();
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        while(!virtualView.getFreeResources().isEmpty()){
            Resource resource = virtualView.getFreeResources().get(0);
            if(gamePhase == GamePhase.FIRST_ROUND || turnController.getTurnPhase() == TurnPhase.BUY_MARKET) {
                virtualView.update(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(resource.getResourceType())));
            }else{
                virtualView.update(new Message(MessageType.PLACE_RESEOURCE_WHEREVER, gson.toJson(resource.getResourceType())));
            }
        }
    }

    /**
     * tries to put the resources in the required place in the warehouse or strong box
     * @param id of the depot or strongbox if it's 0
     */
    public void putResource(int id) throws NotPossibleToAdd {
        String name = game.getCurrentPlayer().getNickName();
        VirtualView virtualView = getConnectedClients().get(name);
        if (id == 0){
            game.getCurrentPlayer().getPlayerBoard().getStrongBox().addResources(virtualView.getFreeResources().get(0));
        }else{
            game.getCurrentPlayer().getDepotById(id).addResource(virtualView.getFreeResources().get(0));
        }

    }
}






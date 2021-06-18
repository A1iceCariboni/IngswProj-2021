package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.sun.prism.Image;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.effects.ExtraProductionPower;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.WarehouseConstructor;

import java.awt.*;
import java.awt.image.DataBufferInt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InputChecker implements Serializable {
    private static final long serialVersionUID = -9074030847073411987L;
    private final GameController gameController;


    private  Game game;


    public InputChecker(GameController gameController, Game game) {
        this.gameController = gameController;
        this.game = game;
    }

    public boolean checkReceivedMessage(String line, String nickname) {
        Gson gson = new Gson();
        Message message = gson.fromJson(line, Message.class);

        switch (message.getCode()) {
            case BUY_DEV:
                return !gameController.connectedClients.get(nickname).isGameActionDone();
            case RESOURCE_PAYMENT:
                ResourcePayment resourcePayment = gson.fromJson(line, ResourcePayment.class);

                return (checkPayment(resourcePayment.getIds(), nickname)) && ((gameController.getTurnPhase() == TurnPhase.BUY_DEV) || (gameController.getTurnPhase() == TurnPhase.ACTIVATE_PRODUCTION));
            case PLACE_RESOURCE_WAREHOUSE:
                PlaceResources placeResources = gson.fromJson(line, PlaceResources.class);
                int id[] = placeResources.getIds();
                for(int j = 0; j < id.length; j++){
                    if((id[j] != -1) && (!checkIdDepot(id[j])||(!canAddToDepot(game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(j), game.getCurrentPlayer().getDepotById(id[j]))))) return false;
                }
                return id.length == game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().size() ;
            case BUY_MARKET:
                BuyMarket buyMarket = gson.fromJson(line, BuyMarket.class);
                if (gameController.getVirtualView(nickname).isGameActionDone()) {
                    return false;
                }
                return ((buyMarket.getRoc().equalsIgnoreCase("row")) || (buyMarket.getRoc().equalsIgnoreCase("col")));
            case WHITE_MARBLES:
                WhiteMarblesChoice whiteMarblesChoice = gson.fromJson(line, WhiteMarblesChoice.class);
                return (!gameController.getVirtualView(nickname).getFreeMarble().isEmpty()) && (whiteMarblesChoice.getPowers().length == gameController.getVirtualView(nickname).getFreeMarble().size()) && ((gameController.getTurnPhase() == TurnPhase.BUY_MARKET) && (checkWhiteMarble(whiteMarblesChoice.getPowers())));
            case SLOT_CHOICE:
                return gameController.getTurnPhase() == TurnPhase.BUY_DEV;
            case ACTIVATE_PRODUCTION:
                ActivateDevProd activateDevProd = gson.fromJson(line, ActivateDevProd.class);
                return checkIdDev(activateDevProd.getIds()) && (checkResources(activateDevProd.getIds()));
            case EXTRA_PRODUCTION:
                ExtraProductionToActivate productionToActivate = gson.fromJson(line, ExtraProductionToActivate.class);
                ArrayList<Resource> resources = new ArrayList<>();
                resources.add(productionToActivate.getResource());
                return checkIdExtraProduction(new int[]{productionToActivate.getId()}) && (checkResources(resources));
            case BASE_PRODUCTION:
                ActivateBaseProd activateBaseProd = gson.fromJson(line, ActivateBaseProd.class);
                String[] command = activateBaseProd.getCommand();
                Resource res1 = new Resource(ResourceType.valueOf(command[0]));
                Resource res2 = new Resource(ResourceType.valueOf(command[1]));
                ArrayList<Resource> resources1 = new ArrayList<>();
                resources1.add(res1);
                resources1.add(res2);
                return checkResources(resources1);

            case ACTIVATE_LEADER_CARD:
                ActivateLeader activateLeader = gson.fromJson(line,ActivateLeader.class);
                return checkLeaderCard(activateLeader.getId());
            case DISCARD_LEADER:
                DiscardLeader discardLeader = gson.fromJson(line, DiscardLeader.class);
                return checkOwnedLeaderCard(discardLeader.getIds());
            case DEPOTS:
                DepotMessage depotMessage = gson.fromJson(line, DepotMessage.class);
                return checkDepotsState(nickname, depotMessage.getWareHouse()) && validDepots(depotMessage.getWareHouse());
            case REMOVE_RESOURCES:
                RemoveResource resource = gson.fromJson(line, RemoveResource.class);
                return checkIdDepot(resource.getId());
            case SEE_PLAYERBOARD:
                if(gameController.getNumberOfPlayers() == 1) return true;
                SeePlayerBoard seePlayerBoard = gson.fromJson(line, SeePlayerBoard.class);
                return checkNickname(seePlayerBoard.getNickname());
            default:
                return true;
        }
    }

    private boolean checkNickname(String nickname) {
        for(Player p : game.getPlayers()){
            if(p.getNickName().equals(nickname)) return true;
        }
        return false;
    }

    private boolean canAddToDepot(Resource res, Depot d) {
        return game.getCurrentPlayer().getPlayerBoard().getWareHouse().canAddToDepot(res, d);
    }

    private boolean checkResources( ArrayList<Resource> resource) {
        ArrayList<Resource> price = new ArrayList<>();
        price.addAll(gameController.getVirtualView(game.getCurrentPlayer().getNickName()).getResourcesToPay());
        price.addAll(resource);
        if(game.getCurrentPlayer().getPlayerBoard().getResources().isEmpty())return false;
        ArrayList<Resource> wallet = new ArrayList<>();
        wallet.addAll(game.getCurrentPlayer().getPlayerBoard().getResources());

        while(!wallet.isEmpty() && !price.isEmpty() && wallet.contains(price.get(0))){
            wallet.remove(price.get(0));
            price.remove(0);
        }
        return price.isEmpty();
    }

    /**
     * checks if the player has the resources he wants to pay with and if they are the same quantity
     * required by the cost of the card
     *
     * @param ids      id of the depot he wants to take the resources, -1 if it's the strongbox
     * @param nickname nickname of the player
     * @return true if the payment can be done, false otherwise
     */
    public boolean checkPayment(int[] ids, String nickname) {
        List<Integer> depotIds = Arrays.stream(ids).boxed().collect(Collectors.toList());
        ArrayList<Resource> cost;
        if (gameController.getTurnPhase() == TurnPhase.BUY_DEV) {
            cost = game.getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment().getCost();
        } else {
            cost = gameController.getVirtualView(nickname).getResourcesToPay();
        }
        for(int j = 0; j < ids.length; j++){
            if(ids[j]!=-1){
                if(!game.getCurrentPlayer().getDepotById(ids[j]).getDepot().isEmpty() && game.getCurrentPlayer().getDepotById(ids[j]).getDepot().get(0).getResourceType() != cost.get(j).getResourceType()){
                    return false;
                }
            }
        }
        ArrayList<Resource> payment = cost;
        Map<Integer, Long> couterMap = depotIds.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        for (int j : couterMap.keySet()) {
            if (j != -1) {
                if(!checkIdDepot(j))return false;
                if (game.getCurrentPlayer().getDepotById(j).getDepot().size() < couterMap.get(j)) {
                    return false;
                } else {
                    for (int i = 0; i < couterMap.get(j); i++) {
                        payment.remove(game.getCurrentPlayer().getDepotById(j).getDepot().get(0));
                    }
                }
            }
        }
        ArrayList<Resource> strongBox = game.getCurrentPlayer().getPlayerBoard().getStrongBox().getRes();
        Map<ResourceType, Long> paymentMap = payment.stream().collect(Collectors.groupingBy(Resource::getResourceType, Collectors.counting()));
        Map<ResourceType, Long> strongBoxMap = strongBox.stream().collect(Collectors.groupingBy(Resource::getResourceType, Collectors.counting()));
        for (ResourceType resourceType : paymentMap.keySet()) {
            if ((strongBoxMap.get(resourceType) == null) || (strongBoxMap.get(resourceType) < paymentMap.get(resourceType))) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if the new configuration of the depots is a valid one
     *
     * @return true if it is valid false otherwise
     */
    public boolean checkDepotsState(String nickname, Depot[] depots) {
        ArrayList<Resource> newPlacement = new ArrayList<>();
        for (Depot depot : depots) {
            if(!checkIdDepot(depot.getId()))return false;
            newPlacement.addAll(depot.getDepot());
        }
        ArrayList<Resource> intersection = new ArrayList<>();
        intersection.addAll(newPlacement);
        intersection.removeAll(game.getCurrentPlayer().getPlayerBoard().getWareHouse().getResources());
        if (intersection.isEmpty()) {
            intersection.addAll(game.getCurrentPlayer().getPlayerBoard().getWareHouse().getResources());
            intersection.removeAll(newPlacement);
            return intersection.isEmpty();
        } else {
            return false;
        }
    }

    /**
     * checks if the given depots are valid  in terms of dimension and resource type
     *
     * @param depot depot to be checked
     * @return true if it is valid, false otherwise
     */
    public boolean validDepots( Depot[] depot) {
        WareHouse wareHouse = new WareHouse();
        for(Depot d: depot) {
            for (Resource r : d.getDepot()) {
                try{
                    wareHouse.addToDepot(r,d);
                }catch(NotPossibleToAdd ex){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * checks if the card is owned by the player
     * and if he has already choosen this power
     * @param cards ids of the required cards
     * @return true if he owns them
     */
    public boolean checkIdDev(int[] cards) {
        ArrayList<Integer> owned = new ArrayList<>();
        for (DevelopmentCard developmentCard : game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards()) {
            owned.add(developmentCard.getId());
        }
        for (int j : cards) {
            if (!owned.contains(j)) {
                return false;
            }
            if(gameController.getVirtualView(game.getCurrentPlayer().getNickName()).getCardsToActivate().contains(j)){
                return false;
            }
        }
        return true;
    }

    /**
     * checks if the player owns the given depot
     * @param id id of the depot
     * @return true if he owns it
     */
    public boolean checkIdDepot(int id){
        if(id == -1)return true;
        return game.getCurrentPlayer().getPlayerBoard().getWareHouse().getIds().contains(id);
    }


    /**
     * checks if the extra production power is owned by the player
     * and if he hasn't already choosen this power
     * @param powers ids of the production powers
     * @return true if he owns them
     */
    public boolean checkIdExtraProduction(int[] powers) {
        for (int j : powers) {
            try {
                if ((!(game.getCurrentPlayer().getLeaderCardById(j).getLeaderEffect() instanceof ExtraProductionPower))||(!game.getCurrentPlayer().getLeaderCardById(j).isActive())) {
                     return false;
                }else{
                    ExtraProductionPower extraProductionPower = (ExtraProductionPower) game.getCurrentPlayer().getLeaderCardById(j).getLeaderEffect();
                    if(gameController.getVirtualView(game.getCurrentPlayer().getNickName()).getExtraProductionToActivate().contains(extraProductionPower.getId())){
                        return false;
                    }
                }
            } catch (NullCardException e) {
                return false;
            }

        }
        return true;
    }

    /**
     * checks if the leadercard is owned by the player and if it's not active
     *
     * @param id of the leader card
     * @return true if it is owned
     */
    public boolean checkLeaderCard(int id) {
        LeaderCard leaderCard = null;
        try {
            leaderCard = game.getCurrentPlayer().getLeaderCardById(id);
            return !leaderCard.isActive();
        } catch (NullCardException e) {
            return false;
        }
    }

    /**
     * checks if the player really owns the white marbles
     * powers he wants to activate
     *
     * @param marb the powers the player wants to use
     * @return true id he owns them
     */
    public boolean checkWhiteMarble(String[] marb) {
        for (String m : marb) {
            if (!game.getCurrentPlayer().getPossibleWhiteMarbles().contains(new Resource(ResourceType.valueOf(m))))
                return false;
        }
        return true;
    }

    /**
     * checks if the player owns the leadercads with the given ids
     * @param ca the ids of the card
     * @return true if he owns the cards false otherwise
     */
    public boolean checkOwnedLeaderCard(int ca) {
        try {
            game.getCurrentPlayer().getLeaderCardById(ca);
            return true;
        } catch (NullCardException e) {
            return false;
        }
    }

    public boolean checkResources(int[] cards){
        ArrayList<Resource> price = gameController.getVirtualView(game.getCurrentPlayer().getNickName()).getResourcesToPay();
        for(int id: cards){
            for(DevelopmentCard developmentCard: game.getCurrentPlayer().getPlayerBoard().getDevelopmentCards()){
                if(developmentCard.getId() == id){
                    price.addAll(developmentCard.getProductionPower().getEntryResources());
                }
            }
        }
        if(game.getCurrentPlayer().getPlayerBoard().getResources().isEmpty())return false;
        ArrayList<Resource> wallet = game.getCurrentPlayer().getPlayerBoard().getResources();

        while(!wallet.isEmpty() && !price.isEmpty() && wallet.contains(price.get(0))){
            wallet.remove(price.get(0));
            price.remove(0);
        }
        return price.isEmpty();

    }

    public void setGame(final Game game) {
        this.game = game;
    }

}

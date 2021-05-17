package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.ExtraProduction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InputChecker {
    private GameController gameController;
    private Map<String, VirtualView> connectedClients;
    private Game game;


    public InputChecker(GameController gameController, Map<String, VirtualView> connectedClients, Game game) {
        this.gameController = gameController;
        this.connectedClients = connectedClients;
        this.game = game;
    }

    public boolean checkReceivedMessage(Message message, String nickname) {
        Gson gson = new Gson();
        switch (message.getCode()) {
            case BUY_DEV:
                if (gameController.getTurnController().isGameActionDone()) {
                    connectedClients.get(nickname).sendInvalidActionMessage();
                    return false;
                }
                return true;
            case RESOURCE_PAYMENT:
                Integer[] ids = gson.fromJson(message.getPayload(), Integer[].class);

                if ((checkPayment(ids, nickname)) && ((gameController.getTurnPhase() == TurnPhase.BUY_DEV) || (gameController.getTurnPhase() == TurnPhase.ACTIVATE_PRODUCTION))) {
                    return true;
                }
                return false;
            case PLACE_RESOURCE_WAREHOUSE:
            case PLACE_RESOURCE_WHEREVER:
                int[] id = gson.fromJson(message.getPayload(), int[].class);
                if (id.length == gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().size()) {
                    return true;
                }
                return false;
            case BUY_MARKET:
                if (gameController.getTurnController().isGameActionDone()) {
                    connectedClients.get(nickname).sendInvalidActionMessage();
                    return false;
                }
                String[] dim = gson.fromJson(message.getPayload(), String[].class);
                if ((dim.length != 2) || ((!dim[0].equalsIgnoreCase("row")) && (!dim[0].equalsIgnoreCase("col")))) {
                    return false;
                }
                return true;
            case WHITE_MARBLES:
                String[] marb = gson.fromJson(message.getPayload(), String[].class);
                if ((connectedClients.get(nickname).getFreeMarble().isEmpty()) || (marb.length < connectedClients.get(nickname).getFreeMarble().size()) || (gameController.getTurnPhase() != TurnPhase.BUY_MARKET) && (!checkWhiteMarble(marb))) {
                    return false;
                }
                return true;
            case SLOT_CHOICE:
                if (gameController.getTurnPhase() == TurnPhase.BUY_DEV) {
                    return true;
                }
                return false;
            case ACTIVATE_PRODUCTION:
                int[] cards = gson.fromJson(message.getPayload(), int[].class);
                if (checkIdDev(cards)) return true;
                return false;
            case EXTRA_PRODUCTION:
                int[] powers = gson.fromJson(message.getPayload(), int[].class);
                if (checkIdExtraProduction(powers)) return true;
                return false;
            case ACTIVATE_LEADER_CARD:
                int l = gson.fromJson(message.getPayload(), int.class);
                if (checkLeaderCard(l)) return true;
                return false;
            case DISCARD_LEADER:
                int ca = gson.fromJson(message.getPayload(), int.class);
                if (checkOwnedLeaderCard(ca)) return true;
                return false;
            case DEPOTS:
                return checkDepotsState(nickname);
            case REMOVE_RESOURCES:
                l = gson.fromJson(message.getPayload(), int.class);
                return checkIdDepot(l);
            default:
                return true;
        }
    }

    /**
     * checks if the player has the resources he wants to pay with and if they are the same quantity
     * required by the cost of the card
     *
     * @param ids      id of the depot he wants to take the resources, -1 if it's the strongbox
     * @param nickname nickname of the player
     * @return true if the payment can be done, false otherwise
     */
    public boolean checkPayment(Integer[] ids, String nickname) {
        List<Integer> depotIds = Arrays.asList(ids);
        ArrayList<Resource> cost = new ArrayList<>();
        if (gameController.getTurnPhase() == TurnPhase.BUY_DEV) {
            cost = connectedClients.get(nickname).getFreeDevelopment().get(0).getCost();
        } else {
            cost = connectedClients.get(nickname).getResourcesToPay();
        }
        ArrayList<Resource> payment = cost;
        Map<Integer, Long> couterMap = depotIds.stream().collect(Collectors.groupingBy(p -> p.intValue(), Collectors.counting()));
        for (int j : couterMap.keySet()) {
            if (j != -1) {
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
        Map<ResourceType, Long> paymentMap = payment.stream().collect(Collectors.groupingBy(p -> p.getResourceType(), Collectors.counting()));
        Map<ResourceType, Long> strongBoxMap = strongBox.stream().collect(Collectors.groupingBy(p -> p.getResourceType(), Collectors.counting()));
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
    public boolean checkDepotsState(String nickname) {
        ArrayList<Resource> newPlacement = new ArrayList<>();
        for (Depot depot : gameController.getVirtualView(nickname).getTempDepots()) {
            if(!checkIdDepot(depot.getId()))return false;
            newPlacement.addAll(depot.getDepot());
        }
        ArrayList<Resource> intersection;
        intersection = newPlacement;
        intersection.removeAll(game.getCurrentPlayer().getPlayerBoard().getWareHouse().getWarehouse());
        if (intersection.isEmpty()) {
            intersection = game.getCurrentPlayer().getPlayerBoard().getWareHouse().getWarehouse();
            intersection.removeAll(newPlacement);
            if (intersection.isEmpty()) {
                for (Depot depot : gameController.getVirtualView(nickname).getTempDepots()) {
                    if (!validDepot(depot)) {
                        emptyStorage(nickname);
                        return false;
                    }
                }
            } else {
                emptyStorage(nickname);
                return false;
            }
        } else {
            emptyStorage(nickname);
            return false;
        }
        return true;
    }

    /**
     * checks if the given depot is a valid depot in terms of dimension and resource type
     *
     * @param depot
     * @return
     */
    public boolean validDepot(Depot depot) {
        Depot d = game.getCurrentPlayer().getDepotById(depot.getId());
        for (Resource r : depot.getDepot()) {
            if (!d.possibleToAdd(r)) {
                return false;
            }
        }
        if (depot.getDepot().size() > d.getDimension()) {
            return false;
        }
        return true;
    }

    /**
     * empty the storage in case of invalid move_resources request
     */
    public void emptyStorage(String nickname) {
        gameController.getVirtualView(nickname).freeTempDepots();
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
        if((id == 1)||(id == 2)||(id == 3)) return true;
        for(int j = 0 ; j < game.getCurrentPlayer().getPlayerBoard().getExtraDepots().size(); j ++){
            if(game.getCurrentPlayer().getPlayerBoard().getExtraDepots().get(j).getId() == id){
                return true;
            }
        }
        return false;
    }


    /**
     * checks if the extra production power is owned by the player
     * and if he hasn't already choosen this power
     * @param powers ids of the production powers
     * @return true if he owns them
     */
    public boolean checkIdExtraProduction(int[] powers) {
        ArrayList<Integer> owned = new ArrayList<>();
        for (ExtraProduction extraProduction : game.getCurrentPlayer().getExtraProductionPowers()) {
            owned.add(extraProduction.getId());
        }
        for (int j : powers) {
            if (!owned.contains(j)) {
                return false;
            }
            if(gameController.getVirtualView(game.getCurrentPlayer().getNickName()).getExtraProductionToActivate().contains(j)){
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
        LeaderCard leaderCard = game.getCurrentPlayer().getLeaderCardById(id);
        if (leaderCard == null || leaderCard.isActive()) {
            return false;
        }
        return true;
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
            LeaderCard leaderCard = game.getCurrentPlayer().getLeaderCardById(ca);
            if (leaderCard == null) {
                return false;
            }
        return true;
    }
}

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
    private final GameController gameController;
    private final Map<String, VirtualView> connectedClients;
    private final Game game;


    public InputChecker(GameController gameController, Map<String, VirtualView> connectedClients, Game game) {
        this.gameController = gameController;
        this.connectedClients = connectedClients;
        this.game = game;
    }

    public boolean checkReceivedMessage(Message message, String nickname) {
        Gson gson = new Gson();
        switch (message.getCode()) {
            case BUY_DEV:
                return !gameController.getTurnController().isGameActionDone();
            case RESOURCE_PAYMENT:
                Integer[] ids = gson.fromJson(message.getPayload(), Integer[].class);

                return (checkPayment(ids, nickname)) && ((gameController.getTurnPhase() == TurnPhase.BUY_DEV) || (gameController.getTurnPhase() == TurnPhase.ACTIVATE_PRODUCTION));
            case PLACE_RESOURCE_WAREHOUSE:
                int[] id = gson.fromJson(message.getPayload(), int[].class);
                for(int j = 0; j < id.length; j++){
                    if((id[j] != -1) && (!checkIdDepot(id[j])||(!canAddToDepot(game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(j), game.getCurrentPlayer().getDepotById(id[j]))))) return false;
                }
                return id.length == game.getCurrentPlayer().getPlayerBoard().getUnplacedResources().size() ;
            case BUY_MARKET:
                if (gameController.getTurnController().isGameActionDone()) {
                    return false;
                }
                String[] dim = gson.fromJson(message.getPayload(), String[].class);
                return (dim.length == 2) && ((dim[0].equalsIgnoreCase("row")) || (dim[0].equalsIgnoreCase("col")));
            case WHITE_MARBLES:
                String[] marb = gson.fromJson(message.getPayload(), String[].class);
                return (!connectedClients.get(nickname).getFreeMarble().isEmpty()) && (marb.length >= connectedClients.get(nickname).getFreeMarble().size()) && ((gameController.getTurnPhase() == TurnPhase.BUY_MARKET) || (checkWhiteMarble(marb)));
            case SLOT_CHOICE:
                return gameController.getTurnPhase() == TurnPhase.BUY_DEV;
            case ACTIVATE_PRODUCTION:
                int[] cards = gson.fromJson(message.getPayload(), int[].class);
                return checkIdDev(cards) && (checkResources(cards));
            case EXTRA_PRODUCTION:
                String[] command = gson.fromJson(message.getPayload(),String[].class);
                int id1 = Integer.parseInt(command[0]);
                Resource resource = new Resource(ResourceType.valueOf(command[1]));
                ArrayList<Resource> resources = new ArrayList<>();
                resources.add(resource);
                return checkIdExtraProduction(new int[]{id1}) && (checkResources(resources));
            case BASE_PRODUCTION:
                command = gson.fromJson(message.getPayload(),String[].class);
                Resource res1 = new Resource(ResourceType.valueOf(command[0]));
                Resource res2 = new Resource(ResourceType.valueOf(command[1]));
                ArrayList<Resource> resources1 = new ArrayList<>();
                resources1.add(res1);
                resources1.add(res2);
                return checkResources(resources1);

            case ACTIVATE_LEADER_CARD:
                int l = gson.fromJson(message.getPayload(), int.class);
                return checkLeaderCard(l);
            case DISCARD_LEADER:
                int ca = gson.fromJson(message.getPayload(), int.class);
                return checkOwnedLeaderCard(ca);
            case DEPOTS:
                return checkDepotsState(nickname);
            case REMOVE_RESOURCES:
                l = gson.fromJson(message.getPayload(), int.class);
                return checkIdDepot(l);
            default:
                return true;
        }
    }

    private boolean canAddToDepot(Resource res, Depot d) {
        return game.getCurrentPlayer().getPlayerBoard().getWareHouse().canAddToDepot(res, d);
    }

    private boolean checkResources(ArrayList<Resource> resource) {
        ArrayList<Resource> price = gameController.getVirtualView(game.getCurrentPlayer().getNickName()).getResourcesToPay();
        price.addAll(resource);
        if(game.getCurrentPlayer().getPlayerBoard().getResources().isEmpty())return false;
        ArrayList<Resource> wallet = game.getCurrentPlayer().getPlayerBoard().getResources();

        while(!wallet.isEmpty() && wallet.contains(price.get(0))){
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
    public boolean checkPayment(Integer[] ids, String nickname) {
        List<Integer> depotIds = Arrays.asList(ids);
        ArrayList<Resource> cost;
        if (gameController.getTurnPhase() == TurnPhase.BUY_DEV) {
            cost = connectedClients.get(nickname).getFreeDevelopment().get(0).getCost();
        } else {
            cost = connectedClients.get(nickname).getResourcesToPay();
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
     * @param depot depot to be checked
     * @return true if it is valid, false otherwise
     */
    public boolean validDepot(Depot depot) {
        Depot d = game.getCurrentPlayer().getDepotById(depot.getId());
        for (Resource r : depot.getDepot()) {
            if (!d.possibleToAdd(r)) {
                return false;
            }
        }
        return depot.getDepot().size() <= d.getDimension();
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
        return game.getCurrentPlayer().getPlayerBoard().getWareHouse().getIds().contains(id);
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
        return leaderCard != null && !leaderCard.isActive();
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
        return leaderCard != null;
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

        while(!wallet.isEmpty() && wallet.contains(price.get(0))){
            wallet.remove(price.get(0));
            price.remove(0);
        }
        return price.isEmpty();

    }
}

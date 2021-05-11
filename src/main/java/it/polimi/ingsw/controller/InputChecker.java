package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.VirtualView;

import java.lang.reflect.Array;
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

    public boolean checkReceivedMessage(Message message, String nickname){
        Gson gson = new Gson();
        switch(message.getCode()){
            case BUY_DEV:
                if(gameController.getTurnController().isGameActionDone()){
                    connectedClients.get(nickname).sendInvalidActionMessage();
                    return false;
                }
                return true;
            case RESOURCE_PAYMENT:
                Integer[] ids = gson.fromJson(message.getPayload(), Integer[].class);

                if(checkPayment(ids,nickname)){
                    return true;
                }
                return false;
            case PLACE_RESOURCE_WAREHOUSE:
            case PLACE_RESOURCE_WHEREVER:
                int[] id = gson.fromJson(message.getPayload(), int[].class);
                if(id.length == connectedClients.get(nickname).getFreeResources().size()){
                    return true;
                }
                return false;
        }
        return false;
    }

    /**
     * checks if the player has the resources he wants to pay with and if they are the same quantity
     * required by the cost of the card
     * @param ids id of the depot he wants to take the resources, -1 if it's the strongbox
     * @param nickname nickname of the player
     * @return true if the payment can be done, false otherwise
     */
    public boolean checkPayment(Integer[] ids, String nickname){
        List<Integer> depotIds =Arrays.asList(ids);
        ArrayList<Resource> cost = connectedClients.get(nickname).getFreeDevelopment().get(0).getCost();
        ArrayList<Resource> payment = cost;
        Map<Integer, Long> couterMap = depotIds.stream().collect(Collectors.groupingBy(p-> p.intValue(),Collectors.counting()));
        for (int j : couterMap.keySet()) {
            if(j != - 1){
                if(game.getCurrentPlayer().getDepotById(j).getDepot().size() < couterMap.get(j)){
                    return false;
                }else{
                    for(int i = 0; i < couterMap.get(j); i++){
                        payment.remove(game.getCurrentPlayer().getDepotById(j).getDepot().get(0));
                    }
                }
            }
        }
        ArrayList<Resource> strongBox = game.getCurrentPlayer().getPlayerBoard().getStrongBox().getRes();
        Map<ResourceType, Long> paymentMap = payment.stream().collect(Collectors.groupingBy(p -> p.getResourceType(),Collectors.counting()));
        Map<ResourceType, Long> strongBoxMap = strongBox.stream().collect(Collectors.groupingBy(p -> p.getResourceType(),Collectors.counting()));
        for(ResourceType resourceType : paymentMap.keySet()){
            if ((strongBoxMap.get(resourceType) == null) || (strongBoxMap.get(resourceType) < paymentMap.get(resourceType))) {
                return false;
            }
        }
        return true;
    }
}

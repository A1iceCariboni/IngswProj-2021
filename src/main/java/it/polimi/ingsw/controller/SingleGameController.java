package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.messages.request.BuyDevelopmentCard;
import it.polimi.ingsw.messages.request.ResourcesForDevCard;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SingleGame;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;

public class SingleGameController extends GameController{
        private SingleGame singleGame;


    @Override
    public void startGame() {
        try{
            this.singleGame = new SingleGame();
        }catch (JsonFileNotFoundException ex){
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }

    @Override
    public void onMessageReceived(Message message, String nickname) throws NullCardException {
        VirtualView virtualView = getVirtualViewByNickname(nickname);
        switch(message.getCode()){
            case ACTIVATE_LEADER_CARD:
                LeaderCard leaderCard = singleGame.getPlayer().getLeaderCardById(Integer.parseInt(message.getPayload()));
                if(leaderCard.isActivableBy(singleGame.getPlayer().getPlayerBoard())){
                    singleGame.getPlayer().activateLeader(leaderCard);
                    virtualView.update(new OkMessage("Ok, leader card activated"));
                }else{
                    virtualView.update(new ErrorMessage("You don't satisfy requirements to activate"));
                }
            case DISCARD_LEADER_CARD:
                LeaderCard leaderCardD = singleGame.getPlayer().getLeaderCardById(Integer.parseInt(message.getPayload()));
                singleGame.getPlayer().discardLeader(leaderCardD);
                virtualView.update(new OkMessage("Ok, leader card eliminated"));
            case BUY_DEVELOPMENT_CARD:
                int c = ((BuyDevelopmentCard) message).getCol();
                int r = ((BuyDevelopmentCard) message).getRow();
                if(singleGame.getCardFrom(c,r) == ((BuyDevelopmentCard) message).getDevelopmentCard()){
                    virtualView.update(new OkMessage("Ok, the card can be bought"));
                }
                else virtualView.update(new ErrorMessage( "The card selected can't be bought or it is not in the selected place"));
            case RESOURCES_FOR_DEV_CARD:
                ArrayList<Resource> resources = ((ResourcesForDevCard) message).getResources();
                ArrayList<Resource> resources1 = singleGame.getPlayer().getPlayerBoard().getResources();
                int j = 1;
                for (Resource res: resources) {
                    if(resources1.indexOf(res) != -1){
                        int i = resources1.indexOf(res);
                        resources1.remove(res);
                    }
                    else j = 0;
                }
                if(j == 1){
                    virtualView.update(new OkMessage( "Ok, you have the rights requirements to buy the card"));
                }
                else virtualView.update(new ErrorMessage( "You don't have the rights resources to can buy the card"));

        }
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.messages.answer.OkMessage;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.SingleGame;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.VirtualView;

public class MultiGameController extends GameController{
    private MultiGame multiGame;

    @Override
    public void startGame() {
        try{
          this.multiGame = new MultiGame();
        }catch (JsonFileNotFoundException ex){
            sendAll(new ErrorMessage("I've had trouble instantiating the game, sorry..."));
        }
    }

    @Override
    public void onMessageReceived(Message message, String nickname) {
        VirtualView virtualView = getVirtualViewByNickname(nickname);
        switch(message.getCode()){
            case ACTIVATE_LEADER_CARD:
                int pos = multiGame.getPlayers().indexOf(nickname);
                Player player = multiGame.getPlayers().get(pos); // vedere se si può fare più bello
                LeaderCard leaderCard = player.getLeaderCardById(Integer.parseInt(message.getPayload()));
                if(leaderCard.isActivableBy(player.getPlayerBoard())){
                    virtualView.update(new OkMessage("Ok, leader card activeted"));
                }else{
                    virtualView.update(new ErrorMessage("You don't satisfy requirements to activate"));
                }
        }
    }
}

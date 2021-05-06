package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SingleGame;

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
    public void onMessageReceived(Message message, String nickname) {

    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.answer.ErrorMessage;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.SingleGame;

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
}

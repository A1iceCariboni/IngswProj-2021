package it.polimi.ingsw.Scenes;

import com.google.gson.Gson;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.WhiteMarblesChoice;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class WhiteMarble extends ViewObservable {
    private int num;
    VirtualModel virtualModel;
    Gson gson;
    private String[] powers;


    @FXML
    ImageView stone, coin, servant, shield;

    public void pickStone(MouseEvent mouseEvent){
        for (int i = 0; i < num; i++) {
            System.out.println("Choose one of your active powers, type the resource you want from this white marble");
            String resource = "STONE";
            powers[i] = resource;
        }
        stone.setOpacity(0.5);
        Message message = new WhiteMarblesChoice(powers);
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void pickServant(MouseEvent mouseEvent){
        for (int i = 0; i < num; i++) {
            System.out.println("Choose one of your active powers, type the resource you want from this white marble");
            String resource = "SERVANT";
            powers[i] = resource;
        }
        servant.setOpacity(0.5);
        Message message = new WhiteMarblesChoice(powers);
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void pickShield(MouseEvent mouseEvent){
        for (int i = 0; i < num; i++) {
        System.out.println("Choose one of your active powers, type the resource you want from this white marble");
        String resource = "SHIELD";
        powers[i] = resource;
    }
        Message message = new WhiteMarblesChoice(powers);
        notifyObserver(obs -> obs.onReadyReply(message));
        shield.setOpacity(0.5);
    }

    public void pickCoin(MouseEvent mouseEvent) {
        for (int i = 0; i < num; i++) {
            System.out.println("Choose one of your active powers, type the resource you want from this white marble");
            String resource = "COIN";
            powers[i] = resource;
        }
        coin.setOpacity(0.5);
        Message message = new WhiteMarblesChoice(powers);
        notifyObserver(obs -> obs.onReadyReply(message));
    }


    public void setnumber(int num, VirtualModel virtualModel){
        this.virtualModel= virtualModel;
        powers = new String[num];

    }

}

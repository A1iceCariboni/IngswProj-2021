package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.WhiteMarblesChoice;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**@author Alessandra Atria*/
/** This class represents the controller for the scene where the player
 * chooses witch resources he wants from the white marbles*/
public class WhiteMarble extends ViewObservable {
    int i;
    int n;
    VirtualModel virtualModel;
    private String[] powers;


    @FXML
    ImageView stone, coin, servant, shield;

    public WhiteMarble() {
        this.virtualModel = new VirtualModel();
        i=0;

    }


    /**pick the selected resource*/
    public void pickStone(MouseEvent mouseEvent){
        String resource = "STONE";
        powers[i] = resource;
        i++;
        if(i==n) {
            Message message = new WhiteMarblesChoice(powers);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickServant(MouseEvent mouseEvent){
        System.out.println("Choose one of your active powers, type the resource you want from this white marble");
        String resource = "SERVANT";
        powers[i] = resource;
        i++;
        if(i==n) {
            Message message = new WhiteMarblesChoice(powers);
            notifyObserver(obs -> obs.onReadyReply(message));
        }

    }

    public void pickShield(MouseEvent mouseEvent){
        System.out.println("Choose one of your active powers, type the resource you want from this white marble");
        String resource = "SHIELD";
        powers[i] = resource;
        i++;
        if(i==n) {
            Message message = new WhiteMarblesChoice(powers);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickCoin(MouseEvent mouseEvent) {
        System.out.println("Choose one of your active powers, type the resource you want from this white marble");
        String resource = "COIN";
        powers[i] = resource;
        i++;
        if(i==n) {
            Message message = new WhiteMarblesChoice(powers);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

   /**@param  num is the number of white marbles*/
    public void setnumber(int num, VirtualModel virtualModel){
        this.virtualModel= virtualModel;
        powers = new String[num];
        n = num;

    }

}

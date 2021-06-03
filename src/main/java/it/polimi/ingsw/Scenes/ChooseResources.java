package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.enumerations.ResourceType.SERVANT;


public class ChooseResources extends ViewObservable {
    Gson gson = new Gson();
    public ImageView shield;
    public ImageView coin;
    public ImageView servant;
    public ImageView stone;
    private String[] chosenRes;
    private int i = 0;


    @FXML
    public void put1(ActionEvent actionEvent) {

    }

    @FXML
    public void put2(ActionEvent actionEvent) {

    }

    @FXML
    public void put3(ActionEvent actionEvent) {

    }

    @FXML
    public void put4(ActionEvent actionEvent) {

    }

    @FXML
    public void put5(ActionEvent actionEvent) {

    }


    public void put6(ActionEvent actionEvent){

    }


    public void exit(ActionEvent actionEvent) {
    }

    public void pickServant(MouseEvent mouseEvent) {
        chosenRes[i] = "SERVANT";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickCoin(MouseEvent mouseEvent) {
        chosenRes[i] = "COIN";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickShield(MouseEvent mouseEvent) {
        chosenRes[i] = "SHIELD";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickStone(MouseEvent mouseEvent) {
        chosenRes[i] = "STONE";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void setQuantity(int quantity){
        chosenRes = new String[quantity];
    }
}

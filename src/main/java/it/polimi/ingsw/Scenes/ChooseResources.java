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
    String[] chosenRes;

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
        String serv = "SERVANT";
        Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(serv));
        notifyObserver(obs -> obs.onReadyReply(message));

    }

    public void pickCoin(MouseEvent mouseEvent) {
        String c = "COIN";
        Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(c));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void pickShield(MouseEvent mouseEvent) {
        String sh = "SHIELD";
        Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(sh));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void pickStone(MouseEvent mouseEvent) {
        String st = "STONE";
        Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(st));
        notifyObserver(obs -> obs.onReadyReply(message));

    }


}

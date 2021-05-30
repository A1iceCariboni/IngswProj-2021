package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;




public class LobbyController extends ViewObservable {

@FXML
    public Label waiting;


    @FXML
    public void initialize(){
      waiting.setText("ciao");
    }
}

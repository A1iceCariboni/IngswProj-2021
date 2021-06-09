package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NamePlayerPlayerboard extends ViewObservable {

    @FXML
    public TextField namePlayer;

    @FXML
    public Button back;

    @FXML
    public Button okButton;

    private VirtualModel virtualModel;
    private String name;
    
    public void back(ActionEvent actionEvent) {
        Board bc = new Board();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel).setVirtualModel(virtualModel));
    }

    public void nameText(ActionEvent actionEvent) {
        name = namePlayer.getText();
    }

    public void setVirtualModel(VirtualModel virtualModel){
        this.virtualModel = virtualModel;
    }

    public void ok() {
        name = namePlayer.getText();
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.SEE_PLAYERBOARD, name)));
        OtherBoard other = new OtherBoard();
        Platform.runLater(() -> GUIRunnable.otherPlayerBoard(other, observers).setOtherBoard(virtualModel));
    }
}

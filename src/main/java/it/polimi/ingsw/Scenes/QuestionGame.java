package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.SeePlayerBoard;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;

public class QuestionGame extends ViewObservable {

    @FXML
    public Button back;

    @FXML
    public Button SingleBut;

    @FXML
    private Button MultiBut;

    private VirtualModel virtualModel;


    public void multi() {
        NamePlayerPlayerboard np = new NamePlayerPlayerboard();
        Platform.runLater(() -> GUIRunnable.namePlayerPlayerboard(np, observers).setVirtualModel(virtualModel));
    }

    public void single() {
        notifyObserver(obs -> obs.onReadyReply(new SeePlayerBoard("LorenzoIlMagnifico")));

    }

    public void back() {
        Board bc = new Board();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel).setVirtualModel(virtualModel));
    }

    public void setVirtualModel (VirtualModel virtualModel) {
        this.virtualModel = virtualModel;
    }
}

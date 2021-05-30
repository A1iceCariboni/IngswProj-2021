package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class BoardController extends ViewObservable {

    public Button LeaderActiveButton;
    private TurnPhase turnPhase;
    int num;



    @FXML
    Button DiscardButton;

    public BoardController() {

    }


    @FXML
    public void DiscardLeaderCards() {
        ChooseLeaderCardsController cl = new ChooseLeaderCardsController();
        cl.addAllObservers(observers);
        Platform.runLater(() -> GUIRunnable.discardLeaderCards(cl));

    }

    public void buyfromMarket(ActionEvent actionEvent) {
    }

    public void ActivateProducion(ActionEvent actionEvent) {
    }


    public void ActivateLeader(ActionEvent actionEvent) {
    }

    public void EndTurn(ActionEvent actionEvent) {
    }

    public void SeeOthers(ActionEvent actionEvent) {
    }

    public void discardRes(ActionEvent actionEvent) {
    }

    public void buyDev(ActionEvent actionEvent) {
    }
}

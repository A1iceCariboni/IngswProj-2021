package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChooseLeaderCardsController extends ViewObservable {

    @FXML
    public ImageView l1;
    @FXML
    public ImageView l2;
    @FXML
    public ImageView l3;
    @FXML
    public ImageView l4;

    private final Stage stage;
    private double xOffset;
    private double yOffset;
    private int count;

    @FXML
    private StackPane rootPane;
    @FXML
    private Button okBtn;


    /**
     * Default constructor.
     */
    public ChooseLeaderCardsController() {
        stage = new Stage();
        stage.initOwner(GUIRunnable.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        xOffset = 0;
        yOffset = 0;
        count = 0;
    }

    @FXML
    public void initialize() {
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onRootPaneMousePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onRootPaneMouseDragged);
        okBtn.setDisable(true);
    }

    /**
     * Handles the mouse pressed event preparing the coordinates for dragging the window.
     *
     * @param event the mouse pressed event.
     */
    private void onRootPaneMousePressed(MouseEvent event) {
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    /**
     * Handles the mouse dragged event by moving the window around the screen.
     *
     * @param event the mouse dragged event.
     */
    private void onRootPaneMouseDragged(MouseEvent event) {
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }


    public void display() {
        stage.showAndWait();
    }


    /**
     * Sets the scene.
     *
     * @param scene scene of the stage.
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public void pick1(MouseEvent event) {
        int id = 1;
        if (count < 2) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(id))));
            count++;
        }if(count==2){
            okBtn.setDisable(false);}
    }

    public void pick2(MouseEvent event) {

        int id = 2;
        if (count < 2) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(id))));
            count++;
        }if(count==2){
            okBtn.setDisable(false);
        }
    }


    public void pick3(MouseEvent event) {
        int id = 3;
        if (count < 2) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(id))));
            count++;
        }if(count==2){
        okBtn.setDisable(false);
        }
    }

    public void pick4(MouseEvent event) {
        int id = 4;
        if (count < 2) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(id))));
            count++;
        }if(count==2){
            okBtn.setDisable(false);
        }
    }

    public void Exit(ActionEvent actionEvent) {
        if (count == 2) {
            stage.close();


        }
    }



}




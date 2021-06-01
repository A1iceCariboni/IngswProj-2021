package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class Board extends ViewObservable {

    private VirtualModel virtualModel;
    public Button LeaderActiveButton;
    private ArrayList<DummyLeaderCard> leaderCards;
    int id1;
    int id2;
    int id3;
    int id4;

    @FXML
    public ImageView p0;
    @FXML
    public ImageView p1;
    @FXML
    public ImageView p2;
    @FXML
    public ImageView p3;
    @FXML
    public ImageView p4;
    @FXML
    public ImageView p5;
    @FXML
    public ImageView p6;
    @FXML
    public ImageView p7;
    @FXML
    public ImageView p8;
    @FXML
    public ImageView p9;
    @FXML
    public ImageView p10;
    @FXML
    public ImageView p11;
    @FXML
    public ImageView p12;
    @FXML
    public ImageView p13;
    @FXML
    public ImageView p14;
    @FXML
    public ImageView p15;
    @FXML
    public ImageView p16;
    @FXML
    public ImageView p17;
    @FXML
    public ImageView p18;
    @FXML
    public ImageView p19;
    @FXML
    public ImageView p20;
    @FXML
    public ImageView p21;
    @FXML
    public ImageView p22;
    @FXML
    public ImageView p23;
    @FXML
    public ImageView p24;

    @FXML
    public ImageView led2;

    @FXML
    Button DiscardButton;

    public Board() {
        this.leaderCards = new ArrayList<>();
    }


    @FXML
    public void initialize() {
        p0.setVisible(false);
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);
        p6.setVisible(false);
        p7.setVisible(false);
        p8.setVisible(false);
        p9.setVisible(false);
        p10.setVisible(false);
        p11.setVisible(false);
        p12.setVisible(false);
        p13.setVisible(false);
        p14.setVisible(false);
        p15.setVisible(false);
        p16.setVisible(false);
        p17.setVisible(false);
        p18.setVisible(false);
        p19.setVisible(false);
        p20.setVisible(false);
        p21.setVisible(false);
        p22.setVisible(false);
        p23.setVisible(false);
        p23.setVisible(false);
        p24.setVisible(false);

    }


    @FXML
    public void DiscardLeaderCards() {
        DiscarLeaderCards f = new DiscarLeaderCards();
        f.addAllObservers(observers);
        System.out.println(leaderCards.get(1).getId());
        Platform.runLater(() -> GUIRunnable.FirstScene(f).setLeaderCards(leaderCards));

    }


    @FXML
    public void buyfromMarket(ActionEvent actionEvent) {
    }


    @FXML
    public void ActivateProducion(ActionEvent actionEvent) {
    }

    @FXML
    public void ActivateLeader(ActionEvent actionEvent) {
    }

    @FXML
    public void EndTurn(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.END_TURN, "")));
    }

    @FXML
    public void SeeOthers(ActionEvent actionEvent) {
    }

    @FXML
    public void discardRes(ActionEvent actionEvent) {
    }

    @FXML
    public void buyDev(ActionEvent actionEvent) {
    }



    public void setLeaderCards(ArrayList<DummyLeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
        Image img1 = new Image(Board.class.getResourceAsStream("/CardsFront/led" + leaderCards.get(0).getId() + ".png"));
        led2.setImage(img1);
    }

}
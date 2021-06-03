package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
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
    public ArrayList<ImageView> faithMarker;


    @FXML
    public ImageView res1;

    @FXML
    public ImageView res2;

    @FXML
    public ImageView res3;
    @FXML
    public ImageView res4;
    @FXML
    public ImageView res5;
    @FXML
    public ImageView res6;
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
    public ImageView led1;
    @FXML
    public ImageView led2;

    @FXML
    Button DiscardButton;

    @FXML
    Button DevButton;

    public Board() {
        this.virtualModel = new VirtualModel();
        this.faithMarker = new ArrayList<>();
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

        faithMarker.add(p0);
        faithMarker.add(p1);
        faithMarker.add(p2);
        faithMarker.add(p3);
        faithMarker.add(p4);
        faithMarker.add(p5);
        faithMarker.add(p6);
        faithMarker.add(p7);
        faithMarker.add(p8);
        faithMarker.add(p9);
        faithMarker.add(p10);
        faithMarker.add(p11);
        faithMarker.add(p12);
        faithMarker.add(p13);
        faithMarker.add(p14);
        faithMarker.add(p15);
        faithMarker.add(p16);
        faithMarker.add(p17);
        faithMarker.add(p18);
        faithMarker.add(p19);
        faithMarker.add(p20);
        faithMarker.add(p21);
        faithMarker.add(p22);
        faithMarker.add(p23);
        faithMarker.add(p24);
    }


    @FXML
    public void updateFaithTrack() {
        for(int i=0; i<24; i++){
            faithMarker.get(i).setVisible(false);
        }
        int p = virtualModel.getPlayerBoard().getFaithMarker();
        faithMarker.get(p).setVisible(true);
    }

    @FXML
    public void DiscardLeaderCards() {
        DiscarLeaderCards f = new DiscarLeaderCards();
        Platform.runLater(() -> GUIRunnable.FirstScene(f, observers).setLeaderCards(virtualModel));
    }

    @FXML
    public void buyDev() {
        DevCards deck = new DevCards();
        Platform.runLater(() -> GUIRunnable.buyDevCard(deck, observers).setDevCards(virtualModel));
    }


    @FXML
    public void buyfromMarket(ActionEvent actionEvent) {
        Market market = new Market();
        Platform.runLater(() -> GUIRunnable.buyMarket(market, observers).setMarket(virtualModel));
    }


    @FXML
    public void ActivateProducion(ActionEvent actionEvent) {
    }

    @FXML
    public void ActivateLeader(ActionEvent actionEvent) {
        DiscarLeaderCards f = new DiscarLeaderCards();
        Platform.runLater(() -> GUIRunnable.FirstScene(f, observers).setLeaderCards(virtualModel));
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
        DiscardResource f = new DiscardResource();
        Platform.runLater(() -> GUIRunnable.discRes(f, observers));
    }




    public void setVirtualModel(VirtualModel virtualModel) {
        this.virtualModel = virtualModel;
        //to set the warehouse
        if(virtualModel.getSlot1()!= "") {
            Image i1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot1() + ".png"));
            res1.setImage(i1);
            res1.setOpacity(1);
        }
        if(virtualModel.getSlot2()!= "") {
            Image i2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot2() + ".png"));
            res2.setImage(i2);
            res2.setOpacity(1);
        }
        if(virtualModel.getSlot3()!= "") {
            Image i3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot3() + ".png"));
            res3.setImage(i3);
            res3.setOpacity(1);
        }
        if(virtualModel.getSlot4()!= "") {
            Image i4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot4() + ".png"));
            res4.setImage(i4);
            res4.setOpacity(1);
        }
        if(virtualModel.getSlot5()!= "") {
            Image i5 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot5() + ".png"));
            res5.setImage(i5);
            res5.setOpacity(1);
        }
        if(virtualModel.getSlot6()!= "") {
            Image i6 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot6() + ".png"));
            res6.setImage(i6);
            res6.setOpacity(1);
        }
        //to set active cards on the board
        if (virtualModel.get1ActiveLeaderCard() != 0) {
            Image aL1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.get1ActiveLeaderCard() + ".png"));
            led1.setImage(aL1);
        }
        if (virtualModel.get2ActiveLeaderCard() != 0) {
            Image aL2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.get2ActiveLeaderCard() + ".png"));
            led2.setImage(aL2);
        }
    }


}
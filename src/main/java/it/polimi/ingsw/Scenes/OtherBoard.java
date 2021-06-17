package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyDepot;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.DummyModel.DummyStrongbox;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OtherBoard extends ViewObservable {

    @FXML
    public ImageView res1, res2, res3, res4, res5, res6;

    @FXML
    public ImageView p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;

    @FXML
    public ImageView led1, led2;

    @FXML
    public ImageView devb1, devb2, devb3;

    @FXML
    public Label nStone, nShield, nCoin, nServant;

    @FXML
    public ImageView pope2, pope3, pope4;

    private VirtualModel virtualModel;
    private final ArrayList<ImageView> faithTrack;
    private final ArrayList<ImageView> devSections;
    private final ArrayList<ImageView> resStrongBox;
    private final ArrayList<ImageView> depot2;
    private final ArrayList<ImageView> depot3;


    public OtherBoard(){
        this.faithTrack = new ArrayList<>();
        this.devSections = new ArrayList<>();
        this.resStrongBox = new ArrayList<>();
        this.depot2 = new ArrayList<>();
        this.depot3 = new ArrayList<>();
    }

    @FXML
    public void initialize(){
        faithTrack.add(p0);
        faithTrack.add(p1);
        faithTrack.add(p2);
        faithTrack.add(p3);
        faithTrack.add(p4);
        faithTrack.add(p5);
        faithTrack.add(p6);
        faithTrack.add(p7);
        faithTrack.add(p8);
        faithTrack.add(p9);
        faithTrack.add(p10);
        faithTrack.add(p11);
        faithTrack.add(p12);
        faithTrack.add(p13);
        faithTrack.add(p14);
        faithTrack.add(p15);
        faithTrack.add(p16);
        faithTrack.add(p17);
        faithTrack.add(p18);
        faithTrack.add(p19);
        faithTrack.add(p20);
        faithTrack.add(p21);
        faithTrack.add(p22);
        faithTrack.add(p23);
        faithTrack.add(p24);

        for (int i=0; i<faithTrack.size(); i++){
            faithTrack.get(i).setVisible(false);
        }

        devSections.add(devb1);
        devSections.add(devb2);
        devSections.add(devb3);

        depot2.add(res2);
        depot2.add(res3);
        depot3.add(res4);
        depot3.add(res5);
        depot3.add(res6);
    }

    public void setOtherBoard(VirtualModel virtualModel){
        this.virtualModel = virtualModel;

        faithTrack.get(virtualModel.getOtherPlayer().getFaithMarker()).setVisible(true);

        DummyDev[] devCards = virtualModel.getOtherPlayer().getDevSections();
        if (devCards.length != 0) {
            for (int i = 0; i < devSections.size(); i++) {
                if (devCards[i] != null) {
                    Image image = new Image(getClass().getResourceAsStream("/CardsFront/devCard" + devCards[i].getId() + ".png"));
                    devSections.get(i).setImage(image);
                    devSections.get(i).setOpacity(1);
                }
            }
        }


        if (virtualModel.getOtherSlot1() != "") {
            Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getOtherSlot1()  + ".png")));
            res1.setImage(image1);
            res1.setOpacity(1);
        }
        if (virtualModel.getOtherSlot2() != "") {
            Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getOtherSlot2() + ".png")));
            depot2.get(0).setImage(image2);
            depot2.get(0).setOpacity(1);
        }
        if (virtualModel.getOtherSlot3() != "") {
            Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getOtherSlot3() + ".png")));
            depot2.get(1).setImage(image3);
            depot2.get(1).setOpacity(1);
        }
        if (virtualModel.getOtherSlot4() != "") {
            Image image4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getOtherSlot4() + ".png")));
            depot3.get(0).setImage(image4);
            depot3.get(0).setOpacity(1);
        }
        if (virtualModel.getOtherSlot5() != "") {
            Image image5 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getOtherSlot5() + ".png")));
            depot3.get(1).setImage(image5);
            depot3.get(1).setOpacity(1);
        }
        if (virtualModel.getOtherSlot6() != "") {
            Image image6 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getOtherSlot6() + ".png")));
            depot3.get(2).setImage(image6);
            depot3.get(2).setOpacity(1);
        }

        if (virtualModel.getOtherCards().size() == 1) {
            Image imageLed1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getOtherCards().get(0).getId() +".png")));
            led1.setImage(imageLed1);
            led1.setOpacity(1);
        }
        if (virtualModel.getOtherCards().size() == 2) {
            Image imageLed2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getOtherCards().get(1).getId()+ ".png")));
            led2.setImage(imageLed2);
            led2.setOpacity(1);
        }

        int coin =0;
        int stone =0;
        int servant=0;
        int shield=0;
        for (int i=0; i<virtualModel.getPlayerBoard().getStrongBox().getResources().size(); i++) {
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("COIN"))
                coin++;
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("STONE"))
                stone++;
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("SHIELD"))
                shield++;
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("SERVANT"))
                servant++;
        }
        nCoin.setText("x"+ coin);
        nStone.setText("x"+ stone);
        nShield.setText("x"+ shield);
        nServant.setText("x"+ servant);

    }


    public void back(ActionEvent actionEvent) {
        Board bc = new Board();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel).setVirtualModel(virtualModel));
    }
}

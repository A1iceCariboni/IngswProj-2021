package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.observers.ViewObservable;
import it.polimi.ingsw.observers.ViewObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


/** Lorenzo il Magnifico FaithTrack*/
public class LMfaithTrack extends ViewObservable {

    @FXML
    public ImageView p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;

    private VirtualModel virtualModel;
    private final ArrayList<ImageView> faithTrack;

    public LMfaithTrack() {
        this.faithTrack = new ArrayList<>();
    }

    @FXML
    public void initialize() {
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

        for (int i = 0; i < faithTrack.size(); i++) {
            faithTrack.get(i).setVisible(false);
        }
    }


    public void setBlackCross(VirtualModel virtualModel, int blackCross){
        this.virtualModel = virtualModel;

        this.faithTrack.get(blackCross).setVisible(true);
    }

    public void back(ActionEvent actionEvent) {
        Board bc = new Board();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel).setVirtualModel(virtualModel));
    }
}

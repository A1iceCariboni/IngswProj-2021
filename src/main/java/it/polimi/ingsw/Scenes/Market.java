package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Market extends ViewObservable {
        private VirtualModel virtualModel;




        public Market() {
            this.virtualModel = new VirtualModel();
        }


        @FXML
        public void initialize() {

        }


        public void setMarket(VirtualModel virtualModel) {
            this.virtualModel = virtualModel;

        }

    public void getrow1(ActionEvent actionEvent) {
    }

    public void getrow2(ActionEvent actionEvent) {
    }
    public void getrow3(ActionEvent actionEvent) {
    }

    public void getcol1(ActionEvent actionEvent) {
    }

    public void getcol2(ActionEvent actionEvent) {
    }
    public void getcol3(ActionEvent actionEvent) {
    }
    public void getcol4(ActionEvent actionEvent) {
    }


    public void exit(ActionEvent actionEvent) {
        Board bc = new Board();
        GUIRunnable.changetoStart(bc, observers);

    }
}

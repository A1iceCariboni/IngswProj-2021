package it.polimi.ingsw.Scenes;

import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
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

public class DiscardResource extends ViewObservable {
    public Button d1, d2,d3;
    Gson gson = new Gson();
    VirtualModel virtualModel;

    @FXML
    ImageView res1, res2, res3, res4, res5, res6;

    public void disc1(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.REMOVE_RESOURCES, gson.toJson(1))));
    }

    public void disc2(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.REMOVE_RESOURCES, gson.toJson(2))));
    }

    public void disc3(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.REMOVE_RESOURCES, gson.toJson(3))));
    }

    public void back(ActionEvent actionEvent) {
        Board bc = new Board();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel));
    }




    public void setRes(VirtualModel virtualModel){
        this.virtualModel = virtualModel;
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
    }
}

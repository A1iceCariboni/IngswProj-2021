package it.polimi.ingsw.Scenes;

import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.RemoveResource;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



/** @author Alessandra Atria */
public class DiscardResource extends ViewObservable {
    public Button d1, d2,d3, ex1 , ex2;
    VirtualModel virtualModel;

    @FXML
    ImageView res1, res2, res3, res4, res5, res6, led1, led2, e1, e2, e3, e4;

    public void disc1(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new RemoveResource(1)));
    }

    public void disc2(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new RemoveResource(2)));
    }

    public void disc3(ActionEvent actionEvent) { notifyObserver(obs -> obs.onReadyReply(new RemoveResource(3)));
    }

    public void discex1(ActionEvent actionEvent) {
        int id1 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId();
        notifyObserver(obs -> obs.onReadyReply(new RemoveResource(id1)));
    }

    public void discex2(ActionEvent actionEvent) {
        int id2 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId();
        notifyObserver(obs -> obs.onReadyReply(new RemoveResource(id2)));
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

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId() == -1) {
            ex1.setDisable(true);
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId() == -1) {
            ex2.setDisable(true);
        }

        String type = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResourceType();
        Image ex1 = null;
        Image i = null;
        switch (type) {
            case ("COIN"):
                ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/COIN.png")));
                i = new Image((getClass().getResourceAsStream("/CardsFront/led8.png")));
                break;
            case ("STONE"):
                ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/STONE.png")));
                i = new Image((getClass().getResourceAsStream("/CardsFront/led5.png")));
                break;
            case ("SERVANT"):
                ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/SERVANT.png")));
                i = new Image((getClass().getResourceAsStream("/CardsFront/led6.png")));
                break;
            case ("SHIELD"):
                ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/SHIELD.png")));
                i = new Image((getClass().getResourceAsStream("/CardsFront/led7.png")));
                break;
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId() != -1) {
            led1.setImage(i);
            led1.setOpacity(1);
        }
        if (!virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().isEmpty()) {
            if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() >= 1) {
                e1.setImage(ex1);
                e1.setOpacity(1);
                if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() == 2) {
                    e2.setImage(ex1);
                    e2.setOpacity(1);
                }
            }
        }


        String type1 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResourceType();
        Image ex2 = null;
        Image i2 = null;
        switch (type1) {
            case ("COIN"):
                ex2 = new Image((getClass().getResourceAsStream("/PunchBoard/COIN.png")));
                i2 = new Image((getClass().getResourceAsStream("/CardsFront/led8.png")));
                break;
            case ("STONE"):
                ex2 = new Image((getClass().getResourceAsStream("/PunchBoard/STONE.png")));
                i2 = new Image((getClass().getResourceAsStream("/CardsFront/led5.png")));
                break;
            case ("SERVANT"):
                ex2 = new Image((getClass().getResourceAsStream("/PunchBoard/SERVANT.png")));
                i2 = new Image((getClass().getResourceAsStream("/CardsFront/led6.png")));
                break;
            case ("SHIELD"):
                ex2 = new Image((getClass().getResourceAsStream("/PunchBoard/SHIELD.png")));
                i2 = new Image((getClass().getResourceAsStream("/CardsFront/led7.png")));
                break;
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId() != -1) {
            led2.setOpacity(1);
            led2.setImage(i2);
        }
        if (!virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().isEmpty()) {
            if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() >= 1) {
                e3.setImage(ex2);
                e3.setOpacity(1);
                if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() == 2) {
                    e4.setImage(ex2);
                    e4.setOpacity(1);
                }
            }
        }
    }

}

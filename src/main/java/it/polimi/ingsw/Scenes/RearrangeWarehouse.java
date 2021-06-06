package it.polimi.ingsw.Scenes;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyDepot;
import it.polimi.ingsw.client.DummyModel.DummyExtraDepot;
import it.polimi.ingsw.client.DummyModel.DummyWareHouse;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import it.polimi.ingsw.utility.DummyWarehouseConstructor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class RearrangeWarehouse extends ViewObservable {

    @FXML
    public Button d1,d2,d3;
    VirtualModel virtualModel;
    DummyWareHouse dummyWareHouse;
    Gson gson = new Gson();
    public Label wLabel;
    public ImageView r1;
    public ImageView r2;
    public ImageView r3;
    public ImageView r4, r5, r6;
    DummyDepot dep1;
    DummyDepot dep2;
    DummyDepot dep3;
    ArrayList<String> res;
    int i=0;

    public RearrangeWarehouse() {
        this.res = new ArrayList<>();

        try {
            this.dummyWareHouse = DummyWarehouseConstructor.parseVoid();
            dummyWareHouse.setDepot1(dep1);
            dummyWareHouse.setDepot2(dep2);
            dummyWareHouse.setDepot3(dep3);
        } catch (JsonFileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    ImageView res1;

    @FXML
    public void initialize(){

    }

    @FXML
    public void pick1(){
    }



    @FXML
    public void put1(ActionEvent actionEvent) {
        dummyWareHouse.setDepot1(dep1);
        dep1.addResource(res.get(i));
        i++;
        Image il = new Image(getClass().getResourceAsStream("/PunchBoard/" + res.get(i) + ".png"));
        res1.setImage(il);
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DEPOTS, gson.toJson(dummyWareHouse))));
        }
    }

    @FXML
    public void put2(ActionEvent actionEvent) {
        dummyWareHouse.setDepot1(dep2);
        dep2.addResource(res.get(i));
        i++;
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DEPOTS, gson.toJson(dummyWareHouse))));
        }

    }

    @FXML
    public void put3(ActionEvent actionEvent) {
        dummyWareHouse.setDepot1(dep3);
        dep3.addResource(res.get(i));
        i++;
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DEPOTS, gson.toJson(dummyWareHouse))));
        }
    }


    public void setWarehouse(VirtualModel virtualModel) throws JsonFileNotFoundException {
        this.virtualModel = virtualModel;

        //sets warehouse
        if(virtualModel.getSlot1()!= "") {
            Image im1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot1() + ".png"));
            r1.setImage(im1);
            r1.setOpacity(1);
            res.add(virtualModel.getSlot1());
        }
        if(virtualModel.getSlot2()!= "") {
            Image im2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot2() + ".png"));
            r2.setImage(im2);
            r2.setOpacity(1);
            res.add(virtualModel.getSlot2());
        }
        if(virtualModel.getSlot3()!= "") {
            Image im3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot3() + ".png"));
            r3.setImage(im3);
            r3.setOpacity(1);
            res.add(virtualModel.getSlot3());
        }
        if(virtualModel.getSlot4()!= "") {
            Image im4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot4() + ".png"));
            r4.setImage(im4);
            r4.setOpacity(1);
            res.add(virtualModel.getSlot4());
        }
        if(virtualModel.getSlot5()!= "") {
            Image im5 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot5() + ".png"));
            r5.setImage(im5);
            r5.setOpacity(1);
            res.add(virtualModel.getSlot5());
        }
        if(virtualModel.getSlot6()!= "") {
            Image im6 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot6() + ".png"));
            r6.setImage(im6);
            r6.setOpacity(1);
            res.add(virtualModel.getSlot6());
        }









    }


    public void exit(ActionEvent actionEvent) {
    }
}

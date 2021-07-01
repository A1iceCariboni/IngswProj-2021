package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyExtraDepot;
import it.polimi.ingsw.client.DummyModel.DummyWareHouse;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.DepotMessage;
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


/**@author Alessandra Atria
/** This class represents the controller for the scene where
 * the player can rearrange his resources in the warehouse*/
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
    public ImageView led1, led2;
    public ImageView r4, r5, r6, re1, re2, re3, re4;

    @FXML
    private Button ex1;

    @FXML
    private Button ex2;


    ArrayList<String> res;
    int i;

    public RearrangeWarehouse() {
        res = new ArrayList<>();
        i = 0;
    }


    @FXML
    ImageView res1;

    @FXML
    public void initialize(){

    }


 /** buttons used to choose to put the resource in the selected depot */
    @FXML
    public void put1(ActionEvent actionEvent) {
        dummyWareHouse.getDepot1().addResource(res.get(i));
        Image il = new Image(getClass().getResourceAsStream("/PunchBoard/" + res.get(i) + ".png"));
        res1.setImage(il);
        res1.setOpacity(1);
        i++;
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new DepotMessage(dummyWareHouse)));
        }
    }

    @FXML
    public void put2(ActionEvent actionEvent) {
        dummyWareHouse.getDepot2().addResource(res.get(i));
        i++;
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new DepotMessage(dummyWareHouse)));
        }

    }

    @FXML
    public void put3(ActionEvent actionEvent) {
        dummyWareHouse.getDepot3().addResource(res.get(i));
        i++;
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new DepotMessage(dummyWareHouse)));
        }
    }

    @FXML
    public void putE1(ActionEvent actionEvent) {
        dummyWareHouse.getExtraDepot1().addResource(res.get(i));
        i++;
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new DepotMessage(dummyWareHouse)));
        }
    }



    @FXML
    public void putE2(ActionEvent actionEvent) {
        dummyWareHouse.getExtraDepot2().addResource(res.get(i));
        i++;
        if(i == res.size()) {
            notifyObserver(obs -> obs.onReadyReply(new DepotMessage(dummyWareHouse)));
        }
    }


    /**sets the scene */
    public void setWarehouse(VirtualModel virtualModel) throws JsonFileNotFoundException {
        this.virtualModel = virtualModel;

        //sets warehouse
        if(!virtualModel.getSlot1().equals("")) {
            Image im1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot1() + ".png"));
            r1.setImage(im1);
            r1.setOpacity(1);
            res.add(virtualModel.getSlot1());
        }
        if(!virtualModel.getSlot2().equals("")) {
            Image im2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot2() + ".png"));
            r2.setImage(im2);
            r2.setOpacity(1);
            res.add(virtualModel.getSlot2());
        }
        if(!virtualModel.getSlot3().equals("")) {
            Image im3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot3() + ".png"));
            r3.setImage(im3);
            r3.setOpacity(1);
            res.add(virtualModel.getSlot3());
        }
        if(!virtualModel.getSlot4().equals("")) {
            Image im4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot4() + ".png"));
            r4.setImage(im4);
            r4.setOpacity(1);
            res.add(virtualModel.getSlot4());
        }
        if(!virtualModel.getSlot5().equals("")) {
            Image im5 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot5() + ".png"));
            r5.setImage(im5);
            r5.setOpacity(1);
            res.add(virtualModel.getSlot5());
        }
        if(!virtualModel.getSlot6().equals("")) {
            Image im6 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot6() + ".png"));
            r6.setImage(im6);
            r6.setOpacity(1);
            res.add(virtualModel.getSlot6());
        }
        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId() != -1) {
            if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() >= 1) {
                String type = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResourceType();
                Image iE1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + type + ".png"));
                re1.setImage(iE1);
                re1.setOpacity(1);
                if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() == 2) {
                    re2.setImage(iE1);
                    re2.setOpacity(1);
                }
            }
            res.addAll(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources());
        }
        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId() != -1) {
            if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() >= 1) {
                String type1 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResourceType();
                Image iE1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + type1 + ".png"));
                re3.setImage(iE1);
                re3.setOpacity(1);
                if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() == 2) {
                    re4.setImage(iE1);
                    re4.setOpacity(1);
                }
            }
            res.addAll(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources());

        }




        try {
            dummyWareHouse = DummyWarehouseConstructor.parseVoid();
            DummyExtraDepot dummyExtraDepot1 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1();
            if(dummyExtraDepot1.getId() != -1)
                dummyWareHouse.setExtraDepot1(new DummyExtraDepot(dummyExtraDepot1.getId(),dummyExtraDepot1.getDimension(), new ArrayList<>(), dummyExtraDepot1.getResourceType()));
            DummyExtraDepot dummyExtraDepot2 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2();
            if(dummyExtraDepot2.getId() != -1)
                dummyWareHouse.setExtraDepot2(new DummyExtraDepot(dummyExtraDepot2.getId(),dummyExtraDepot2.getDimension(), new ArrayList<>(), dummyExtraDepot2.getResourceType()));
        } catch (JsonFileNotFoundException e) {
            System.out.println("Error loading the warehouse");
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId() == -1) {
            ex1.setDisable(true);
            ex1.setOpacity(0);
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId() == -1) {
            ex2.setDisable(true);
            ex2.setOpacity(0);
        }

        String type = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResourceType();
        Image i = switch (type) {
            case ("COIN") -> new Image((getClass().getResourceAsStream("/CardsFront/led8.png")));
            case ("STONE") -> new Image((getClass().getResourceAsStream("/CardsFront/led5.png")));
            case ("SERVANT") -> new Image((getClass().getResourceAsStream("/CardsFront/led6.png")));
            case ("SHIELD") -> new Image((getClass().getResourceAsStream("/CardsFront/led7.png")));
            default -> null;
        };

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId() != -1) {
            led1.setImage(i);
            led1.setOpacity(1);
        }

        String type1 = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResourceType();
        Image i2 = switch (type1) {
            case ("COIN") -> new Image((getClass().getResourceAsStream("/CardsFront/led8.png")));
            case ("STONE") -> new Image((getClass().getResourceAsStream("/CardsFront/led5.png")));
            case ("SERVANT") -> new Image((getClass().getResourceAsStream("/CardsFront/led6.png")));
            case ("SHIELD") -> new Image((getClass().getResourceAsStream("/CardsFront/led7.png")));
            default -> null;
        };

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId() != -1) {
            led2.setOpacity(1);
            led2.setImage(i2);
        }

    }



    /** button to go back to the player's board scene*/
    public void exit(ActionEvent actionEvent) {
        Board bc = new Board();
        GUIRunnable.changetoStart(bc, observers, virtualModel);
    }
}

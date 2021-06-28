package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**@author Alessandra Atria*/

public class AddtoWarehouse extends ViewObservable {
    public Button d1,d2,d3, c1, c2,c3 ;
    public Button disc;
    VirtualModel virtualModel;
    Gson gson = new Gson();
    public Label wLabel;
    public ImageView r1;
    public ImageView r2;
    public ImageView r3;
    public ImageView r4;
    public Label sst, sc, ssh, sv;
    private String[] chosenRes;
    private int i = 0;
    private int count = 0;
    private ArrayList<String> resource;
    private String type;
    private int[] answer;

    @FXML
    ImageView ledp1, ledp2, led1, led2;
    @FXML
    ImageView e1, e2,e3,e4, ep1, ep2,ep3,ep4;
    @FXML
    private Button ex1;
    @FXML
    private Button ex2;
    @FXML
    private Button ex11, ex12;
    @FXML
    private Button strongbox;

    @FXML
    ImageView res1, res2, res3, res4, res5, res6;

    @FXML
    public void initialize(){

    }

    @FXML
    public void put1(ActionEvent actionEvent) {
        answer[count] = 1;
        count++;
        if(count == resource.size()) {
            notifyObserver(obs -> obs.onReadyReply(new PlaceResources(answer)));
        }
    }

    @FXML
    public void put2(ActionEvent actionEvent) {
        answer[count] = 2;
        count++;
        if(count == resource.size()) {
            notifyObserver(obs -> obs.onReadyReply(new PlaceResources(answer)));
        }
    }

    @FXML
    public void put3(ActionEvent actionEvent) {
        answer[count] = 3;
        count++;
        if(count == resource.size()) {
            notifyObserver(obs -> obs.onReadyReply(new PlaceResources(answer)));
        }
    }

    @FXML
    public void putex1(ActionEvent actionEvent) {
        answer[count] = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId();
        count++;
        if(count == resource.size()) {
            notifyObserver(obs -> obs.onReadyReply(new PlaceResources(answer)));
        }
    }

    @FXML
    public void putex2(ActionEvent actionEvent) {
        answer[count] = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId();
        count++;
        if(count == resource.size()) {
            notifyObserver(obs -> obs.onReadyReply(new PlaceResources(answer)));
        }
    }


    @FXML
    public void discard(ActionEvent actionEvent) {
        answer[count] = -1;
        count++;
        if(count == resource.size()) {
            notifyObserver(obs -> obs.onReadyReply(new PlaceResources(answer)));
        }
    }


    public void pickServant(MouseEvent mouseEvent) {
        chosenRes[i] = "SERVANT";
        i++;
        if(i == chosenRes.length) {
            Message message = new ResourcesReply(chosenRes);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickCoin(MouseEvent mouseEvent) {
        chosenRes[i] = "COIN";
        i++;
        if(i == chosenRes.length) {
            Message message = new ResourcesReply(chosenRes);
            notifyObserver(obs -> obs.onReadyReply(message));

        }
    }

    public void pickShield(MouseEvent mouseEvent) {
        chosenRes[i] = "SHIELD";
        i++;
        if(i == chosenRes.length) {
            Message message = new ResourcesReply(chosenRes);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickStone(MouseEvent mouseEvent) {
        chosenRes[i] = "STONE";
        i++;
        if(i == chosenRes.length) {
            Message message = new ResourcesReply(chosenRes);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }




    
    public void setResource(String[] resource, VirtualModel virtualModel) {
        wLabel.setText("Where do you want to put it?");
        this.virtualModel = virtualModel;
        this.resource = new ArrayList<>();
        this.resource.addAll(Arrays.asList(resource));
        answer = new int[resource.length];
        int coin = 0;
        int stone = 0;
        int servant = 0;
        int shield = 0;
        strongbox.setOpacity(0);
        strongbox.setDisable(true);
        for (String res : virtualModel.getPlayerBoard().getStrongBox().getResources()) {
            if (res.equals("COIN"))
                coin++;
            if (res.equals("STONE"))
                stone++;
            if (res.equals("SHIELD"))
                shield++;
            if (res.equals("SERVANT"))
                servant++;
        }
        sc.setText("x" + coin);
        sst.setText("x" + stone);
        ssh.setText("x" + shield);
        sv.setText("x" + servant);
        c1.setOpacity(0);
        c2.setOpacity(0);
        c3.setOpacity(0);
        ex12.setOpacity(0);
        ex11.setOpacity(0);
        Image i1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[0] + ".png"));
        r1.setOpacity(1);
        r1.setImage(i1);
        if (resource.length >= 2) {
            Image i2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[1] + ".png"));
            r2.setOpacity(1);
            r2.setImage(i2);
        }
        if (resource.length >= 3) {
            Image i3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[2] + ".png"));
            r3.setImage(i3);
            r3.setOpacity(1);
        }
        if (resource.length == 4) {
            Image i4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[3] + ".png"));
            r4.setImage(i4);
            r4.setOpacity(1);
        }

        //sets warehouse
        if (virtualModel.getSlot1() != "") {
            Image im1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot1() + ".png"));
            res1.setImage(im1);
            res1.setOpacity(1);
        }
        if (virtualModel.getSlot2() != "") {
            Image im2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot2() + ".png"));
            res2.setImage(im2);
            res2.setOpacity(1);
        }
        if (virtualModel.getSlot3() != "") {
            Image im3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot3() + ".png"));
            res3.setImage(im3);
            res3.setOpacity(1);
        }
        if (virtualModel.getSlot4() != "") {
            Image im4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot4() + ".png"));
            res4.setImage(im4);
            res4.setOpacity(1);
        }
        if (virtualModel.getSlot5() != "") {
            Image im5 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot5() + ".png"));
            res5.setImage(im5);
            res5.setOpacity(1);
        }
        if (virtualModel.getSlot6() != "") {
            Image im6 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot6() + ".png"));
            res6.setImage(im6);
            res6.setOpacity(1);
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId() == -1) {
            ex1.setDisable(true);
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId() == -1) {
            ex2.setDisable(true);
        }
        type = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResourceType();
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


    public void setPay(ArrayList<String> resource, VirtualModel virtualModel) {
        wLabel.setText("Choose the resources you want to use to pay");
        this.virtualModel = virtualModel;
        this.resource = new ArrayList<>();
        this.resource.addAll(resource);
        answer = new int[resource.size()];
        d1.setOpacity(0);
        d2.setOpacity(0);
        d3.setOpacity(0);
        ex1.setOpacity(0);
        ex2.setOpacity(0);
        disc.setOpacity(0);
        int coin = 0;
        int stone = 0;
        int servant = 0;
        int shield = 0;
        for (String res : virtualModel.getPlayerBoard().getStrongBox().getResources()) {
            if (res.equals("COIN"))
                coin++;
            if (res.equals("STONE"))
                stone++;
            if (res.equals("SHIELD"))
                shield++;
            if (res.equals("SERVANT"))
                servant++;
        }
        sc.setText("x" + coin);
        sst.setText("x" + stone);
        ssh.setText("x" + shield);
        sv.setText("x" + servant);

        //sets warehouse
        if (virtualModel.getSlot1() != "") {
            Image im1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot1() + ".png"));
            res1.setImage(im1);
            res1.setOpacity(1);
        }
        if (virtualModel.getSlot2() != "") {
            Image im2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot2() + ".png"));
            res2.setImage(im2);
            res2.setOpacity(1);
        }
        if (virtualModel.getSlot3() != "") {
            Image im3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot3() + ".png"));
            res3.setImage(im3);
            res3.setOpacity(1);
        }
        if (virtualModel.getSlot4() != "") {
            Image im4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot4() + ".png"));
            res4.setImage(im4);
            res4.setOpacity(1);
        }
        if (virtualModel.getSlot5() != "") {
            Image im5 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot5() + ".png"));
            res5.setImage(im5);
            res5.setOpacity(1);
        }
        if (virtualModel.getSlot6() != "") {
            Image im6 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot6() + ".png"));
            res6.setImage(im6);
            res6.setOpacity(1);
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId() == -1) {
            ex11.setDisable(true);
        }

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId() == -1) {
            ex12.setDisable(true);
        }



        type = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResourceType();
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

        if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId()!= -1) {
            ledp1.setImage(i);
            ledp1.setOpacity(1);
        }
        if (!virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().isEmpty()) {
            if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() >= 1) {
                ep1.setImage(ex1);
                ep1.setOpacity(1);
                if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() == 2) {
                    ep2.setImage(ex1);
                    ep2.setOpacity(1);
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
                ledp2.setImage(i2);
                ledp2.setOpacity(1);
            }
            if (!virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().isEmpty()) {
            if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() >= 1) {
                ep3.setImage(ex2);
                ep3.setOpacity(1);
                if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() == 2) {
                    ep4.setImage(ex2);
                    ep4.setOpacity(1);
                }
            }
        }
    }




    public void setQuantity(int quantity){
        chosenRes = new String[quantity];
        r1.setOpacity(1);
        r2.setOpacity(1);
        r3.setOpacity(1);
        r4.setOpacity(1);
        d1.setOpacity(0);
        d2.setOpacity(0);
        d3.setOpacity(0);
        c1.setOpacity(0);
        c2.setOpacity(0);
        c3.setOpacity(0);
        ex11.setOpacity(0);
        ex12.setOpacity(0);
        ex1.setOpacity(0);
        ex2.setOpacity(0);
        disc.setOpacity(0);
    }


    @FXML
    public void pay1(ActionEvent actionEvent) {
        answer[count] = 1;
        count++;
        if(count == resource.size()) {
            Message message = new ResourcePayment(answer);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    @FXML
    public void pay2(ActionEvent actionEvent) {
        answer[count] = 2;
        count++;
        if(count == resource.size()) {
            Message message = new ResourcePayment(answer);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    @FXML
    public void pay3(ActionEvent actionEvent) {
        answer[count] = 3;
        count++;
        if(count == resource.size()) {
         Message message = new ResourcePayment(answer);
         notifyObserver(obs -> obs.onReadyReply(message));
        }
    }


    @FXML
    public void pays1(ActionEvent actionEvent) {
        answer[count] = -1;
        count++;
        if(count == resource.size()) {
            Message message = new Message(MessageType.RESOURCE_PAYMENT, gson.toJson(answer));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }



    @FXML
    public void payex1(ActionEvent actionEvent) {
        answer[count] = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getId();
        count++;
        if(count == resource.size()) {
            Message message = new ResourcePayment(answer);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    @FXML
    public void payex2(ActionEvent actionEvent) {
        answer[count] = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getId();
        count++;
        if(count == resource.size()) {
            Message message = new ResourcePayment(answer);
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

}

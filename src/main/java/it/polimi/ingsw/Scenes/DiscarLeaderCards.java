package it.polimi.ingsw.Scenes;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** @author  Alessandra Atria*/


public class DiscarLeaderCards extends ViewObservable {

    private VirtualModel virtualModel;



    @FXML
    private Button okBtn;
    @FXML
    public ImageView l1;
    @FXML
    public ImageView l2;
    @FXML
    public ImageView l3;
    @FXML
    public ImageView l4;

    public DiscarLeaderCards() {
        this.virtualModel = new VirtualModel();
    }


    @FXML
    public void initialize() {

    }

    @FXML
    public void pick1(){

    }


    @FXML
    public void pick2(){

    }

    @FXML
    public void pick3(){

    }

    @FXML
    public void pick4(){

    }
    @FXML
    public void DiscardLeader1() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(virtualModel.getLeaderCards().get(0).getId()))));
        l1.setVisible(false);

    }

    @FXML
    public void DiscardLeader2() {

        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(virtualModel.getLeaderCards().get(1).getId()))));
        l2.setVisible(false);
    }

    @FXML
    public void DiscardLeader3() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(virtualModel.getLeaderCards().get(2).getId()))));
        l3.setVisible(false);
    }

    @FXML
    public void DiscardLeader4() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(virtualModel.getLeaderCards().get(3).getId()))));
        l4.setVisible(false);

    }


    @FXML
    public void ActivateLeader1(){}

    @FXML
    public void ActivateLeader2(){}

    @FXML
    public void ActivateLeader3(){}
    @FXML
    public void ActivateLeader4(){}





    public void Exit() {
        Board bc = new Board();
        GUIRunnable.changetoStart(bc, observers);

    }


    public void setLeaderCards(VirtualModel virtualModel) {
        this.virtualModel =  virtualModel;
        Image img1 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(0).getId() + ".png"));
        l1.setImage(img1);
        Image img2 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(1).getId()+ ".png"));
        l2.setImage(img2);
        Image img3 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(2).getId() + ".png"));
        l3.setImage(img3);
        Image img4 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(3).getId() + ".png"));
        l4.setImage(img4);

    }

}







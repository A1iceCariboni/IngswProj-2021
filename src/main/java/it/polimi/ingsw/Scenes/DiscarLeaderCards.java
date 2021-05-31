package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DiscarLeaderCards extends ViewObservable {
    int id1, id2, id3, id4;
    private VirtualModel virtualModel;
    int count;


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
    }


    @FXML
    public void initialize() {
    }


    public void pick1() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(1))));
    }

    public void pick2() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(2))));
    }


    public void pick3() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(3))));
    }

    public void pick4() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.DISCARD_LEADER, Integer.toString(4))));

    }

    public void Exit() {
        Board bc = new Board();
        bc.addAllObservers(observers);
        GUIRunnable.changetoStart(bc);

    }


    public void setLeaderCards(VirtualModel virtualModel) {
        this.virtualModel = virtualModel;
        id1 = virtualModel.getLeaderCards().get(0).getId();
        id2 = virtualModel.getLeaderCards().get(1).getId();
        id3 = virtualModel.getLeaderCards().get(2).getId();
        id4 = virtualModel.getLeaderCards().get(3).getId();
        Image img1 = new Image(getClass().getResourceAsStream("/CardsFront/led" + id1 + ".png"));
        Image img2 = new Image(getClass().getResourceAsStream("/CardsFront/led" + id2 + ".png"));
        Image img3 = new Image(getClass().getResourceAsStream("/CardsFront/led" + id3 + ".png"));
        Image img4 = new Image(getClass().getResourceAsStream("/CardsFront/led" + id4 + ".png"));
        l1.setImage(img1);
        l2.setImage(img2);
        l3.setImage(img3);
        l4.setImage(img4);
    }

}







package it.polimi.ingsw.Scenes;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.ActivateLeader;
import it.polimi.ingsw.messages.DiscardLeader;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** @author  Alessandra Atria*/
/** This class represents the controller for the scene where the player can discard his leadercards or activates them */

public class DiscarLeaderCards extends ViewObservable {

    private VirtualModel virtualModel;

    @FXML
    private Button DiscardButton1, DiscardButton2, DiscardButton3, DiscardButton4;

    @FXML
    private Button ActivateButton1, ActivateButton2, ActivateButton3, ActivateButton4;
    @FXML
    public ImageView l1;
    @FXML
    public ImageView l2;
    @FXML
    public ImageView l3;
    @FXML
    public ImageView l4;

    /**constructor*/
    public DiscarLeaderCards() {
        this.virtualModel = new VirtualModel();
    }


   /** button to discard a leadercard*/
    @FXML
    public void DiscardLeader1() {
        notifyObserver(obs -> obs.onReadyReply(new DiscardLeader(virtualModel.getLeaderCards().get(0).getId())));
        l1.setVisible(false);

    }

    @FXML
    public void DiscardLeader2() {

        notifyObserver(obs -> obs.onReadyReply(new DiscardLeader(virtualModel.getLeaderCards().get(1).getId())));
        l2.setVisible(false);
    }

    @FXML
    public void DiscardLeader3() {
        notifyObserver(obs -> obs.onReadyReply(new DiscardLeader(virtualModel.getLeaderCards().get(2).getId())));
        l3.setVisible(false);
    }

    @FXML
    public void DiscardLeader4() {
        notifyObserver(obs -> obs.onReadyReply(new DiscardLeader(virtualModel.getLeaderCards().get(3).getId())));
        l4.setVisible(false);

    }

   /** button to activate a leadercard*/
    @FXML
    public void ActivateLeader1(){
        Message message = new ActivateLeader(virtualModel.getLeaderCards().get(0).getId());
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    @FXML
    public void ActivateLeader2(){
        Message message = new ActivateLeader(virtualModel.getLeaderCards().get(1).getId());
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    @FXML
    public void ActivateLeader3(){
        Message message = new ActivateLeader(virtualModel.getLeaderCards().get(2).getId());
        notifyObserver(obs -> obs.onReadyReply(message));
    }
    @FXML
    public void ActivateLeader4(){
        Message message = new ActivateLeader(virtualModel.getLeaderCards().get(3).getId());
        notifyObserver(obs -> obs.onReadyReply(message));
    }


    /**disable buttons to discard when the player choose to activate a leader card*/
    public void disableDevButtons(){
         DiscardButton1.setDisable(true);
         DiscardButton2.setDisable(true);
         DiscardButton3.setDisable(true);
         DiscardButton4.setDisable(true);
    }

    /**disable buttons to activate when the player choose to discard a leader card*/
    public void disableActiveButtons(){
        ActivateButton1.setDisable(true);
        ActivateButton2.setDisable(true);
        ActivateButton3.setDisable(true);
        ActivateButton4.setDisable(true);

    }



    /**sets the scene*/
    public void setLeaderCards(VirtualModel virtualModel) {
        this.virtualModel =  virtualModel;
        if(virtualModel.getLeaderCards().size() >= 1) {
            Image img1 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(0).getId() + ".png"));
            l1.setImage(img1);
        }else{
            DiscardButton1.setDisable(true);
            ActivateButton1.setDisable(true);}
        if(virtualModel.getLeaderCards().size() >= 2) {
            Image img2 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(1).getId() + ".png"));
            l2.setImage(img2);
        }else {
            DiscardButton2.setDisable(true);
            ActivateButton2.setDisable(true);}
        if(virtualModel.getLeaderCards().size() >= 3) {
            Image img3 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(2).getId() + ".png"));
            l3.setImage(img3);
        }else {
            DiscardButton3.setDisable(true);
            ActivateButton3.setDisable(true);
        }
        if(virtualModel.getLeaderCards().size() >= 4) {
            Image img4 = new Image(getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(3).getId() + ".png"));
            l4.setImage(img4);
        }else {
            DiscardButton4.setDisable(true);
            ActivateButton4.setDisable(true);
        }

    }

    /**button to go back to player 's board scene */
    public void back(ActionEvent actionEvent) {
        Board bc = new Board();
        GUIRunnable.changetoStart(bc, observers,virtualModel);
    }
}







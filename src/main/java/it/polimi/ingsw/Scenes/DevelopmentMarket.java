package it.polimi.ingsw.Scenes;

import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class DevelopmentMarket extends ViewObservable {
    Gson gson = new Gson();
    VirtualModel virtualModel;
    ArrayList<Integer> payloadDev = new ArrayList<>();

 @FXML
 public ImageView img00, img01, img02, img03, img10,img11, img12, img13, img20, img21, img22, img23;


   @FXML
   public void select00(){
       payloadDev.add(0);
       payloadDev.add(0);
       Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
       notifyObserver(obs -> obs.onReadyReply(messageDev));
        }

   @FXML
    public void select01(){
        payloadDev.add(0);
        payloadDev.add(1);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select02(){
        payloadDev.add(0);
        payloadDev.add(2);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select03(){
        payloadDev.add(0);
        payloadDev.add(3);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }


    @FXML
    public void select10(){
        payloadDev.add(1);
        payloadDev.add(0);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select11(){
        payloadDev.add(1);
        payloadDev.add(1);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select12(){
        payloadDev.add(1);
        payloadDev.add(2);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select13(){
        payloadDev.add(1);
        payloadDev.add(3);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select20(){
        payloadDev.add(2);
        payloadDev.add(0);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select21(){
        payloadDev.add(2);
        payloadDev.add(1);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select22(){
        payloadDev.add(2);
        payloadDev.add(2);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select23(){
        payloadDev.add(2);
        payloadDev.add(3);
        Message messageDev = new Message(MessageType.BUY_DEV, gson.toJson(payloadDev));
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }



        public void setDevCards(VirtualModel virtualModel){
            this.virtualModel= virtualModel;
            ArrayList<Image> images2 = new ArrayList<>();
            for(int i=0; i< Constants.rows; i++){
                for (int j=0; j<Constants.cols; j++){
                    int id = virtualModel.getBoardDevCard()[i][j].getId();
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardsFront/devCard" + id + ".png")));
                    images2.add(image);
                }
            }
            img00.setImage(images2.get(0));
            img01.setImage(images2.get(1));
            img02.setImage(images2.get(2));
            img03.setImage(images2.get(3));
            img10.setImage(images2.get(4));
            img11.setImage(images2.get(5));
            img12.setImage(images2.get(6));
            img13.setImage(images2.get(7));
            img20.setImage(images2.get(8));
            img21.setImage(images2.get(9));
            img22.setImage(images2.get(10));
            img23.setImage(images2.get(11));
        }


    public void back(ActionEvent actionEvent) {
     //   Board bc = new Board();
 //       Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers).setVirtualModel(virtualModel, turnPhase));
    }
}

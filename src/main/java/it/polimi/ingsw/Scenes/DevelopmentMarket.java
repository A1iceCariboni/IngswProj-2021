package it.polimi.ingsw.Scenes;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.messages.BuyDev;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Objects;

/**This class represents the controller for the development cards' market */
/** @author Alessandra Atria */
public class DevelopmentMarket extends ViewObservable {
    VirtualModel virtualModel;
    ArrayList<Integer> payloadDev = new ArrayList<>();
    int id;

 public  DevelopmentMarket(){
     id=0;
 }

 @FXML
 public ImageView img00, img01, img02, img03, img10,img11, img12, img13, img20, img21, img22, img23;

   /** by selecting the card, the player chooses to buy it*/
   @FXML
   public void select00(){
       payloadDev.add(0);
       payloadDev.add(0);
       Message messageDev = new BuyDev(payloadDev);
       notifyObserver(obs -> obs.onReadyReply(messageDev));
        }

   @FXML
    public void select01(){
        payloadDev.add(0);
        payloadDev.add(1);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select02(){
        payloadDev.add(0);
        payloadDev.add(2);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select03(){
        payloadDev.add(0);
        payloadDev.add(3);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }


    @FXML
    public void select10(){
        payloadDev.add(1);
        payloadDev.add(0);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select11(){
        payloadDev.add(1);
        payloadDev.add(1);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select12(){
        payloadDev.add(1);
        payloadDev.add(2);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select13(){
        payloadDev.add(1);
        payloadDev.add(3);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select20(){
        payloadDev.add(2);
        payloadDev.add(0);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select21(){
        payloadDev.add(2);
        payloadDev.add(1);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select22(){
        payloadDev.add(2);
        payloadDev.add(2);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }

    @FXML
    public void select23(){
        payloadDev.add(2);
        payloadDev.add(3);
        Message messageDev = new BuyDev(payloadDev);
        notifyObserver(obs -> obs.onReadyReply(messageDev));
    }



    /**set the development cards' board*/
        public void setDevCards(VirtualModel virtualModel){
            this.virtualModel= virtualModel;
            ArrayList<Image> images2 = new ArrayList<>();
            for(int i=0; i< Constants.rows; i++) {
                for (int j = 0; j < Constants.cols; j++) {
                    if (virtualModel.getBoardDevCard()[i][j] != null) {
                        id = virtualModel.getBoardDevCard()[i][j].getId();
                        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardsFront/devCard" + id + ".png")));
                        images2.add(image);
                    } else {
                        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardsBack/MastersofRenaissance__Cards_BACK_3mmBleed-49-1.png")));
                        images2.add(image);
                    }
                }
            }
            if(images2.get(0)!= null)
            img00.setImage(images2.get(0));
            if(images2.get(1)!= null)
            img01.setImage(images2.get(1));
            if(images2.get(2)!= null)
            img02.setImage(images2.get(2));
            if(images2.get(3)!= null)
            img03.setImage(images2.get(3));
            if(images2.get(4)!= null)
            img10.setImage(images2.get(4));
            if(images2.get(5)!= null)
            img11.setImage(images2.get(5));
            if(images2.get(6)!= null)
            img12.setImage(images2.get(6));
            if(images2.get(7)!= null)
            img13.setImage(images2.get(7));
            if(images2.get(8)!= null)
            img20.setImage(images2.get(8));
            if(images2.get(9)!= null)
            img21.setImage(images2.get(9));
            if(images2.get(10)!= null)
            img22.setImage(images2.get(10));
            if(images2.get(11)!= null)
            img23.setImage(images2.get(11));
        }

    /**button used to go back to the player's board scene*/
    public void back(ActionEvent actionEvent) {
       Board bc = new Board();
       Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel));
    }
}

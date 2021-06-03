package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;



public class AddtoWarehouse extends ViewObservable {
    public Button d1,d2,d3,d4,d5,d6;
    Gson gson = new Gson();
    public Label wLabel;
    public ImageView r1;
    public ImageView r2;
    public ImageView r3;
    public ImageView r4;
    private String[] chosenRes;
    private int i = 0;
    private int count = 0;
    private String[] resource;
    private int[] answer;

    @FXML
    public void initialize(){

    }

    @FXML
    public void put1(ActionEvent actionEvent) {
        answer[count] = 1;
        count++;
        if(count == resource.length) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        }
    }

    @FXML
    public void put2(ActionEvent actionEvent) {
        answer[count] = 2;
        count++;
        if(count == resource.length) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        }
    }

    @FXML
    public void put3(ActionEvent actionEvent) {
        answer[count] = 2;
        count++;
        if(count == resource.length) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        }
    }

    @FXML
    public void put4(ActionEvent actionEvent) {
        answer[count] = 3;
        count++;
        if(count == resource.length) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        }
    }

    @FXML
    public void put5(ActionEvent actionEvent) {
        answer[count] = 3;
        count++;
        if(count == resource.length) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        }
    }


    public void put6(ActionEvent actionEvent){
        answer[count] = 3;
        count++;
        if(count == resource.length) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        }
    }

    @FXML
    public void discard(ActionEvent actionEvent) {
        answer[count] = -1;
        count++;
        if(count == resource.length) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.PLACE_RESOURCE_WAREHOUSE, gson.toJson(answer))));
        }
    }

    public void exit(ActionEvent actionEvent) {
    }

    public void pickServant(MouseEvent mouseEvent) {
        chosenRes[i] = "SERVANT";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickCoin(MouseEvent mouseEvent) {
        chosenRes[i] = "COIN";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));

        }
    }

    public void pickShield(MouseEvent mouseEvent) {
        chosenRes[i] = "SHIELD";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }

    public void pickStone(MouseEvent mouseEvent) {
        chosenRes[i] = "STONE";
        i++;
        if(i == chosenRes.length) {
            Message message = new Message(MessageType.CHOOSE_RESOURCES, gson.toJson(chosenRes));
            notifyObserver(obs -> obs.onReadyReply(message));
        }
    }




    
    public void setResource(String[] resource) {
        wLabel.setText("Where do you want to put it?");
        this.resource = resource;
        answer = new int[resource.length];
        Image i1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[0] + ".png"));
        r1.setOpacity(1);
        r1.setImage(i1);
        if (resource.length >= 2) {
            Image i2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[1] + ".png"));
            r2.setOpacity(1);
            r2.setImage(i2);
        }
        if (resource.length >= 3){
            Image i3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[2] + ".png"));
            r3.setImage(i3);
            r3.setOpacity(1);
        }
        if(resource.length ==4) {
            Image i4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + resource[3] + ".png"));
            r4.setImage(i4);
            r4.setOpacity(1);
        }
    }

    public void setQuantity(int quantity){
        chosenRes = new String[quantity];
        r1.setOpacity(1);
        r2.setOpacity(1);
        r3.setOpacity(1);
        r4.setOpacity(1);


    }



}

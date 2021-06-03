package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class DevCards extends ViewObservable {

    @FXML
    public ImageView img00;

    @FXML
    public ImageView img01;

    @FXML
    public ImageView img02;

    @FXML
    public ImageView img10;

    @FXML
    public ImageView img11;

    @FXML
    public ImageView img12;

    @FXML
    public ImageView img20;

    @FXML
    public ImageView img21;

    @FXML
    public ImageView img22;

    @FXML
    public ImageView img30;

    @FXML
    public ImageView img31;

    @FXML
    public ImageView img32;

    @FXML
    public CheckBox this00;

    @FXML
    public CheckBox this01;

    @FXML
    public CheckBox this02;

    @FXML
    public CheckBox this10;

    @FXML
    public CheckBox this11;

    @FXML
    public CheckBox this12;

    @FXML
    public CheckBox this20;

    @FXML
    public CheckBox this21;

    @FXML
    public CheckBox this22;

    @FXML
    public CheckBox this30;

    @FXML
    public CheckBox this31;

    @FXML
    public CheckBox this32;

    @FXML
    public Button okButton;


    private int count;
    private VirtualModel virtualModel;
    private ImageView[][] images;
    DummyDev[][] devBoard;
    int id;



    @FXML
    private StackPane rootPane;

    public DevCards() {
        images = new ImageView[Constants.rows][Constants.cols];
        count = 0;
        devBoard = new DummyDev[Constants.rows][Constants.cols];
        int id = 0;
    }

    @FXML
    public void initialize() {
      images[0][0] = img00;
      images[0][1] = img01;
      images[0][2] = img02;
      images[1][0] = img10;
      images[1][1] = img11;
      images[1][2] = img12;
      images[2][0] = img20;
      images[2][1] = img21;
      images[2][2] = img22;
      images[3][0] = img30;
      images[3][1] = img31;
      images[3][2] = img32;
    }



    public void selectThis00(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[0][0].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis01(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[0][1].getId();
        count ++;
        this00.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis10(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[1][0].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this00.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis20(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[2][0].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this00.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis30(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[3][0].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this00.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis11(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[1][1].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this00.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis21(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[2][1].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this00.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis31(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[3][1].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this00.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis02(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[0][2].getId();
        count ++;
        this01.setSelected(false);
        this00.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis12(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[1][2].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this00.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis22(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[2][2].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this00.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this32.setSelected(false);
        okButton.setDisable(false);
    }

    public void selectThis32(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[3][2].getId();
        count ++;
        this01.setSelected(false);
        this02.setSelected(false);
        this10.setSelected(false);
        this11.setSelected(false);
        this12.setSelected(false);
        this20.setSelected(false);
        this21.setSelected(false);
        this22.setSelected(false);
        this30.setSelected(false);
        this31.setSelected(false);
        this00.setSelected(false);
        okButton.setDisable(false);
    }

    public void Exit(ActionEvent actionEvent) {
        if (count >= 1) {
            notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.BUY_DEV, Integer.toString(id))));
            Board bc = new Board();
            bc.addAllObservers(observers);
            GUIRunnable.changetoStart(bc, observers);
        }
    }

    public void setDevCards(VirtualModel virtualModel) {
        this.virtualModel = virtualModel;
        this.devBoard = virtualModel.getBoardDevCard();
        for(int i=0; i< Constants.rows; i++){
            for (int j=0; j<Constants.cols; j++){
                int id = devBoard[i][j].getId();
                Image image = new Image(getClass().getResourceAsStream("/CardsFront/devCard" + id + ".png"));
                images[i][j].setImage(image);
            }
        }

    }
}

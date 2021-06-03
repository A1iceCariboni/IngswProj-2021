package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyDev;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Objects;

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


    private VirtualModel virtualModel;
    private DummyDev[][] devBoard;
    private int id;



    @FXML
    private StackPane rootPane;

    public DevCards() {
        devBoard = new DummyDev[Constants.rows][Constants.cols];
        id = 0;
    }

    @FXML
    public void initialize() {

        this00.setSelected(false);
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
        okButton.setDisable(true);
    }



    public void selectThis00(ActionEvent actionEvent) {
        id = virtualModel.getBoardDevCard()[0][0].getId();
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

    public void Exit() {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.BUY_DEV, Integer.toString(id))));
        Board bc = new Board();
        bc.addAllObservers(observers);
        GUIRunnable.changetoStart(bc, observers);

    }

    public void setDevCards(VirtualModel virtualModel) {
        this.virtualModel = virtualModel;
        this.devBoard = virtualModel.getBoardDevCard();
        ArrayList<Image> images2 = new ArrayList<>();
        for(int i=0; i< Constants.rows; i++){
            for (int j=0; j<Constants.cols; j++){
                int id = devBoard[i][j].getId();
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardsFront/devCard" + id + ".png")));
                images2.add(image);
            }
        }
        img00.setImage(images2.get(0));
        img01.setImage(images2.get(1));
        img02.setImage(images2.get(2));
        img10.setImage(images2.get(3));
        img11.setImage(images2.get(4));
        img12.setImage(images2.get(5));
        img20.setImage(images2.get(6));
        img21.setImage(images2.get(7));
        img22.setImage(images2.get(8));
        img30.setImage(images2.get(9));
        img31.setImage(images2.get(10));
        img32.setImage(images2.get(11));

    }
}

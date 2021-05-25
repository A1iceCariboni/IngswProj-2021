package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.observers.Observable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends Observable {

        @FXML
        private AnchorPane rootPane;
        @FXML
        private Button PlayButton;
        @FXML
        private Button ExitButton;

        @FXML
        private Button BottoneTitolo;

       @FXML
       public void GotoLoggerScene(Event event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("/prova.fxml"));
           Stage window =(Stage) PlayButton.getScene().getWindow();
           window.setScene(new Scene(root));

       }

      @FXML
      public void Exit(){
           System.exit(0);
      }

    public void nascondititolo(ActionEvent actionEvent) {



    }
}

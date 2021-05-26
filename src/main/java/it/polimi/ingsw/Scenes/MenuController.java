package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.observers.Observable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends Observable {

    public ImageView settingsBackground;

        @FXML
        AnchorPane rootPane;
        @FXML
        Button PlayButton;
        @FXML
        Button ExitButton;

        @FXML
        private Button BottoneTitolo;





       @FXML
       public void GotoLoggerScene(Event event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("/fxml/Username_Scene.fxml"));
           Stage window =(Stage) PlayButton.getScene().getWindow();
           window.setScene(new Scene(root));
           window.setWidth(1280d);
           window.setHeight(720d);
           window.setResizable(false);
           window.setMaximized(true);
           window.setFullScreen(true);
           window.setFullScreenExitHint("");
           window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

       }

      @FXML
      public void Exit(){
           System.exit(0);
      }

    public void nascondititolo(ActionEvent actionEvent) {



    }
}

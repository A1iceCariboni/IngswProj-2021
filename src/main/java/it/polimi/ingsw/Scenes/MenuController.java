package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.observers.GuiObservable;
import it.polimi.ingsw.observers.Observable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends GuiObservable {


        @FXML
        AnchorPane rootPane;
        @FXML
        Button PlayButton;
        @FXML
        Button ExitButton;


    @FXML
    public void initialize()  {


     PlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::GotoLoggerScene);
    }


       @FXML
       public void GotoLoggerScene(Event event){
           int port = Constants.DEFAULT_PORT;
           String ip = Constants.LOCAL_HOST;
           notifyObserver(obs -> obs.onConnectionRequest(ip,port));

       }

      @FXML
      public void Exit(){
           System.exit(0);
      }


}

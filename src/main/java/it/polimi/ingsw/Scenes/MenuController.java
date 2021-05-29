package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MenuController extends ViewObservable {


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

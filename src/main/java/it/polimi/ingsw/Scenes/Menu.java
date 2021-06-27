package it.polimi.ingsw.Scenes;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class Menu extends ViewObservable {


        @FXML
        AnchorPane rootPane;
        @FXML
        Button PlayButton;
        @FXML
        Button ExitButton;


    @FXML
    public void initialize()  {
    }


       @FXML
       public void GotoLoggerScene(Event event){
           ConnectScene connectScene = new ConnectScene();
           GUIRunnable.changetoConnect(connectScene, observers);
       }

      @FXML
      public void Exit(){
           System.exit(0);
      }


}

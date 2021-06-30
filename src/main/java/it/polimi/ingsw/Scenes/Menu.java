package it.polimi.ingsw.Scenes;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**@author Alessandra Atria*/
/** This class represents the controller for the first scene*/
public class Menu extends ViewObservable {
        @FXML
        Button PlayButton;
        @FXML
        Button ExitButton;


    @FXML
    public void initialize()  {
    }


       /** to go to the next scene*/
       @FXML
       public void GotoLoggerScene(Event event){
           ConnectScene connectScene = new ConnectScene();
           GUIRunnable.changetoConnect(connectScene, observers);
       }


       /** to exit the game*/
      @FXML
      public void Exit(){
           System.exit(0);
      }


}

package it.polimi.ingsw.Scenes;
import it.polimi.ingsw.messages.NumberOfPlayerReply;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/** @author Alessandra Atria*/
/** This class represents the controller for the scene where
 *  the first player chooses the number of players he wants to play with */

public class NumberOfPlayers extends ViewObservable {
  ObservableList list = FXCollections.observableArrayList();
    @FXML
    public Button ConfirmButton;
    @FXML
    public Button back_to_menu;
    @FXML
    public ChoiceBox<String> playerschoice;


    public void initialize(){
        loadData();
        
    }


    public void loadData(){
        list.removeAll(list);
        String one = "1 Player";
        String two = "2 Players";
        String three = "3 Players";
        String four = "4 Players";
        list.addAll(one,two,three,four);
        playerschoice.getItems().addAll(list);
    }



    /**to go to the next scene */
     @FXML
    public void GotoWaitingScene(ActionEvent event){
         int number = 0;
         if(playerschoice.getValue()!= null){
             number = Character.getNumericValue(playerschoice.getValue().charAt(0));
             NumberOfPlayerReply message = new NumberOfPlayerReply(number);
             notifyObserver(obs -> obs.onReadyReply(message));
             ConfirmButton.setDisable(true);
         }
    }

}

package it.polimi.ingsw.Scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {


    @FXML
    Button joinButton;
    @FXML
    Button back_to_menu ;

    @FXML
    TextField Nickname_field;


    public void Login(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/prova.fxml"));
        Stage window =(Stage) joinButton.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setWidth(1280d);
        window.setHeight(720d);
        window.setResizable(false);
        window.setMaximized(true);
        window.setFullScreen(true);
        window.setFullScreenExitHint("");
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

    }

    public void GotMenuScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu_Scene.fxml"));
        Stage window =(Stage) back_to_menu.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setWidth(1280d);
        window.setHeight(720d);
        window.setResizable(false);
        window.setMaximized(true);
        window.setFullScreen(true);
        window.setFullScreenExitHint(" ");
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

    }
}

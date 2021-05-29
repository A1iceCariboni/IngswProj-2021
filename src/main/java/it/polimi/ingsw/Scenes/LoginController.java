package it.polimi.ingsw.Scenes;
import it.polimi.ingsw.messages.request.SetupMessage;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends ViewObservable {


    public Label InvalidNickname;
    @FXML
    Button joinButton;
    @FXML
    Button back_to_menu ;

    @FXML
    TextField nicknameField;



    @FXML
    public void initialize(){
        InvalidNickname.setOpacity(0);

      //  back_to_menu.addEventHandler(MouseEvent.MOUSE_CLICKED, this::GotMenuScene);
    }

    @FXML
    public void Login(Event event)  {
        String nickname;
        nickname = nicknameField.getText();
        System.out.println(nickname);
        if(nickname.isEmpty()){
            InvalidNickname.setOpacity(100);
        }else {
            SetupMessage setupMessage = new SetupMessage(nickname);
            notifyObserver(obs -> obs.onUpdateNickname(setupMessage));
        }

    }


    @FXML
    public void GotMenuScene(Event event) throws IOException {
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

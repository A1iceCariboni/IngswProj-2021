package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ConnectScene extends ViewObservable {


    public Button back_to_menu;
    public TextField serverPort;
    public TextField serverName;

    public void Connect() {
        String ip;
        int port = Constants.DEFAULT_PORT;
        System.out.println(">Insert the server IP address [Press enter for localhost] ");
        System.out.print(">");
        ip = serverName.getText();
        try {
            port = Integer.parseInt(serverPort.getText());
        }catch(NumberFormatException ex){
            SocketClient.LOGGER.info("Using default port");
        }
        int finalPort = port;
        String finalIp = ip;
        notifyObserver(obs -> obs.onConnectionRequest(finalIp, finalPort));
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

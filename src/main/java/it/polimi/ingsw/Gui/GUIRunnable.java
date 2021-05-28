package it.polimi.ingsw.Gui;
import it.polimi.ingsw.Scenes.LoginController;
import it.polimi.ingsw.Scenes.MenuController;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.controller.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIRunnable extends Application {


    private static Scene scene;


    /**
     * Returns the active controller.
     *
     * @return active controller.
     */


        @Override
        public void start(Stage Stage) throws Exception{
            Gui gui = new Gui();
            GuiController  guiController = new GuiController(gui);
            gui.addObserver(guiController);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Menu_Scene.fxml"));
            Parent root = loader.load();
            MenuController controller = loader.getController();
            controller.addObserver(guiController);
            Stage.setTitle("Masters Of Renaissance");
            scene = new Scene(root);
            Stage.setScene(scene);
            Stage.setWidth(1280d);
            Stage.setHeight(720d);
            Stage.setResizable(false);
            Stage.setMaximized(true);
            Stage.setFullScreen(true);
            Stage.setFullScreenExitHint("");
            Stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            Stage.show();


        }

        public static void main(String[] args) {
            launch(args);
        }



    public static void changeLogin() {
        try {
        FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Username_Scene.fxml"));
            Parent root = loader.load();


            scene.setRoot(root);


        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
    }

    public static void changetoNumPlayers() {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Number_of_players_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);



        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
    }

}

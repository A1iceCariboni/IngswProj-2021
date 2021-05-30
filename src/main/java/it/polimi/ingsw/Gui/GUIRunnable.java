package it.polimi.ingsw.Gui;
import it.polimi.ingsw.Scenes.*;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.Scenes.AlertSceneController;
import it.polimi.ingsw.controller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class GUIRunnable extends Application {


    private static Scene scene;


    public static Scene getActiveScene() {
        return scene;
    }

    /**
     * Returns the active controller.
     *
     * @return active controller.
     */


        @Override
        public void start(Stage Stage) throws Exception{
            Gui gui = new Gui();
            ClientController clientController = new ClientController(gui);
            gui.addObserver(clientController);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Menu_Scene.fxml"));
            Parent root = loader.load();
            MenuController controller = loader.getController();
            controller.addObserver(clientController);
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



    public static LoginController changeLogin(LoginController l) {
        try {
        FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Username_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            l = loader.getController();
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return l;
    }






    public static NumberOfPlayersController changetoNumPlayers(NumberOfPlayersController n) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Number_of_players_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            n = loader.getController();


        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return n;
    }


    public static BoardController changetoStart(BoardController bc) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Board_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            bc = loader.getController();

        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return bc;
    }


    public static void showAlert(String title, String message) {
        FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Alert_Scene.fxml"));
        Parent parent;
        try {
           parent = loader.load();
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
            return;
        }
        AlertSceneController alertSceneController = loader.getController();
        Scene alertScene = new Scene(parent);
        alertSceneController.setScene(alertScene);
        alertSceneController.setAlertTitle(title);
        alertSceneController.setAlertMessage(message);
        alertSceneController.displayAlert();
    }


    public static ChooseLeaderCardsController discardLeaderCards(ChooseLeaderCardsController cl) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Discard_LeaderCards.fxml"));
            Parent parent = loader.load();
            cl  = loader.getController();
            Scene discLeadCards = new Scene(parent);
            cl.setScene(discLeadCards);
            cl.display();
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
          
        }

        return cl;

    }

}

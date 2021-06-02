package it.polimi.ingsw.Scenes;

import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.observers.ViewObservable;
import it.polimi.ingsw.observers.ViewObserver;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneController extends ViewObservable {


    private static Scene activeScene;
    private static GenericSceneController activeController;




     /** @return active scene.
     */
    public static Scene getActiveScene() {
        return activeScene;
    }

    /**

     * @return active controller.
     */
    public static GenericSceneController getActiveController() {
        return activeController;
    }



    public static <T> T changeScene(ArrayList<ViewObserver> obs, Scene scene, String fxml) {
        T controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            activeController = (GenericSceneController) controller;
            ((ViewObservable) controller).addAllObservers(obs);
            activeScene = scene;
            activeScene.setRoot(root);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return controller;
    }


    public static void changeScene(GenericSceneController controller, String fxml) {
        changeScene(controller, activeScene, fxml);
    }

    public static void changeScene(GenericSceneController controller, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeScene(controller, scene, fxml);
    }


    public static <T> T changeScene(ArrayList<ViewObserver> observers, String fxml) {
        return changeScene(observers, activeScene, fxml);
    }

    public static <T> T changeScene(ArrayList<ViewObserver> observerList, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        return changeScene(observerList, scene, fxml);
    }

    public static void changeScene(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            // Setting the controller BEFORE the load() method.
            loader.setController(controller);
            activeController = controller;
            Parent root = loader.load();

            activeScene = scene;
            activeScene.setRoot(root);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
    }


}

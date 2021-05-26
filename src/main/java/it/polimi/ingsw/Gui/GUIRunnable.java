package it.polimi.ingsw.Gui;
import it.polimi.ingsw.Gui.Gui;
import it.polimi.ingsw.controller.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class GUIRunnable extends Application {

        @Override
        public void start(Stage Stage) throws Exception{
           // Gui gui = new Gui();
           // GuiController  guiController = new GuiController(gui);
           // gui.addObserver(guiController);
           // gui.start();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu_Scene.fxml"));
            Stage.setTitle("Masters Of Renaissance");
            Stage.setScene(new Scene(root));
            Stage.setWidth(1280d);
            Stage.setHeight(720d);
            Stage.setResizable(false);
            Stage.setMaximized(true);
            Stage.setFullScreen(true);
            Stage.setFullScreenExitHint("");
            Stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            Stage.setTitle("Santorini Board Game");
            Stage.show();
        }



        public static void main(String[] args) {
            launch(args);
        }
    }

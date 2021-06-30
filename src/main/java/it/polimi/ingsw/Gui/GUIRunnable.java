package it.polimi.ingsw.Gui;
import it.polimi.ingsw.Scenes.*;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.Scenes.AlertScene;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.observers.ViewObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
            Menu controller = loader.getController();
            controller.addObserver(clientController);
            Stage.setTitle("Masters Of Renaissance");
            scene = new Scene(root);
            Stage.setScene(scene);
            Stage.setWidth(1280d);
            Stage.setHeight(720d);
            Stage.setResizable(false);
        //    Stage.setMaximized(true);
          //  Stage.setFullScreen(true);
         //   Stage.setFullScreenExitHint("");
         //   Stage
            //   .setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            Stage.show();


        }

        public static void main(String[] args) {
            launch(args);
        }



    public static Login changeLogin(Login l) {
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






    public static NumberOfPlayers changetoNumPlayers(NumberOfPlayers n) {
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


    public static Board changetoStart(Board bc, ArrayList<ViewObserver> observers, VirtualModel virtualModel) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Board_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            bc = loader.getController();
            bc.addAllObservers(observers);
            bc.disable();
            bc.payBtn.setVisible(false);
            bc.setVirtualModel(virtualModel);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return bc;
    }

    public static ConnectScene changetoConnect(ConnectScene c, ArrayList<ViewObserver> observers) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Connect_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            c = loader.getController();
            c.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return c;
    }

    public static Board changetoPay(Board bc, ArrayList<ViewObserver> observers, VirtualModel virtualModel) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Board_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            bc = loader.getController();
            bc.addAllObservers(observers);
            bc.disable();
            bc.setPayBtn();
            bc.setVirtualModel(virtualModel);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return bc;
    }


    public static DiscarLeaderCards leaderCardsScene(DiscarLeaderCards f, ArrayList<ViewObserver> observers, VirtualModel virtualModel) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/DiscardLeaderCards_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            f = loader.getController();
            f.addAllObservers(observers);
            f.setLeaderCards(virtualModel);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return f;
    }

    public static DevelopmentMarket buyDevCard(DevelopmentMarket cl, ArrayList<ViewObserver> observers) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/DevelopmentDeck_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            cl  = loader.getController();
            cl.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return cl;
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
        AlertScene alertSceneController = loader.getController();
        Scene alertScene = new Scene(parent);
        alertSceneController.setScene(alertScene);
        alertSceneController.setAlertTitle(title);
        alertSceneController.setAlertMessage(message);
        alertSceneController.displayAlert();
    }


    public static AddtoWarehouse chooseRes(AddtoWarehouse c, ArrayList<ViewObserver> observers, int quantity) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/ChooseResouce_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            c = loader.getController();
            c.addAllObservers(observers);
            c.setQuantity(quantity);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return c;
    }


    public static Market buyMarket(Market m, ArrayList<ViewObserver> observers) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Market_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            m = loader.getController();
            m.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return m;
    }

    public static AddtoWarehouse putInWarehouse(AddtoWarehouse c, ArrayList<ViewObserver> observers, String[] resource, VirtualModel virtualModel) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/ChooseResouce_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            c = loader.getController();
            c.addAllObservers(observers);
            c.setResource(resource, virtualModel);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return c;
    }

    public static AddtoWarehouse payWithRes(AddtoWarehouse c, ArrayList<ViewObserver> observers, ArrayList<String> resource, VirtualModel virtualModel) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/ChooseResouce_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            c = loader.getController();
            c.addAllObservers(observers);
            c.setPay(resource, virtualModel);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return c;
    }


    public static DiscardResource discRes(DiscardResource f, ArrayList<ViewObserver> observers) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/DiscardResource_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            f = loader.getController();
            f.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return f;
    }



    public static Wait waitingscene(Wait m, ArrayList<ViewObserver> observers) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Waiting_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            m = loader.getController();
            m.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return m;
    }


    public static Board slotChoice(Board bc, ArrayList<ViewObserver> observers) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Board_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            bc = loader.getController();
            bc.addAllObservers(observers);
            bc.setButtons();

        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return bc;
    }


    public static RearrangeWarehouse moveFromDepot(RearrangeWarehouse r, ArrayList<ViewObserver> observers) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Rearrange_Warehouse_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            r = loader.getController();
            r.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return r;
    }

    public static OtherBoard otherPlayerBoard (OtherBoard board, ArrayList<ViewObserver> observers) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Other_Board_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            board  = loader.getController();
            board.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return board;
    }

    public static QuestionGame questionGame (QuestionGame game, ArrayList<ViewObserver> observers) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Con_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            game  = loader.getController();
            game.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return game;
    }

    public static NamePlayerPlayerboard namePlayerPlayerboard (NamePlayerPlayerboard namePP, ArrayList<ViewObserver> observers) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Name_PlayerPlayerboard_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            namePP  = loader.getController();
            namePP.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return namePP;
    }

    public static LMfaithTrack lmFaithTrack(LMfaithTrack lm, ArrayList<ViewObserver> observers) {
        try {
            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/Lorenzo_FaithTrack_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            lm  = loader.getController();
            lm.addAllObservers(observers);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return lm;
    }


    public static WhiteMarble whiteScene(WhiteMarble w, ArrayList<ViewObserver> observers, int num, VirtualModel virtualModel) {
        try {

            FXMLLoader loader = new FXMLLoader(GUIRunnable.class.getResource("/fxml/WhiteMarble_Scene.fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
            w = loader.getController();
            w.addAllObservers(observers);
            w.setnumber(num, virtualModel);
        } catch (IOException e) {
            SocketClient.LOGGER.severe(e.getMessage());
        }
        return w;
    }







}

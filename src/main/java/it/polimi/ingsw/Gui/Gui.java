package it.polimi.ingsw.Gui;



import com.google.gson.Gson;
import it.polimi.ingsw.Scenes.*;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Arrays;


/** @author Alessandra Atria
 *  * class that implements the method to answer to the server message in the client controller*/


public class Gui extends ViewObservable implements View {
    private VirtualModel virtualModel;

    Gson gson = new Gson();
    private TurnPhase turnPhase;
    private Scene scene;
    Board bc;
    ArrayList<String> resources = new ArrayList<>();

    public Gui() {
        virtualModel = new VirtualModel();
    }


    @Override
    public void showEndGame() {
        bc = new Board();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel).setDisableAll());
    }

    @Override
    public void askNickname() {
        Login loginController = new Login();
        Platform.runLater(() -> GUIRunnable.changeLogin(loginController).addAllObservers(observers));


    }

    @Override
    public void askNumberOfPlayers() {
        NumberOfPlayers n = new NumberOfPlayers();

        Platform.runLater(() -> GUIRunnable.changetoNumPlayers(n).addAllObservers(observers));
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        Platform.runLater(() -> GUIRunnable.showAlert("Info Message", genericMessage));
    }

    @Override
    public void showBlackCross(int blackCross) {
        turnPhase = TurnPhase.SEE_PLAYERBOARD_SINGLE;
        virtualModel.setBlackCross(blackCross);
    }

    @Override
    public void otherVictoryPointsIn(int victory) {
        virtualModel.setOtherVictoryPoints(victory);
    }

    @Override
    public void dummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards) {
        virtualModel.setLeaderCard(dummyLeaderCards);
    }


    @Override

    public void faithTrackNew(DummyFaithTrack faithTrack) {
        virtualModel.getPlayerBoard().setFaithTrack(faithTrack);
    }


    @Override

    public void marketTrayNew(DummyMarket market) {
        virtualModel.setDummyMarket(market);
    }

    @Override
    public void otherWarehouseNew(DummyWareHouse dummyWareHouse) {
        virtualModel.getOtherPlayer().setWareHouse(dummyWareHouse);
    }

    @Override
    public void otherDummyStrongBox(DummyStrongbox strongBox) {
        virtualModel.getOtherPlayer().setStrongBox(strongBox);
    }

    @Override
    public void otherFaithMarker(int pos) {
        virtualModel.getOtherPlayer().moveFaithMarker(pos);
    }

    @Override
    public void otherDevCards(DummyDev[] cards) {
        virtualModel.getOtherPlayer().setDevSections(cards);
    }

    @Override
    public void otherLeaderCardIn(DummyLeaderCard[] dummyLeaderCards) {
        virtualModel.setOtherCards(dummyLeaderCards);
        turnPhase = TurnPhase.SEE_PLAYERBOARD;
    }


    @Override
    public void devMarketNew(DummyDev[][] devMarket) {
        virtualModel.setBoardDevCard(devMarket);
    }



    @Override
    public void yourTurn() {
        chooseAction();
    }

    @Override
    public void waitTurn(){
        Wait w = new Wait();
        Platform.runLater(() -> GUIRunnable.waitingscene(w, observers));

    }


    @Override
    public void wareHouseNew(DummyWareHouse dummyWareHouse) {
        virtualModel.getPlayerBoard().setWareHouse(dummyWareHouse);
    }

    @Override
    public void chooseAction() {
        if(turnPhase == TurnPhase.SEE_PLAYERBOARD){
            turnPhase = TurnPhase.FREE;
            OtherBoard board = new OtherBoard();
            Platform.runLater(() -> GUIRunnable.otherPlayerBoard(board, observers).setOtherBoard(virtualModel));
        }else {
            if(turnPhase == TurnPhase.SEE_PLAYERBOARD_SINGLE){
                turnPhase = TurnPhase.FREE;
                LMfaithTrack lm = new LMfaithTrack();
                Platform.runLater(() -> GUIRunnable.lmFaithTrack(lm, observers).setBlackCross(virtualModel, virtualModel.getBlackCross()));
            }else {
                turnPhase = TurnPhase.FREE;
                bc = new Board();
                Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers, virtualModel));
            }
        }
    }






    @Override
    public void modifyWarehouse() {

    }


    @Override
    public void chooseResources(int quantity) {
        AddtoWarehouse c = new AddtoWarehouse();
        Platform.runLater(() -> GUIRunnable.chooseRes(c, observers, quantity));


    }

    @Override
    public void victoryPointsIn(int victoryPoints) {
        virtualModel.setVictoryPoints(victoryPoints);
    }

    @Override
    public void addResourceToWareHouse(String[] resource) {
        AddtoWarehouse c = new AddtoWarehouse();
        Platform.runLater(() -> GUIRunnable.putInWarehouse(c, observers, resource, virtualModel));

    }



    public void askWhiteMarble(int num) {
        WhiteMarble w = new WhiteMarble();
        Platform.runLater(() -> GUIRunnable.whiteScene(w, observers, num , virtualModel));

    }

    @Override
    public void activateProduction(String[] toPay) {
        Platform.runLater(() -> GUIRunnable.changetoPay(bc, observers, virtualModel).setPay(toPay));

    }



    @Override
    public void modifyFaithMarker(int pos) {
        virtualModel.getPlayerBoard().setFaithMarker(pos);
    }

    @Override
    public void payResources(String[] resources) {
        System.out.println("paga");
        AddtoWarehouse c = new AddtoWarehouse();
        Platform.runLater(() -> GUIRunnable.payWithRes(c, observers, new ArrayList<>(Arrays.asList(resources)), virtualModel));

    }

    @Override
    public void slotChoice() {
        bc = new Board();
        Platform.runLater(() -> GUIRunnable.slotChoice(bc, observers).setVirtualModel(virtualModel));

    }

    @Override
    public void newDummyStrongBox(DummyStrongbox dummyStrongbox) {
    virtualModel.getPlayerBoard().setStrongBox(dummyStrongbox);
    }

    @Override
    public void addDevCards(DummyDev[] dummyDevs) {
        virtualModel.getPlayerBoard().setDevSections(dummyDevs);

    }


    @Override
    public TurnPhase getTurnPhase() {
        return turnPhase;
    }


    @Override
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }
}
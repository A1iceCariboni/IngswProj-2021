package it.polimi.ingsw.Gui;



import com.google.gson.Gson;
import it.polimi.ingsw.Scenes.LoginController;
import it.polimi.ingsw.Scenes.NumberOfPlayersController;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.scene.Scene;

public class Gui extends ViewObservable implements View {


    Gson gson = new Gson();
    private TurnPhase turnPhase;
    private Scene scene;


    @Override
    public void askNickname() {
        LoginController loginController = new LoginController();
        Platform.runLater(() -> GUIRunnable.changeLogin(loginController).addAllObservers(observers));


    }

    @Override
    public void askNumberOfPlayers() {
        NumberOfPlayersController n = new NumberOfPlayersController();

        Platform.runLater(() -> GUIRunnable.changetoNumPlayers(n).addAllObservers(observers));
    }

    @Override
    public void dummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards) {

    }

    @Override
    public void faithTrackNew(DummyFaithTrack dummyFaithTrack) {

    }

    @Override
    public void devMarketNew(DummyDev[][] dummyDevs) {

    }

    @Override
    public void marketTrayNew(DummyMarket dummyMarket) {

    }

    @Override
    public void yourTurn() {

    }

    @Override
    public void wareHouseNew(DummyWareHouse dummyWareHouse) {

    }

    @Override
    public void chooseAction() {

    }

    @Override
    public void discardResource() {

    }

    @Override
    public void modifyWarehouse() {

    }

    @Override
    public void discardLeader() {

    }

    @Override
    public void chooseResources(int quantity) {

    }

    @Override
    public void addResourceToWareHouse(String[] resource) {

    }

    @Override
    public void activateLeader() {

    }

    @Override
    public void askWhiteMarble(int num) {

    }

    @Override
    public void takeResourcesFromMarket() {

    }

    @Override
    public void buyDevelopmentCard() {

    }

    @Override
    public void activateProduction(String[] toPay) {

    }

    @Override
    public void checkResponse(String message) {

    }

    @Override
    public void modifyFaithMarker(int pos) {

    }

    @Override
    public void payResources(String[] resources) {

    }

    @Override
    public void slotChoice() {

    }

    @Override
    public void newDummyStrongBox(DummyStrongbox dummyStrongbox) {

    }

    @Override
    public void addDevCards(DummyDev[] dummyDevs) {

    }

    @Override
    public void setTurnPhase(TurnPhase turnPhase) {

    }

    @Override
    public TurnPhase getTurnPhase() {
        return null;
    }


}
package it.polimi.ingsw.Gui;



import com.google.gson.Gson;
import it.polimi.ingsw.Scenes.*;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
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


    public void showGenericMessage(String genericMessage) {
        Platform.runLater(() -> GUIRunnable.showAlert("Info Message", genericMessage));
    }



    @Override
    public void dummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards) {
    }

    @Override
    public void faithTrackNew(DummyFaithTrack dummyFaithTrack) {

    }

    @Override
    public void devMarketNew(DummyDev[][] dummyDevs) {
        BoardController bc = new BoardController();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc).addAllObservers(observers));
    }

    @Override
    public void marketTrayNew(DummyMarket dummyMarket) {

    }

    @Override
    public void yourTurn() {
        chooseAction();
    }



    @Override
    public void wareHouseNew(DummyWareHouse dummyWareHouse) {
    }

    @Override
    public void chooseAction() {
        turnPhase = TurnPhase.FREE;
        int choice = 0;
        switch (choice) {
            case 1:
                discardLeader();
                break;
            case 2:
                turnPhase = TurnPhase.ACTIVATE_PRODUCTION;

                activateProduction(new String[0]);
                break;

            case 3:
                activateLeader();
                break;

            case 4:
                turnPhase = TurnPhase.BUY_DEV;
                buyDevelopmentCard();
                break;

            case 5:
                turnPhase = TurnPhase.BUY_MARKET;
                takeResourcesFromMarket();
                break;

            case 6:
                modifyWarehouse();
                break;

            case 7:
                discardResource();
                break;

            case 8:
                turnPhase = TurnPhase.NOT_YOUR_TURN;
                notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.END_TURN, "")));
                break;

        }
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
    public TurnPhase getTurnPhase() {
        return turnPhase;
    }


    @Override
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }
}
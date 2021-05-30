package it.polimi.ingsw.Gui;



import com.google.gson.Gson;
import it.polimi.ingsw.Scenes.BoardController;
import it.polimi.ingsw.Scenes.LoginController;
import it.polimi.ingsw.Scenes.NumberOfPlayersController;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.scene.Scene;

import javax.swing.*;
import java.awt.*;

public class Gui extends ViewObservable implements View {


    Gson gson = new Gson();
    private TurnPhase turnPhase;
    private Scene scene;


    public Gui() {
    }


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
    public void showGenericMessage(String genericMessage) {
        Platform.runLater(() -> GUIRunnable.showAlert("Info Message", genericMessage));
    }



    @Override
    public void dummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards) {


    }


    @Override

    public void faithTrackNew(DummyFaithTrack faithTrack) {

    }


    @Override

    public void marketTrayNew(DummyMarket market) {

    }

    @Override
    public void otherWarehouseNew(DummyWareHouse dummyWareHouse) {

    }

    @Override
    public void otherDummyStrongBox(DummyStrongbox dummyStrongbox) {

    }

    @Override
    public void otherFaithMarker(int pos) {

    }

    @Override
    public void otherDevCards(DummyDev[] dummyDevs) {

    }

    @Override
    public void otherLeaderCardIn(DummyLeaderCard[] dummyLeaderCards) {

    }


    @Override
    public void devMarketNew(DummyDev[][] dummyDevs) {

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
        BoardController bc = new BoardController();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc).addAllObservers(observers));
        turnPhase = TurnPhase.FREE;
        System.out.println("If it's your first round you can:");
        System.out.println("1. Discard a leader card");
        System.out.println("If it's not you can also:");
        System.out.println("2. Activate the production");
        System.out.println("3. Activate a leader card");
        System.out.println("4. Buy a development card");
        System.out.println("5. Take resources from the market");
        System.out.println("6. Rearrange the warehouse");
        System.out.println("7. Discard a resource");
        System.out.println("8. End your turn");
        System.out.println("9. To see someone else's playerboard");
    }




    @Override
    public void discardResource() {

    }

    @Override
    public void modifyWarehouse() {

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
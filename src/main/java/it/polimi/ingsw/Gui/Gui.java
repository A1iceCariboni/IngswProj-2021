package it.polimi.ingsw.Gui;

import com.google.gson.Gson;
import it.polimi.ingsw.Scenes.LoginController;
import it.polimi.ingsw.Scenes.MenuController;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.InputReadTask;
import it.polimi.ingsw.controller.GuiController;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.observers.GuiObservable;
import javafx.application.Platform;
import javafx.scene.Scene;

public class Gui extends GuiObservable{


    Gson gson = new Gson();
    private TurnPhase turnPhase;
    private Scene scene;


    public Gui() {
    }


    public void askNickname() {
      //  GUIRunnable.changeLogin(
        Platform.runLater(() -> GUIRunnable.changeLogin());

    }

    public void askNumberOfPlayers()  {

        Platform.runLater(() -> GUIRunnable.changetoNumPlayers());

    }


    public void DummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards) {

    }

    public void faithTrackNew(DummyFaithTrack faithTrack) {

    }


    public void devMarketNew(DummyDev[][] devMarket) {

    }


    public void marketTrayNew(DummyMarket market) {

    }


    public void yourTurn() {

    }


    public void wareHouseNew(DummyWareHouse dummyWareHouse) {

    }


    public void chooseAction() {

    }


    public void discardResource() {

    }


    public void modifyWarehouse() {

    }


    public void discardLeader() {

    }


    public void chooseResources(int quantity) {

    }


    public void addResourceToWareHouse(String[] resource) {

    }


    public void activateLeader() {

    }


    public void askWhiteMarble(int num) {

    }


    public void takeResourcesFromMarket() {

    }


    public void buyDevelopmentCard() {

    }


    public void activateProduction(String[] toPay) {

    }


    public String readYN() {
        return null;
    }


    public int readInt(int max_value, int min_value, String question) {
        return 0;
    }


    public int readAnyInt(String question) {
        return 0;
    }


    public String readResource() {
        return null;
    }


    public void checkResponse(String name) {

    }


    public void modifyFaithMarker(int pos) {

    }


    public void payResources(String[] resource) {

    }


    public void slotChoice() {

    }


    public int readDepotId() {
        return 0;
    }


    public TurnPhase getTurnPhase() {
        return null;
    }


    public void newDummyStrongBox(DummyStrongbox strongBox) {

    }


    public void addDevCards(DummyDev[] cards) {

    }


    public void waitTurn() {

    }


    public void setTurnPhase(TurnPhase turnPhase) {

    }


    public String readLineWithTimeout() {
        return null;
    }
}

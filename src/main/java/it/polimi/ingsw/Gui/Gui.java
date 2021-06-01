package it.polimi.ingsw.Gui;



import com.google.gson.Gson;
import it.polimi.ingsw.Scenes.*;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.scene.Scene;

public class Gui extends ViewObservable implements View {
    private VirtualModel virtualModel;

    Gson gson = new Gson();
    private TurnPhase turnPhase;
    private Scene scene;


    public Gui() {
        virtualModel = new VirtualModel();
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
    public void devMarketNew(DummyDev[][] devMarket) {
        virtualModel.setBoardDevCard(devMarket);
    }



    @Override
    public void yourTurn() {
        chooseAction();
    }

    @Override
    public void waitTurn(){

    }


    @Override
    public void wareHouseNew(DummyWareHouse dummyWareHouse) {
        virtualModel.getPlayerBoard().setWareHouse(dummyWareHouse);
    }

    @Override
    public void chooseAction() {
        turnPhase = TurnPhase.FREE;
        Board bc = new Board();
        bc.addAllObservers(observers);
        Platform.runLater(() -> GUIRunnable.changetoStart(bc).setLeaderCards(virtualModel.getLeaderCards()));
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
package it.polimi.ingsw.Gui;

import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.observers.GuiObservable;

public class Gui extends GuiObservable{


    public void start() {
        int port = Constants.DEFAULT_PORT;
        String ip = Constants.LOCAL_HOST;
        notifyObserver(obs -> obs.onConnectionRequest(ip,port));

    }


    public String readLine() {
        return null;
    }


    public void askNickname() {

    }

    public void askNumberOfPlayers() {

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

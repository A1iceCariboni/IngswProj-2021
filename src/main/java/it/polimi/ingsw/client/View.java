package it.polimi.ingsw.client;

import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.enumerations.TurnPhase;

public interface View {



     void askNickname();

     void askNumberOfPlayers();

     void dummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards);

     void faithTrackNew(DummyFaithTrack dummyFaithTrack);

     void devMarketNew(DummyDev[][] dummyDevs);

     void marketTrayNew(DummyMarket dummyMarket);

     void otherWarehouseNew(DummyWareHouse dummyWareHouse);

     void otherDummyStrongBox(DummyStrongbox dummyStrongbox);

     void otherFaithMarker(int pos);

     void otherDevCards(DummyDev[] dummyDevs);

     void otherLeaderCardIn(DummyLeaderCard[] dummyLeaderCards);

     void yourTurn();

     void wareHouseNew(DummyWareHouse dummyWareHouse);

     void chooseAction();

     void discardResource();

     void modifyWarehouse();

    // void discardLeader();

     void chooseResources(int quantity);

     void addResourceToWareHouse(String[] resource);

     void activateLeader();

     void askWhiteMarble(int num);

     void takeResourcesFromMarket();

     void buyDevelopmentCard();

     void activateProduction(String[] toPay);

     void checkResponse(String message);

     void modifyFaithMarker(int pos);

     void payResources(String[] resources);

     void slotChoice();

     void newDummyStrongBox(DummyStrongbox dummyStrongbox);

     void addDevCards(DummyDev[]dummyDevs);

     void setTurnPhase(TurnPhase turnPhase);

     TurnPhase getTurnPhase();

     void showGenericMessage(String message);




}

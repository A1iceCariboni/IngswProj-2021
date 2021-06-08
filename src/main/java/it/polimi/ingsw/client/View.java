package it.polimi.ingsw.client;

import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.enumerations.TurnPhase;

/** common interface between gui and cli*/
public interface View {


     /** asks the nickname to the player*/
     void askNickname();


     /** asks the nickname to the player number of players to the first player*/
     void askNumberOfPlayers();

     /**
      * adds the leader card to the board of the player
      * @param dummyLeaderCards cards of the player
      */
     void dummyLeaderCardIn(DummyLeaderCard[] dummyLeaderCards);



     /**
      * modifies the dummy faith track
      * @param dummyFaithTrack new faith track
      */
     void faithTrackNew(DummyFaithTrack dummyFaithTrack);


     /**
      * modifies the dummy market of the development cards
      * @param dummyDevs new development cards market
      */
     void devMarketNew(DummyDev[][] dummyDevs);


     /**
      * modifies the dummy market tray
      * @param market new market
      */
     void marketTrayNew(DummyMarket market);

     void otherWarehouseNew(DummyWareHouse dummyWareHouse);

     void otherDummyStrongBox(DummyStrongbox dummyStrongbox);

     void otherFaithMarker(int pos);

     void otherDevCards(DummyDev[] dummyDevs);

     void otherLeaderCardIn(DummyLeaderCard[] dummyLeaderCards);


     /**
      * asks to the player which operations wants to do during the turn and to end the turn
      */
     void yourTurn();

     /** modifies the warehouse
      * @param dummyWareHouse is new warehouse
      */
     void wareHouseNew(DummyWareHouse dummyWareHouse);

     /** method to let the player decide witch action he wants to make*/
     void chooseAction();

     /** method to rearrange the resources of the warehouse*/
     void modifyWarehouse();



     void waitTurn();



     /**
      * asks the resources and notifies the server
      */
     void chooseResources(int quantity);

     void showVictoryPoints(int victoryPoints);

     /**
      * adds a resource where the player wants to add it
      * adds the resources to the dummy warehouse
      * notifies the server about the choice and sends -1 if the resource must be discarded
      */
     void addResourceToWareHouse(String[] resource);


     void askWhiteMarble(int num);


     void activateProduction(String[] toPay);

  //   void checkResponse(String message);

     void modifyFaithMarker(int pos);

     void payResources(String[] resources);


     /** asks the player where he wants to put the development cards*/
     void slotChoice();



     /**
      * sets the dummy strong box in the virtual model
      * @param strongBox the new dummy strongbox
      */
     void newDummyStrongBox(DummyStrongbox strongBox);


     /**
      * adds the development cards in the dummy development section
      * @param cards
      */
     void addDevCards(DummyDev[] cards);

     void setTurnPhase(TurnPhase turnPhase);

     TurnPhase getTurnPhase();


     void showGenericMessage(String message);


    void showBlackCross(int blackCross);
}

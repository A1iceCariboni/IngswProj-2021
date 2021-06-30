package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.GamePhase;
import it.polimi.ingsw.enumerations.MarbleColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FakeClientHandler extends ClientHandler{


    @Override
  public void sendMessage(Message message){

  }
  @Override
  public synchronized void readFromClient(){

  }

}


class MultiGameControllerTest {
  static GameController gameController;
  static ClientHandler clientHandler;


  @BeforeEach
   void init(){
      gameController = new MultiGameController();
      gameController.addPlayer("Alice");
      gameController.addPlayer("Sofia");
      gameController.addPlayer("Alessandra");
      clientHandler = new FakeClientHandler();
      gameController.addConnectedClient("Alice", new FakeVirtualView("Alice"));
      gameController.addConnectedClient("Sofia", new FakeVirtualView("Sofia"));
      gameController.addConnectedClient("Alessandra", new FakeVirtualView("Alessandra"));
      assertEquals(gameController.getConnectedClients().size(), 3 );
      assertEquals(gameController.getDisconnectedClients().size(), 0);

      gameController.startGame();
      gameController.getTurnController().changeGamePhase();
      assertTrue(gameController.isStarted());
      assertEquals(gameController.getGamePhase(), GamePhase.FIRST_ROUND);
  }




    @Test
    void startGame() {
     assertEquals(gameController.getGame().getPlayers().size(),3);
     assertEquals(gameController.getGame().getPlayers().get(0).getNickName(),"Alice");
     assertEquals(gameController.getGame().getPlayers().get(1).getNickName(),"Sofia");
     assertEquals(gameController.getGame().getPlayers().get(2).getNickName(),"Alessandra");
     assertEquals(gameController.getGame().getPlayers().get(0).getActiveLeaderCards().size(),0);
     assertEquals(gameController.getGame().getPlayers().get(1).getActiveLeaderCards().size(),0);
     assertEquals(gameController.getGame().getPlayers().get(2).getActiveLeaderCards().size(),0);
     assertEquals(gameController.getGame().getPlayers().get(0).getLeadercards().size(),4);
     assertEquals(gameController.getGame().getPlayers().get(1).getLeadercards().size(),4);
     assertEquals(gameController.getGame().getPlayers().get(2).getLeadercards().size(),4);
  }

    @Test
    void activateLeaderCard() {
        gameController.activateLeaderCard(0);
        assertEquals(gameController.getGame().getCurrentPlayer().getActiveLeaderCards().size(),0);
    }

    @Test
    void discardLeaderCards() {
    Player player = gameController.getGame().getCurrentPlayer();
    int id = gameController.getGame().getCurrentPlayer().getLeadercards().get(0).getId();
    gameController.discardLeaderCard(id);
    assertEquals(player.getLeadercards().size(),3);
    gameController.setGamePhase(GamePhase.IN_GAME);
    assertEquals(gameController.getGamePhase(), GamePhase.IN_GAME);

    int pos = player.getPlayerBoard().getFaithMarker();
    id = gameController.getGame().getCurrentPlayer().getLeadercards().get(0).getId();

    gameController.discardLeaderCard(id);
    id = gameController.getGame().getCurrentPlayer().getLeadercards().get(0).getId();
    gameController.discardLeaderCard(id);

    assertEquals(player.getLeadercards().size(),1);
    assertEquals(player.getPlayerBoard().getFaithMarker(), pos+1);
    }



    @Test
    void putResource() throws NotPossibleToAdd {
    ArrayList<Resource> resources = new ArrayList<>();
    resources.add(new Resource(ResourceType.SHIELD));
    resources.add(new Resource(ResourceType.COIN));

    Player player = gameController.getGame().getCurrentPlayer();
    gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(resources.get(0));
    gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(resources.get(1));

    gameController.putResource(new int []{1,2});
    assertEquals(player.getPlayerBoard().getResources().size(),62);

    gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(resources.get(0));
    gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(resources.get(1));

    assertThrows(NotPossibleToAdd.class, () -> gameController.putResource(new int[]{1,1}));

    gameController.putResource(new int[]{-1,-1});
    for(Player p: gameController.getGame().getPlayers()){
        if(!p.equals(gameController.getGame().getCurrentPlayer())){
            assertNotEquals(p.getPlayerBoard().getFaithMarker(), 0);
        }
    }

    }
    @Test
    void buyCard() throws EmptyDeck {
      gameController.turnPhase = TurnPhase.BUY_DEV;
      ArrayList<Resource> res = gameController.getGame().getDeckDevelopment()[0][0].getCard().getCost();
      for(Resource resource: res) {
          gameController.getGame().getCurrentPlayer().getPlayerBoard().getStrongBox().addResources(resource);
      }
      assertEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment(),null);

      gameController.buyDevelopment(0, 0);
      assertNotEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment(),null);
      int[] id = new int[res.size()];
      for(int j = 0; j< res.size(); j++){
          id[j] = -1;
      }
      gameController.pay(id);
      assertNotEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment(),null);
      assertFalse(gameController.getGame().getCurrentPlayer().getPlayerBoard().getStrongBox().getRes().isEmpty());
      gameController.placeCard(0);
      assertEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedDevelopment(),null);
  }

  @Test
    void getFromMarket(){
      Player player = gameController.getGame().getCurrentPlayer();
      VirtualView vv = gameController.getConnectedClients().get(gameController.getTurnController().getActivePlayer());

      gameController.getFromMarketRow(2);

      gameController.getFromMarketCol(3);

      assertFalse(player.getPlayerBoard().getUnplacedResources().isEmpty());
      assertTrue(vv.getFreeMarble().isEmpty());


      gameController.getGame().getCurrentPlayer().addPossibleWhiteMarbles(new Resource(ResourceType.SHIELD));
      gameController.getGame().getCurrentPlayer().addPossibleWhiteMarbles(new Resource(ResourceType.SERVANT));

      gameController.getFromMarketRow(2);
      gameController.getFromMarketCol(3);


      assertFalse(player.getPlayerBoard().getUnplacedResources().isEmpty());

  }

  @Test
    void whiteMarbles(){
      MarbleEffect marbleEffect = playerBoard -> {
      };
      ArrayList<Marble> marbles = new ArrayList<>();
      marbles.add(new Marble(MarbleColor.WHITE,marbleEffect));
      marbles.add(new Marble(MarbleColor.WHITE,marbleEffect));
      gameController.getGame().getCurrentPlayer().addPossibleWhiteMarbles(new Resource(ResourceType.SHIELD));
      gameController.getGame().getCurrentPlayer().addPossibleWhiteMarbles(new Resource(ResourceType.SERVANT));

      gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).addAllFreeMarbles(marbles);
      gameController.chooseWhiteMarbleEffect(new String[]{"SHIELD","SERVANT"});
      assertEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(0),new Resource(ResourceType.SHIELD));
      assertEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(1),new Resource(ResourceType.SERVANT));

  }

  @Test
    void activateProdRegular() throws CannotAdd {
      try {
          gameController.getGame().getCurrentPlayer().getPlayerBoard().addDevCard(gameController.getGame().getDeckDevelopment()[0][0].getCard(),0);
      } catch (EmptyDeck emptyDeck) {
          emptyDeck.printStackTrace();
      }
      int[] id = new int[]{gameController.getGame().getCurrentPlayer().getPlayerBoard().getDevelopmentCards().get(0).getId()};
      gameController.addProductionPower(id);
      assertFalse(gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).getCardsToActivate().isEmpty());
  }

@Test
  public void removeResource() throws NotPossibleToAdd {
      Player player = gameController.getGame().getCurrentPlayer();
      gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(new Resource(ResourceType.SERVANT));
      gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(new Resource(ResourceType.SHIELD));

      gameController.putResource(new int []{1,2});
      gameController.removeResource(1);
      assertEquals(61, player.getPlayerBoard().getResources().size());
     for(Player p: gameController.getGame().getPlayers()){
        if(!p.equals(gameController.getGame().getCurrentPlayer())){
            assertNotEquals(p.getPlayerBoard().getFaithMarker(), 0);
        }
     }
     gameController.removeResource(1);
    assertEquals(61, player.getPlayerBoard().getResources().size());
  }


  @Test
    public void startProduction() throws EmptyDeck, CannotAdd, NullCardException {
      Player player = gameController.getGame().getCurrentPlayer();
      VirtualView vv = gameController.getConnectedClients().get(gameController.getTurnController().getActivePlayer());
      player.getPlayerBoard().addDevCard(gameController.getGame().getDeckDevelopment()[0][0].getCard(), 0);
      player.getPlayerBoard().addDevCard(gameController.getGame().getDeckDevelopment()[0][0].getCard(), 1);
      int id1 = player.getPlayerBoard().getDevCardSlots()[0].getId();
      int id2 = player.getPlayerBoard().getDevCardSlots()[1].getId();
      int i = 0;
      while(gameController.getGame().getDeckLeader().getCardDeck().get(i).getLeaderEffect().getEffectName() != "EXTRA_PRODUCTION"){
          i++;
      }
      LeaderCard lc = gameController.getGame().getDeckLeader().getCardDeck().get(i);
      player.addLeaderCard(lc);
      player.activateLeader(lc, player.getPlayerBoard(), player);
      gameController.addProductionPower(new int[]{id1, id2});
      assertTrue(vv.getCardsToActivate().contains(id1));
      assertTrue(vv.getCardsToActivate().contains(id2));
      assertTrue(vv.getExtraProductionToActivate().isEmpty());
      assertTrue(!vv.getResourcesToProduce().contains(new Resource(ResourceType.SERVANT)));
      assertTrue(vv.getResourcesToProduce().isEmpty());
      gameController.addExtraProductionPower(lc.getId(), new Resource(ResourceType.SERVANT));
      assertTrue(!vv.getExtraProductionToActivate().isEmpty());
      assertTrue(vv.getResourcesToProduce().contains(new Resource(ResourceType.SERVANT)));
      assertTrue(!vv.getResourcesToProduce().isEmpty());

      gameController.addBasicProduction(new Resource(ResourceType.COIN), new Resource(ResourceType.COIN), new Resource(ResourceType.SHIELD));
      gameController.addBasicProduction(new Resource(ResourceType.COIN), new Resource(ResourceType.COIN), new Resource(ResourceType.SHIELD));

      assertTrue(vv.getResourcesToPay().contains(new Resource(ResourceType.COIN)));
      assertTrue(vv.getBasicProd().equals(new Resource(ResourceType.SHIELD)));

      gameController.startProduction();
      assertTrue(vv.getCardsToActivate().isEmpty());
      assertTrue(vv.getResourcesToProduce().isEmpty());
      assertTrue(vv.getExtraProductionToActivate().isEmpty());
      assertEquals(vv.getBasicProd(), null);




  }

  @Test
    public void changeDepotsState() throws NotPossibleToAdd {
      Player player = gameController.getGame().getCurrentPlayer();

      Depot[] depots = new Depot[5];
      depots[0] = new Depot(1, 1, new ArrayList<>() );
      ArrayList<Resource> res = new ArrayList<>();
      res.add(new Resource(ResourceType.COIN));
      res.add(new Resource(ResourceType.COIN));
      depots[1] = new Depot (2, 2, res);
      depots[2] = new Depot(3,3, new ArrayList<>());
      depots[3] = new ExtraDepot(-1, -1, ResourceType.NONE);
      depots[4] = new ExtraDepot(-1, -1, ResourceType.NONE);
      gameController.changeDepotsState(depots);
      assertEquals(player.getPlayerBoard().getWareHouse().getDepots().size(), 5);
      assertEquals(player.getPlayerBoard().getWareHouse().getResources().size(), 2);
      assertTrue(player.getPlayerBoard().getWareHouse().getResources().contains(new Resource(ResourceType.COIN)));


  }

  @Test
    public void pay() throws EmptyDeck {
      gameController.setTurnPhase(TurnPhase.BUY_DEV);
      Player player = gameController.getGame().getCurrentPlayer();
      VirtualView vv = gameController.getConnectedClients().get(gameController.getTurnController().getActivePlayer());

      DevelopmentCard dc = gameController.getGame().getDeckDevelopment()[0][0].getCard();
      player.getPlayerBoard().setUnplacedDevelopment(dc);
      int dim = player.getPlayerBoard().getResources().size();
      int c = dc.getCost().size();
      int[] ids = new int[c];
      for(int i = 0; i< c; i++){
          ids[i] = - 1 ;
      }
      gameController.pay(ids);
      assertEquals(player.getPlayerBoard().getResources().size(), dim-c);

      gameController.setTurnPhase(TurnPhase.ACTIVATE_PRODUCTION);
      ArrayList<Resource> toPay = new ArrayList<>();
      toPay.add(new Resource(ResourceType.COIN));
      toPay.add(new Resource(ResourceType.COIN));
      toPay.add(new Resource(ResourceType.STONE));
      toPay.add(new Resource(ResourceType.STONE));
      dim = player.getPlayerBoard().getResources().size();

      vv.addAllResourcesToPay(toPay);
      gameController.pay(new int[]{-1,-1,-1,-1});
      assertEquals(player.getPlayerBoard().getResources().size(), dim-4);

  }

  @Test
    void singleGame() throws InvalidNickname, NotPossibleToAdd {
      gameController = new SingleGameController();
      gameController.addConnectedClient("Alice", new FakeVirtualView("Alice"));
      gameController.addPlayer("Alice");
      gameController.startGame();
      Player p = gameController.getGame().getCurrentPlayer();

      assertEquals(gameController.getGame().getPlayers().size(), 1);
      assertDoesNotThrow(() -> gameController.getGame().getFakePlayer());

      p.getPlayerBoard().getWareHouse().addToDepot(new Resource(ResourceType.SERVANT), new Depot(2, 2 , new ArrayList<>()));
      gameController.removeResource(2);
      assertEquals(gameController.getGame().getFakePlayer().getBlackCross(), 1);

      p.getPlayerBoard().addUnplacedResource(new Resource(ResourceType.COIN));
      p.getPlayerBoard().addUnplacedResource(new Resource(ResourceType.COIN));

      gameController.putResource(new int[]{-1, 2});
      assertEquals(gameController.getGame().getFakePlayer().getBlackCross(), 2);
      assertEquals(p.getPlayerBoard().getWareHouse().getResources().size(), 1);




  }
}

class FakeVirtualView extends VirtualView {
    private String nickname;
    private final ArrayList<Resource> freeResources;
    private ArrayList<Marble> freeMarble;
    private final ArrayList<DevelopmentCard> freeDevelopment;
    private ArrayList<Depot> tempDepots = new ArrayList<>();
    private StrongBox tempStrongBox = new StrongBox();
    private final ArrayList<Resource> resourcesToPay;
    private final ArrayList<Integer> cardsToActivate;
    private final ArrayList<Integer> extraProductionToActivate;
    private final ArrayList<Resource> resourcesToProduce;
    private Resource basicProd;


    public FakeVirtualView(String nickname) {
        this.nickname = nickname;
        freeResources = new ArrayList<>();
        freeDevelopment = new ArrayList<>();
        freeMarble = new ArrayList<>();
        this.resourcesToPay = new ArrayList<>();
        this.cardsToActivate = new ArrayList<>();
        this.extraProductionToActivate = new ArrayList<>();
        this.resourcesToProduce = new ArrayList<>();
    }


    public void addFreeResource(Resource resource) {
        this.freeResources.add(resource);
    }

    @Override
    public void update(Message message) {

    }

    public void removeFreeResources(int pos) {
        freeResources.remove(pos);
    }

    public ArrayList<Resource> getFreeResources() {
        return freeResources;
    }

    public ArrayList<Marble> getFreeMarble() {
        return freeMarble;
    }

    public ArrayList<DevelopmentCard> getFreeDevelopment() {
        return freeDevelopment;
    }

    public String getNickname(){
        return nickname;
    }
    public void sendInvalidActionMessage() {

    }

    public void addFreeMarble(ArrayList<Marble> freeMarble) {
        this.freeMarble = freeMarble;
    }

    public void addFreeDevelopment(DevelopmentCard freeDevelopment) {
        this.freeDevelopment.add(freeDevelopment);
    }

    public void addAllFreeMarbles(ArrayList<Marble> marbles) {
        this.freeMarble.addAll(marbles);
    }

    public void removeAllFreeMarbles() {
        this.freeMarble.clear();
    }

    public void removeFreeDevelopment(int pos) {
        this.freeDevelopment.remove(pos);
    }

    public ArrayList<Depot> getTempDepots() {
        return tempDepots;
    }

    public void setTempDepots(ArrayList<Depot> tempDepots) {
        this.tempDepots = tempDepots;
    }

    public void freeTempDepots() {
        this.tempDepots.clear();
    }

    public StrongBox getTempStrongBox() {
        return tempStrongBox;
    }

    public void freeStrongBox() {
        this.tempStrongBox.removeAllResources();
    }

    public void setTempStrongBox(StrongBox tempStrongBox) {
        this.tempStrongBox = tempStrongBox;
    }

    public void addCardToActivate(int id) {
        this.cardsToActivate.add(id);
    }

    public void addAllResourcesToPay(ArrayList<Resource> resources) {
        this.resourcesToPay.addAll(resources);
    }

    public void removeCardsToActivate() {
        this.cardsToActivate.clear();
    }

    public void removeResourcesToPay() {
        this.resourcesToPay.clear();
    }

    public ArrayList<Resource> getResourcesToPay() {
        return resourcesToPay;
    }

    public ArrayList<Integer> getCardsToActivate() {
        return cardsToActivate;
    }

    public void addExtraProductionToActivate(int id) {
        this.extraProductionToActivate.add(id);
    }

    public void removeAllExtraProduction() {
        this.extraProductionToActivate.clear();
    }

    public void addResourceToProduce(Resource resource) {
        this.resourcesToProduce.add(resource);
    }

    public void removeAllResourcesToProduce() {
        this.resourcesToProduce.clear();
    }

    public Resource getBasicProd() {
        return basicProd;
    }

    public void setBasicProd(Resource basicProd) {
        this.basicProd = basicProd;
    }

    public ArrayList<Integer> getExtraProductionToActivate() {
        return extraProductionToActivate;
    }

    public ArrayList<Resource> getResourcesToProduce() {
        return resourcesToProduce;
    }

    public void removeResourceToProduce(int pos) {
        this.resourcesToProduce.remove(pos);
    }

    public void close(){}
}
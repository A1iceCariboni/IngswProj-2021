package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.enumerations.MarbleColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
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
  static GameController gameController = new MultiGameController();
  static ClientHandler clientHandler;
  @BeforeAll
  static void init(){
      gameController.addPlayer("Alice");
      gameController.addPlayer("Sofia");
      gameController.addPlayer("Alessandra");
      clientHandler = new FakeClientHandler();
      FakeVirtualView virtualView = new FakeVirtualView();
      gameController.addConnectedClient("Alice", virtualView);
      gameController.addConnectedClient("Sofia", virtualView);
      gameController.addConnectedClient("Alessandra", virtualView);
  }

  @BeforeEach
  void start(){
      gameController.startGame();
      gameController.getTurnController().changeGamePhase();

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
    }

    @Test
    void discardLeaderCards() throws NullCardException {
    Player player = gameController.getGame().getCurrentPlayer();
    int id = gameController.getGame().getCurrentPlayer().getLeadercards().get(0).getId();
    gameController.discardLeaderCards(new int[] {id});
    assertEquals(player.getLeadercards().size(),3);
    }

    @Test
    void placeResources() {

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
    assertEquals(player.getPlayerBoard().getResources().size(),2);

    gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(resources.get(0));
    gameController.getGame().getCurrentPlayer().getPlayerBoard().addUnplacedResource(resources.get(1));

   assertThrows(NotPossibleToAdd.class, () -> gameController.putResource(new int[]{1,1}));

    }
    @Test
    void buyCard(){
      gameController.turnPhase = TurnPhase.BUY_DEV;
      ArrayList<Resource> res = gameController.getGame().getDeckDevelopment()[0][0].getCard().getCost();
      for(Resource resource: res) {
          gameController.getGame().getCurrentPlayer().getPlayerBoard().getStrongBox().addResources(resource);
      }
      assertEquals(gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).getFreeDevelopment().size(),0);

      gameController.buyDevelopment(0, 0);
      assertEquals(gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).getFreeDevelopment().size(),1);
      int[] id = new int[res.size()];
      for(int j = 0; j< res.size(); j++){
          id[j] = -1;
      }
      gameController.pay(id);
      assertEquals(gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).getFreeDevelopment().size(),1);
      assertTrue(gameController.getGame().getCurrentPlayer().getPlayerBoard().getStrongBox().getRes().isEmpty());
      gameController.placeCard(0);
      assertEquals(gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).getFreeDevelopment().size(),0);
  }

  @Test
    void getFromMarket(){
      gameController.turnPhase = TurnPhase.BUY_MARKET;
      gameController.getFromMarketRow(2);
      assertFalse(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().isEmpty());
      assertTrue(gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).getFreeMarble().isEmpty());
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
      gameController.chooseWhiteMarbleEffect(new int[]{0,1});
      assertEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(0),new Resource(ResourceType.SHIELD));
      assertEquals(gameController.getGame().getCurrentPlayer().getPlayerBoard().getUnplacedResources().get(1),new Resource(ResourceType.SERVANT));

  }

  @Test
    void activateProdRegular() throws CannotAdd {
      gameController.getGame().getCurrentPlayer().getPlayerBoard().addDevCard(gameController.getGame().getDeckDevelopment()[0][0].getCard(),0);
      int[] id = new int[]{gameController.getGame().getCurrentPlayer().getPlayerBoard().getDevelopmentCards().get(0).getId()};
      gameController.addProductionPower(id);
      assertFalse(gameController.getConnectedClients().get(gameController.getGame().getCurrentPlayer().getNickName()).getCardsToActivate().isEmpty());
  }
}

class FakeVirtualView extends VirtualView {
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


    public FakeVirtualView() {
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
        return "Ciao";
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
}
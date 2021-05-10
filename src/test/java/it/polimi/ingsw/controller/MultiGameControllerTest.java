package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketServer;
import it.polimi.ingsw.server.VirtualView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.Socket;
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

class FakeVirtualView extends VirtualView{
  private ArrayList<Resource> freeResource = new ArrayList<>();
  @Override
  public void update(Message message){

  }
  @Override
  public void removeFreeResources(int pos){
    freeResource.remove(pos);
  }

  @Override
  public ArrayList<Resource> getFreeResources() {
    return freeResource;
  }
  @Override
  public void addFreeReource(Resource resource){
    this.freeResource.add(resource);
  }

  public void setFreeResource(ArrayList<Resource> freeResource) {
    this.freeResource = freeResource;
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
    @Test
    void startGame() {
     gameController.startGame();
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
    gameController.getConnectedClients().get(player.getNickName()).addFreeReource(resources.get(0));
    gameController.getConnectedClients().get(player.getNickName()).addFreeReource(resources.get(1));
    gameController.putResource(1);
    assertEquals(player.getPlayerBoard().getResources().size(),1);
    gameController.getConnectedClients().get(player.getNickName()).removeFreeResources(0);
    gameController.putResource(2);
    assertEquals(player.getPlayerBoard().getResources().size(),2);

   assertThrows(NotPossibleToAdd.class, () -> gameController.putResource(1));

    }
}
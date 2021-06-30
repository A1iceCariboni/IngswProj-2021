package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.EmptyDeck;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.VirtualView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InputCheckerTest {

    private static GameController gameController;
    private static InputChecker inputChecker;

    @BeforeEach
     void init(){
        gameController = new SingleGameController();
        gameController.addConnectedClient("Alice", new FakeVirtualView("Alice"));
        gameController.addPlayer("Alice");
        gameController.startGame();
        inputChecker = new InputChecker(gameController, gameController.getGame());
    }

    @Test
    void checkPayment() throws EmptyDeck {
        Player p = gameController.getGame().getCurrentPlayer();
        gameController.setTurnPhase(TurnPhase.BUY_DEV);
        DevelopmentCard dc = gameController.getGame().getDeckDevelopment()[0][0].getCard();
        p.getPlayerBoard().setUnplacedDevelopment(dc);
        int[] ids = new int[dc.getCost().size()];
        for(int i = 0; i < dc.getCost().size(); i++){
            ids[i] = -1;
        }
        assertTrue(inputChecker.checkPayment(ids, p.getNickName()));

        ids[0] = 2;
        assertFalse(inputChecker.checkPayment(ids, p.getNickName()));
        assertFalse(inputChecker.checkPayment(new int[]{}, p.getNickName()));

    }

    @Test
    void checkDepotsState() throws NotPossibleToAdd {
        Player p = gameController.getGame().getCurrentPlayer();

        Depot[] depots = new Depot[5];
        depots[0] = new Depot(1, 1, new ArrayList<>() );
        ArrayList<Resource> res = new ArrayList<>();
        res.add(new Resource(ResourceType.COIN));
        res.add(new Resource(ResourceType.COIN));
        depots[1] = new Depot (2, 2, res);
        depots[2] = new Depot(3,3, new ArrayList<>());
        depots[3] = new ExtraDepot(2, 4, ResourceType.COIN);
        depots[4] = new ExtraDepot(-1, -1, ResourceType.NONE);
        assertFalse(inputChecker.checkDepotsState(p.getNickName(), depots));


        p.getPlayerBoard().getWareHouse().addToDepot(new Resource(ResourceType.COIN), new Depot(3, 3 , new ArrayList<>()));

        depots = new Depot[5];
        depots[0] = new Depot(1, 1, new ArrayList<>() );
        res = new ArrayList<>();
        res.add(new Resource(ResourceType.COIN));
        depots[1] = new Depot (2, 2, res);
        depots[2] = new Depot(3,3, new ArrayList<>());
        depots[3] = new ExtraDepot(-1, -1, ResourceType.NONE);
        depots[4] = new ExtraDepot(-1, -1, ResourceType.NONE);
        assertTrue(inputChecker.checkDepotsState(p.getNickName(), depots));
    }

    @Test
    void validDepots()  {

    }

    @Test
    void checkIdDev() throws EmptyDeck, CannotAdd {
        Player player = gameController.getGame().getCurrentPlayer();
        VirtualView vv = gameController.getConnectedClients().get(gameController.getTurnController().getActivePlayer());

        DevelopmentCard dc = gameController.getGame().getDeckDevelopment()[0][0].getCard();
        player.getPlayerBoard().addDevCard(dc, 1);
        assertTrue(inputChecker.checkIdDev(new int[]{dc.getId()}));
        assertFalse(inputChecker.checkIdDev(new int[]{-1}));
        vv.addCardToActivate(dc.getId());
        assertFalse(inputChecker.checkIdDev(new int[]{dc.getId()}));
    }

    @Test
    void checkIdDepot() {
        assertTrue(inputChecker.checkIdDepot(-1));
        assertTrue(inputChecker.checkIdDepot(1));
        assertFalse(inputChecker.checkIdDepot(4));
        gameController.getGame().getCurrentPlayer().getPlayerBoard().getWareHouse().setExtraDepot(new ExtraDepot(2,4, ResourceType.SERVANT));
        assertTrue(inputChecker.checkIdDepot(4));
    }

    @Test
    void checkIdExtraProduction() throws NullCardException {
        Player pl = gameController.getGame().getCurrentPlayer();
        VirtualView vv = gameController.getConnectedClients().get(gameController.getTurnController().getActivePlayer());


        assertFalse(inputChecker.checkIdExtraProduction(new int[]{-1,-2}));
        ArrayList<LeaderCard> lcs = new ArrayList<>();
        for(LeaderCard lc: gameController.getGame().getDeckLeader().getCardDeck()){
            if(lc.getLeaderEffect().getEffectName().equals("EXTRA_PRODUCTION")){
                pl.addLeaderCard(lc);
                lcs.add(lc);
            }
        }
        int[] ids = new int[lcs.size()];
        for(int i = 0; i < lcs.size(); i++){
            ids[i] = lcs.get(i).getId();
        }
        assertFalse(inputChecker.checkIdExtraProduction(ids));
        for(LeaderCard lc: lcs){
            pl.activateLeader(lc, pl.getPlayerBoard(), pl);
        }
        assertTrue(inputChecker.checkIdExtraProduction(ids));
        vv.addExtraProductionToActivate(pl.getExtraProductionPowers().get(0).getId());
        assertFalse(inputChecker.checkIdExtraProduction(ids));

    }

    @Test
    void checkLeaderCard() throws NullCardException {
        Player pl = gameController.getGame().getCurrentPlayer();

        int id = pl.getLeadercards().get(0).getId();

        assertTrue(inputChecker.checkLeaderCard(id));
        assertFalse(inputChecker.checkLeaderCard(-1));

        pl.activateLeader(pl.getLeadercards().get(0), pl.getPlayerBoard(), pl);
        assertFalse(inputChecker.checkLeaderCard(id));
    }

    @Test
    void checkWhiteMarble() {
        Player pl = gameController.getGame().getCurrentPlayer();

        assertFalse(inputChecker.checkWhiteMarble(new String[]{"SERVANT"}));
        pl.addPossibleWhiteMarbles(new Resource(ResourceType.SERVANT));
        assertTrue(inputChecker.checkWhiteMarble(new String[]{"SERVANT"}));

    }

    @Test
    void checkOwnedLeaderCard() {
        Player pl = gameController.getGame().getCurrentPlayer();

        assertTrue(inputChecker.checkOwnedLeaderCard(pl.getLeadercards().get(0).getId()));
        assertFalse(inputChecker.checkOwnedLeaderCard(-1));
    }

    @Test
    void checkResources() throws CannotAdd, EmptyDeck {
        Player pl = gameController.getGame().getCurrentPlayer();
        int[] ids = new int[3];
        for(int i = 0; i < 3; i++){
          DevelopmentCard card = gameController.getGame().getDeckDevelopment()[0][i].getCard();
          pl.getPlayerBoard().addDevCard(card, i);
          ids[i] = card.getId();
        }

        assertTrue(inputChecker.checkResources(ids));
         pl.getPlayerBoard().getStrongBox().setStrongbox(new ArrayList<>());
         assertFalse(inputChecker.checkResources(ids));
    }

    @Test
    void checkResources2() throws CannotAdd, EmptyDeck {
        Player pl = gameController.getGame().getCurrentPlayer();
        ArrayList<Resource> res = new ArrayList<>();
        res.add(new Resource(ResourceType.COIN));
        res.add(new Resource(ResourceType.COIN));
        res.add(new Resource(ResourceType.SERVANT));
        res.add(new Resource(ResourceType.SERVANT));

        assertTrue(inputChecker.checkResources(res));
        pl.getPlayerBoard().getStrongBox().setStrongbox(new ArrayList<>());
        assertFalse(inputChecker.checkResources(res));
        ArrayList<Resource> res1 = new ArrayList<>();
        res1.add(new Resource(ResourceType.COIN));
        res1.add(new Resource(ResourceType.COIN));
        res1.add(new Resource(ResourceType.SERVANT));

        pl.getPlayerBoard().getStrongBox().setStrongbox(res1);
        assertFalse(inputChecker.checkResources(res));

    }


    @Test
    void checkNickname(){
        assertTrue(inputChecker.checkNickname("Alice"));
        assertFalse(inputChecker.checkNickname("Cucu"));
    }

    @Test
    void setGame() {
    }
}
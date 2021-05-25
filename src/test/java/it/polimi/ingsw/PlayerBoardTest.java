package it.polimi.ingsw;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotAdd;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import it.polimi.ingsw.model.cards.effects.ProductionPower;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.enumerations.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

/**author Alessandra Atria*/

public class PlayerBoardTest {
    private static PlayerBoard p;
    private WareHouse w;
    private StrongBox s;
    private static ArrayList<Resource> res;
    private static ArrayList<DevelopmentCard> dev;
    private static DevelopmentCard d1;
    private static DevelopmentCard d2;
    private static ArrayList<Resource> entry;
    private static ArrayList<Resource> prod;
    private static ProductionPower pp;
    private static int faithmaker;


    @Before
    public void setUp(){
        ArrayList<Resource> cost = new ArrayList<>();
        ArrayList<DevelopmentCard> dev = new ArrayList<>();
        entry = new ArrayList<>();
        prod = new ArrayList<>();
        res = new ArrayList<>();
        Resource e1 = new Resource(ResourceType.COIN);
        Resource e2 = new Resource(ResourceType.COIN);
        Resource e3 = new Resource(ResourceType.COIN);
        Resource e4 = new Resource(ResourceType.SERVANT);
        Resource p1 = new Resource(ResourceType.SHIELD);
        Resource p2 = new Resource(ResourceType.STONE);
        entry.add(e1);
        entry.add(e2);
        entry.add(e3);
        entry.add(e4);
        prod.add(p1);
        prod.add(p2);
        Resource r1 = new Resource(ResourceType.COIN);
        Resource r2 = new Resource(ResourceType.SERVANT);
        cost.add(r1);
        cost.add(r1);
        cost.add(r2);
        pp = new ProductionPower(entry,prod);
        d1 = new DevelopmentCard(1, cost, 1, CardColor.GREEN, pp, 10 );
        p = new PlayerBoard(new WareHouse(), new StrongBox());
    }

    /** gets resources, development cards and faith maker position */
    @Test
    public void getter(){
        w = new WareHouse();
        s = new StrongBox();
        ArrayList<Resource> cost = new ArrayList<>();
        d1 = new DevelopmentCard(1, cost, 2, CardColor.GREEN, pp, 10 );
        PlayerBoard p = new PlayerBoard(w,s);
        faithmaker = 1;

        assertEquals(p.getFaithMarker(), 0);
        assertEquals(p.getResources(), res);

    }



    /** moves the faithmaker of n pos*/
    @Test
    public void moveFaithmaker() {
        w = new WareHouse();
        s = new StrongBox();
        faithmaker = 1;
        PlayerBoard p = new PlayerBoard(w,s);
        assertEquals(p.getFaithMarker(), 0);
        p.moveFaithMarker(6);
        assertEquals(p.getFaithMarker(), 6);
    }



    @Test
    public void addResourceToExtraDepot() throws NotPossibleToAdd {
        PlayerBoard p = new PlayerBoard(new WareHouse(),new StrongBox());
        ExtraDepot e1 = new ExtraDepot(2, 3,(SERVANT));
        ExtraDepot e2 = new ExtraDepot(2,4, (SHIELD));
        p.getWareHouse().setExtraDepot(e1);
        p.getWareHouse().setExtraDepot(e2);
        assertFalse(e1.equals(e2));
        p.getWareHouse().addToDepot(new Resource(SERVANT),e1);
        assertEquals(p.getWareHouse().getDepots().get(3).getDepot().size(),1);
        assertThrows(NotPossibleToAdd.class, () -> p.getWareHouse().addToDepot(new Resource(COIN),e1));
    }

    /**
     * checks if card can be added to the slot and checks if player board can get development cards
     * @throws NotPossibleToAdd if it's not possible
     */

    @Test
    public void addDevCard() throws CannotAdd {
        ArrayList<Resource> cost = new ArrayList<>();
        d1 = new DevelopmentCard(3, cost, 1, CardColor.PURPLE, pp, 5 );
        d2 = new DevelopmentCard(1, cost, 3, CardColor.GREEN, pp, 10 );
        p = new PlayerBoard(w,s);
        p.addDevCard(d1,2);
        assertEquals(p.getDevelopmentCards().get(0).getColor(), CardColor.PURPLE);
        assertEquals(p.getDevelopmentCards().get(0).getLevel(), 1);
        assertEquals(p.getDevelopmentCards().get(0).getCost(), cost);
        assertEquals(p.getDevelopmentCards().get(0).getVictoryPoints(), 5);
        assertEquals(p.getDevelopmentCards().get(0).getProductionPower(), pp);
        assertThrows(CannotAdd.class, () -> p.addDevCard(d2,2));
    }

    @Test
    public void addAll() throws CannotAdd {
        DevelopmentCard d1 = new DevelopmentCard(1, new ArrayList<>(), 1, CardColor.PURPLE, pp, 5 );
        DevelopmentCard d2 = new DevelopmentCard(2, new ArrayList<>(), 2, CardColor.GREEN, pp, 10 );
        DevelopmentCard d3 = new DevelopmentCard(3, new ArrayList<>(), 3, CardColor.PURPLE, pp, 5 );
        DevelopmentCard d4 = new DevelopmentCard(4, new ArrayList<>(), 1, CardColor.GREEN, pp, 10 );
        DevelopmentCard d5 = new DevelopmentCard(5, new ArrayList<>(), 2, CardColor.PURPLE, pp, 5 );
        DevelopmentCard d6 = new DevelopmentCard(6, new ArrayList<>(), 3, CardColor.GREEN, pp, 10 );
        DevelopmentCard d7 = new DevelopmentCard(7, new ArrayList<>(), 1, CardColor.PURPLE, pp, 5 );
        Player p1 = new Player(false,"ale",1,new PlayerBoard(new WareHouse(), new StrongBox()));
        p1.getPlayerBoard().addDevCard(d1,0);
        p1.getPlayerBoard().addDevCard(d2,0);
        p1.getPlayerBoard().addDevCard(d3,0);
        assertEquals(d1,p1.getPlayerBoard().getCoveredDevCards().get(0));
        assertEquals(d2,p1.getPlayerBoard().getCoveredDevCards().get(1));
        assertEquals(d3,p1.getPlayerBoard().getDevelopmentCards().get(0));
        assertEquals(p1.getPlayerBoard().getCountDevCards(), 3);
    }


}

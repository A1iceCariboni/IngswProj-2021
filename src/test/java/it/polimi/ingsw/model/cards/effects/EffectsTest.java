package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.requirements.LevelReq;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * test for effects of leader cards
 * @author Alice Cariboni
 */

public class EffectsTest {

    private static Player player = new Player(true, "ali",0, new PlayerBoard(new WareHouse(), new StrongBox()));


    @Test
   void discountTest(){
        Discount discount = new Discount(new Resource(ResourceType.SHIELD), 2);
        assertEquals(discount.getQuantity(),2);
        assertEquals(discount.getResourceType(),new Resource(ResourceType.SHIELD));
        discount.applyEffect(player, player.getPlayerBoard());
        assertEquals(player.getDiscountedResource().get(0),new Resource(ResourceType.SHIELD));
        Discount discount2 = new Discount(new Resource(ResourceType.COIN), 2);
        discount2.applyEffect(player, player.getPlayerBoard());
        assertEquals(player.getDiscountedResource().get(2),new Resource(ResourceType.COIN));
    }

    @Test
    void extraProductionTest(){
        ExtraProductionPower extraProductionPower = new ExtraProductionPower(new Resource(ResourceType.SERVANT),1, 1);
        extraProductionPower.applyEffect(player, player.getPlayerBoard());
        assertEquals(extraProductionPower.getQuantity(),1);
        assertEquals(extraProductionPower.getResourceType(),new Resource(ResourceType.SERVANT));
        assertEquals(player.getExtraProductionPowers().get(0).getEntryResources().get(0), new Resource(ResourceType.SERVANT));
    }

    @Test
    void ExtraSlotTest(){
        ExtraSlot extraSlot = new ExtraSlot((ResourceType.SHIELD),2);
        extraSlot.applyEffect(player, player.getPlayerBoard());
        assertEquals(extraSlot.getQuantity(),2);
        assertEquals(extraSlot.getResourceType(), ResourceType.SHIELD);
        assertNotEquals(player.getPlayerBoard().getWareHouse().getDepots().get(3).getId(),-1);
        assertEquals(player.getPlayerBoard().getWareHouse().getDepots().get(3).getType(),ResourceType.SHIELD);
    }

    @Test
    void JollyMarbleTest(){
        JollyMarble jollyMarble = new JollyMarble(new Resource(ResourceType.SERVANT));
        jollyMarble.applyEffect(player, player.getPlayerBoard());
        assertEquals(jollyMarble.getResourceType(),new Resource(ResourceType.SERVANT));
        assertEquals(player.getPossibleWhiteMarbles().get(0),new Resource(ResourceType.SERVANT));
    }


}

package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyWareHouse;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.WareHouse;
import it.polimi.ingsw.utility.DummyWarehouseConstructor;
import it.polimi.ingsw.utility.WarehouseConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DepotMessage extends Message{


    public DepotMessage(DummyWareHouse wareHouse) {
        super(MessageType.DEPOTS, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(wareHouse));
    }

    public Depot[] getWareHouse(){

        Depot[] depots = WarehouseConstructor.parse(getPayload());
        return depots;
    }

    public DummyWareHouse getDummyWarehouse(){

        DummyWareHouse dw = DummyWarehouseConstructor.parse(getPayload());
        return dw;
    }
}

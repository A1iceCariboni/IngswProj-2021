package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.client.DummyModel.DummyWareHouse;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.utility.DummyWarehouseConstructor;
import it.polimi.ingsw.utility.WarehouseConstructor;

public class OtherWareHouse extends Message{

    public OtherWareHouse(DummyWareHouse wareHouse) {
        super(MessageType.OTHER_WAREHOUSE, "");
        Gson gson = new Gson();
        setPayload(gson.toJson(wareHouse));
    }


    public DummyWareHouse getDummyWarehouse(){

        DummyWareHouse dw = DummyWarehouseConstructor.parse(getPayload());
        return dw;
    }
}

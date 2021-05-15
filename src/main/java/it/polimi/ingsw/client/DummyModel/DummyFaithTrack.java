package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;
import java.util.Arrays;

public class DummyFaithTrack {
    private DummyCell[] faithTrack;

    public DummyFaithTrack(DummyCell[] faithTrack){
        this.faithTrack = faithTrack;
    }

    @Override
    public String toString() {
        return "DummyFaithTrack{" +
                "faithTrack=" + Arrays.toString(faithTrack) +
                '}';
    }
}


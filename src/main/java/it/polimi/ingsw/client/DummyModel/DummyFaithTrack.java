package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;
import java.util.Arrays;

public class DummyFaithTrack {
    private DummyCell[] faithTrack;

    public DummyFaithTrack(){}
    public DummyFaithTrack(DummyCell[] faithTrack){
        this.faithTrack = faithTrack;
    }

    public boolean isPopeSpace(int pos){
        return faithTrack[pos].isPopeSpace();
    }

    public int getReportSection(int pos){
        return faithTrack[pos].getReportSection();
    }

    @Override
    public String toString() {
        return "DummyFaithTrack{" +
                "faithTrack=" + Arrays.toString(faithTrack) +
                '}';
    }
}


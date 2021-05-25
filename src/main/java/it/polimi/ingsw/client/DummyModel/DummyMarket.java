package it.polimi.ingsw.client.DummyModel;

public class DummyMarket {
private final String[][] dummyMarbles;
private final String slindig;




    public DummyMarket(String[][] dummyMarbles, String slinding){
    this.dummyMarbles = dummyMarbles;
    this.slindig = slinding;
}


    public String[][] getDummyMarbles() {
        return dummyMarbles;
    }

    public String getSlindig() {
        return slindig;
    }


}

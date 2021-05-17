package it.polimi.ingsw.client.DummyModel;

public class DummyMarket {
private String[][] dummyMarbles;
private String slindig;




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

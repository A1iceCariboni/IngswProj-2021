package it.polimi.ingsw.client.DummyModel;

import java.util.ArrayList;

public class DummyStrongbox {
    ArrayList<String> resources;


    public DummyStrongbox(ArrayList<String> resources) {
        this.resources = resources;
    }

    public ArrayList<String> getResources() {
        return resources;
    }

    public void setResources(ArrayList<String> resources) {
        this.resources = resources;
    }
}

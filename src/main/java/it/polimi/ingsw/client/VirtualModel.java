package it.polimi.ingsw.client;

import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.model.FaithTrack;

/**
 * simplified version of the model in the client
 * @author Alice Cariboni
 */

public class VirtualModel {
private final Cli cli;
private final FaithTrack faithTrack;

    public VirtualModel(Cli cli, FaithTrack faithTrack) {
        this.cli = cli;
        this.faithTrack = faithTrack;
    }
}


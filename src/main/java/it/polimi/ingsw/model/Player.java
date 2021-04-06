package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;

public class Player {
    private boolean inkwell;
    private String nickName;
    private int victoryPoints;
    private ArrayList<LeaderCard> leaderCards;
    private PlayerBoard playerBoard;

    public Player(boolean inkwell, String nickName, int victoryPoints, ArrayList<LeaderCard> leaderCards, PlayerBoard playerBoard) {
        this.inkwell = inkwell;
        this.nickName = nickName;
        this.victoryPoints = victoryPoints;
        this.leaderCards = leaderCards;
        this.playerBoard = playerBoard;
    }

    public void addVictoryPoints(int victoryPoints) {
        this.victoryPoints = this.victoryPoints + victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

}

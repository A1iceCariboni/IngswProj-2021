package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;

/**
 * This class represents a Player
 * @author Alessandra Atria
 */

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

    public String getNickName() {
        return this.nickName;
    }


    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * This method adds Victory points to the player
     * @param victoryPoints represents the points to add to existing ones
     */
    public void addVictoryPoints(int victoryPoints) {
        this.victoryPoints = this.victoryPoints + victoryPoints;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public ArrayList<LeaderCard> getActiveLeaderCards() {
        return leaderCards;
    }

    /**
     * This method activates a leader card
     * @param card is the card that is going to be activated
     */
    public void activateLeader(LeaderCard card) throws NullCardException{
        if (!this.leaderCards.contains(card))   throw new NullCardException();
         this.leaderCards.get(leaderCards.lastIndexOf(card)).active();
    }

    /**
     * This method removes a leaderCard from the player leader card deck
     * @param card is the card that is going to be removed
     * @throws NullCardException if player doesn't have the card
     */
    public void discardLeader(LeaderCard card) throws NullCardException {
        if (!this.leaderCards.contains(card)) throw new NullCardException();
        else
         this.leaderCards.remove(card);
    }




}




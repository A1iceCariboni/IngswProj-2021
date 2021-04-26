package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.PlayerMove;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.effects.ProductionPower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
    private PlayerMove playerMove;
    private ArrayList<Resource> discountedResource;
    private ArrayList<ExtraProduction> extraProductionPowers;
    private ArrayList<Resource> possibleWhiteMarbles;

    public Player(boolean inkwell, String nickName, int victoryPoints, PlayerBoard playerBoard) {
        this.inkwell = inkwell;
        this.nickName = nickName;
        this.victoryPoints = victoryPoints;
        this.leaderCards = new ArrayList<>();
        this.playerBoard = playerBoard;
        this.discountedResource = new ArrayList<>();
        this.extraProductionPowers = new ArrayList<>();
        this.possibleWhiteMarbles = new ArrayList<>();
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


    public PlayerMove getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(PlayerMove playerMove) {
        this.playerMove = playerMove;
    }

    /**
     *
     * @return an arraylist of the discounted resources because of a leadercard effect
     */
    public ArrayList<Resource> getDiscountedResource() {
        return discountedResource;
    }

    public void addDiscountedResource(Resource discountedResource) {
        this.discountedResource.add(discountedResource);
    }


    /**
     *
     * @return an array list of extra production powers given by some leadercards effects
     */
    public ArrayList<ExtraProduction> getExtraProductionPowers() {
        return extraProductionPowers;
    }

    public void addExtraProductionPowers(ExtraProduction extraProduction) {
        this.extraProductionPowers.add(extraProduction);
    }

    /**
     *
     * @return an array list of resources that the white marbles can give to the player if it' empty the white marbels give no resources
     */
    public ArrayList<Resource> getPossibleWhiteMarbles() {
        return possibleWhiteMarbles;
    }

    public void addPossibleWhiteMarbles(Resource possibleWhiteMarble) {
        this.possibleWhiteMarbles.add(possibleWhiteMarble);
    }

    public void addLeaderCard(LeaderCard card){
        this.leaderCards.add(card);
    }

    public void setInkwell(boolean b) {
        this.inkwell = b;
    }

    public void setYourTurn(boolean b) {
    }

    public boolean getInkwell() {
        return inkwell;
    }

    public ArrayList<LeaderCard> getLeadercards() {
        return leaderCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return player.getNickName().equals(nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inkwell, nickName, victoryPoints, leaderCards, playerBoard, playerMove, discountedResource, extraProductionPowers, possibleWhiteMarbles);
    }
}




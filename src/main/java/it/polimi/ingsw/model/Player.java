package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.PlayerMove;
import it.polimi.ingsw.exceptions.NotPossibleToAdd;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.effects.ProductionPower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents a Player
 * @author Alessandra Atria
 */

public class Player implements Serializable {
    private static final long serialVersionUID = 3448105864781799196L;

    private boolean inkwell;
    private final String nickName;
    private int victoryPoints;
    private final ArrayList<LeaderCard> leaderCards;
    private final PlayerBoard playerBoard;
    private PlayerMove playerMove;
    private final ArrayList<Resource> discountedResource;
    private final ArrayList<ExtraProduction> extraProductionPowers;
    private final ArrayList<Resource> possibleWhiteMarbles;


    public Player(){
        this.inkwell = false;
        this.nickName = "";
        this.victoryPoints = 0;
        this.leaderCards = new ArrayList<>();
        this.playerBoard = new PlayerBoard(new WareHouse(), new StrongBox());
        this.discountedResource = new ArrayList<>();
        this.extraProductionPowers= new ArrayList<>();
        this.possibleWhiteMarbles = new ArrayList<>();
    }

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
        ArrayList<LeaderCard> leaderCards1 = new ArrayList<>();
        for(LeaderCard leaderCard: leaderCards){
            if(leaderCard.isActive()){
                leaderCards1.add(leaderCard);
            }
        }
        return leaderCards1;
    }

    /**
     * This method activates a leader card
     * @param card is the card that is going to be activated
     */
    public void activateLeader(LeaderCard card, PlayerBoard b, Player p) throws NullCardException{
        if (!this.leaderCards.contains(card))   throw new NullCardException();
         this.leaderCards.get(leaderCards.lastIndexOf(card)).active(p,b);
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

    public LeaderCard getLeaderCardById(int id){
        for(LeaderCard leaderCard: leaderCards){
            if(leaderCard.getId() == id){
                return leaderCard;
            }
        }
        return null;
    }
    @Override
    public int hashCode() {
        return Objects.hash(inkwell, nickName, victoryPoints, leaderCards, playerBoard, playerMove, discountedResource, extraProductionPowers, possibleWhiteMarbles);
    }
    public Depot getDepotById(int id) {
        for(Depot depot: playerBoard.getWareHouse().getDepots()){
            if(depot.getId() == id){
                return depot;
            }
        }
        return null;
    }

    public int getBlackCross(){
        return -1;
    }

    public ActionToken getToken(){
        return null;
    }
}




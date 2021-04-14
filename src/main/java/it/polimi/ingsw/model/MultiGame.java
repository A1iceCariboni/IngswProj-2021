package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.utility.DevelopentCardParser;
import it.polimi.ingsw.utility.LeaderCardParser;

import java.util.ArrayList;

/**
 * @author Sofia Canestraci
 * the class controls the game if there are more than 1 player
 */
public class MultiGame extends Game {
    private LeaderDeck deckLeader;
    private DevelopmentCardDeck[][] deckDevelopment;
    private FaithTrack faithTrack;
    private MarketTray marketTray;
    private ArrayList<Player> players;

    public MultiGame(ArrayList<Player> players) throws JsonFileNotFoundException {
        this.players = players;
        this.faithTrack = new FaithTrack();
        this.marketTray = new MarketTray();
        deckLeader = new LeaderDeck(LeaderCardParser.parseLeadCards());
        this.deckDevelopment = new DevelopmentCardDeck[3][4];
        for(int r = 0; r< 3; r++) {
            for (int c = 0; c < 4; c++) {
                this.deckDevelopment[r][c] = new DevelopmentCardDeck();
            }
        }
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck(DevelopentCardParser.parseDevCards());
        CardColor[] colors = {CardColor.GREEN, CardColor.YELLOW, CardColor.PURPLE, CardColor.BLUE};
        for(int r = 0; r< 3; r++){
            for(int c = 0; c< 4; c++){
                this.deckDevelopment[r][c].addCard(developmentCardDeck.getByColorAndLevel(colors[c],r+1));
            }
        }

    }

    /**
     * it starts the game and creates the principal classes
     */
    @Override
    public void startGame() throws JsonFileNotFoundException {
        players = new ArrayList<>();
    }

    /**
     * @param player the player that ends the game
     * it lets that each player ends the round to end the game and launch the method to find the winners
     */
    @Override
    public void endGame(Player player){
        int i = players.indexOf(player);
        Player player1 = player;
        if (i != players.size() - 1){
            player1 = nextPlayer(player1);
            i = i+1;
        }
        addWinner();
    }

    /**
     * it gets the victory points of each player and checks who are the winners
     * @return winners: an array list that contains the winners
     */

    public ArrayList<Player> addWinner(){
        int winnerVictoryPoints = 0;
        ArrayList<Player> winners = new ArrayList<>();
        for (int j = 0; j<players.size(); j++){
            int victoryPoints = players.get(j).getVictoryPoints();
            if (victoryPoints>=winnerVictoryPoints){
                if (victoryPoints != winnerVictoryPoints){
                    winners.clear();
                }
                winnerVictoryPoints = victoryPoints;
                winners.add(players.get(j));
            }
        }
        return winners;

    }

    /**
     * it adds a player for the game and it sets the inkwell true to the first player
     * (=the first player that is added in the arraylist)
     * @param p the player to add in the arraylist: players, where there are all the players
     */
    @Override
    public void addPlayer(Player p){
        this.players.add(p);
        if (this.players.indexOf(p) == 0){
            p.setInkwell(true);
        }
        else p.setInkwell(false);
    }


    /**
     * it decides who is the player that has to play next
     * @param player the player that ends the turn
     * @return the player that has to play next
     */
    @Override
    public Player nextPlayer(Player player){
        int numberPlayers = players.size();
        int i = players.indexOf(player);
        if (i != numberPlayers - 1){
            i = i + 1;
        }
        else i = 0;
        players.get(i).setYourTurn(true);
        return players.get(i);
    }
    @Override
    public MarketTray getMarketTray(){ return marketTray;}
    @Override
    public FaithTrack getFaithTrack(){ return faithTrack;}

    public ArrayList<Player> getPlayers(){ return players;}

    public LeaderDeck getDeckLeader() {
        return deckLeader;
    }

    /**
     *
     * @param col column in the grid of dev cards
     * @param row row in the grid of dev cards
     * @return the card i have required and remove it from the table
     */
    public DevelopmentCard getCardFrom(int col, int row){
       return this.deckDevelopment[row][col].popCard();
    }

    public DevelopmentCardDeck[][] getDeckDevelopment() {
        return deckDevelopment;
    }
}




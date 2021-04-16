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
    private ArrayList<Player> winners;
    private int currentPlayer;

    public MultiGame() throws JsonFileNotFoundException {
        this.players = new ArrayList<>();
        this.faithTrack = new FaithTrack();
        this.marketTray = new MarketTray();
        this.winners = null;
        this.currentPlayer = 0;
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
                for(int i = 0; i < 4; i++) {
                    this.deckDevelopment[r][c].addCard(developmentCardDeck.getByColorAndLevel(colors[c], r + 1));
                }
            }
        }

    }

    /**
     * it assigns the cards to the players
     */
    @Override
    public void startGame() throws JsonFileNotFoundException {
       for(Player player: this.players) {
           for (int i = 0; i < 4; i++) {
               player.addLeaderCard(this.deckLeader.popCard());
           }
          if(player.getInkwell()){
              this.currentPlayer = players.indexOf(player);
          }
       }

    }

    /**
     * @param player the player that ends the game
     * it lets that each player ends the round to end the game and launch the method to find the winners
     */
    @Override
    public void endGame(Player player){
        int i = players.indexOf(player);
        Player player1 = player;
        for(int j=0; j<players.size(); j++){
            if (i != players.size() - 1){
                player1 = nextPlayer();
                i = i+1;
            }
        }
        addWinner();
    }

    /**
     *  it adds the victory points of the number of the resources and of the faith track
     *  and it gets the victory points of each player and checks who are the winners
     *  (winners have same victory points and same number of resources)
     * @return winners: the player who has won
     */

    public ArrayList<Player> addWinner(){
        for (int i=0; i<players.size(); i++){
             int pointsFaithTrack = getFaithTrack().getLastVictoryPoint(players.get(i).getPlayerBoard().getFaithMarker());
             int pointsResources = players.get(i).getPlayerBoard().getResources().size() / 5;
             players.get(i).addVictoryPoints(pointsResources+pointsFaithTrack);
        }

        int winnerVictoryPoints = 0;
        winners = new ArrayList<>();
        Player tempWinner = null;
        for (int j = 0; j<players.size(); j++){
            int victoryPoints = players.get(j).getVictoryPoints();
            if (victoryPoints>winnerVictoryPoints){
                winners.clear();
                winners.add(players.get(j));
                tempWinner = winners.get(0);
                winnerVictoryPoints = victoryPoints;
            }
            if (victoryPoints == winnerVictoryPoints && tempWinner != null && tempWinner != players.get(j)){
                int jResources = players.get(j).getPlayerBoard().getResources().size();
                int tempResources = tempWinner.getPlayerBoard().getResources().size();
                if (jResources > tempResources){
                    winners.clear();
                    winners.add(players.get(j));
                    tempWinner = winners.get(0);
                    winnerVictoryPoints = victoryPoints;
                }
                if (jResources == tempResources){
                    winners.add(players.get(j));
                }
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
     * @return the player that has to play next
     */
    @Override
    public Player nextPlayer(){
        currentPlayer = (currentPlayer + 1) % players.size();
        return players.get(currentPlayer);
    }


    @Override
    public MarketTray getMarketTray(){ return marketTray;}
    @Override
    public FaithTrack getFaithTrack(){ return faithTrack;}

    public ArrayList<Player> getPlayers(){ return players;}

    public LeaderDeck getDeckLeader() { return deckLeader;}

    public ArrayList<Player> getWinners() { return winners;}

    /**
     * it checks if a player has 7 development cards or has the faith marker on the last position of the faith track
     * @return true if the required are satisfied
     */
    @Override
    public boolean checkEndGame(){
        boolean b = false;
        for(int i=0; i<players.size(); i++){
            if (players.get(i).getPlayerBoard().getCountDevCards() == 7){
                b = true;
                endGame(players.get(i));
            }
            if (players.get(i).getPlayerBoard().getFaithMarker() == 23){
                b = true;
                endGame(players.get(i));
            }
        }
        return b;
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

    /**
     * it controls if the players are in the report section of the faith track and if true it add
     * the points for pope to the victory points of the player
     */
    @Override
    public void getPopePoints(){
        for (int i = 0; i < players.get(this.currentPlayer).getPlayerBoard().getFaithMarker(); i++) {
            if (this.faithTrack.isPopeSpace(i)) {
                for (Player player : this.players) {
                    if (faithTrack.isReportSection(player.getPlayerBoard().getFaithMarker()-1)) {
                        player.addVictoryPoints(this.faithTrack.getPointsForPope(i));
                    }
                }
                this.faithTrack.deactivatePopeSpace(i);
                this.faithTrack.deactivateSection(i);
            }
        }
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }
}




package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.MarketTray;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Deck;

import java.util.ArrayList;

/**
 * @author Sofia Canestraci
 * the class controls the game if there are more than 1 player
 */
public class MultiGame extends Game {
    private Deck deckLeader;
    private Deck deckDevelopment[][];
    private FaithTrack faithTrack;
    private MarketTray marketTray;
    private ArrayList<Player> players = new ArrayList<>();

    public MultiGame(ArrayList<Player> players, FaithTrack faithTrack, MarketTray marketTray){
        this.players = players;
        this.faithTrack = faithTrack;
        this.marketTray = marketTray;
    }

    /**
     * it starts the game and creates the principal classes
     */
    @Override
    public void startGame() throws JsonFileNotFoundException {
        faithTrack = new FaithTrack();
        marketTray = new MarketTray();
        players = new ArrayList<Player>();

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
    public MarketTray getMarketTray(){ return this.marketTray;}
    @Override
    public FaithTrack getFaithTrack(){ return this.faithTrack;}

    public ArrayList<Player> getPlayers(){ return this.players;}
}




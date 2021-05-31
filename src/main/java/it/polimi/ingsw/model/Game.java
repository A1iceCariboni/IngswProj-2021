package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.InvalidNickname;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.LeaderDeck;
import it.polimi.ingsw.utility.DevelopentCardParser;
import it.polimi.ingsw.utility.LeaderCardParser;
import it.polimi.ingsw.utility.MarketTrayParser;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Sofia Canestraci
 * the class controls the game and makes possible that it starts and ends
 */
public class Game implements Serializable {
    private static final long serialVersionUID = -1219837309023544815L;

    protected LeaderDeck deckLeader;
    protected DevelopmentCardDeck[][] deckDevelopment;
    protected FaithTrack faithTrack;
    protected MarketTray marketTray;
    protected ArrayList<Player> players;
    protected ArrayList<Player> winners;
    protected int currentPlayer;


    public Game() throws JsonFileNotFoundException {
        this.players = new ArrayList<>();
        this.faithTrack = new FaithTrack();
        this.marketTray = MarketTrayParser.parseMarket();
        this.winners = new ArrayList<>();
        this.currentPlayer = 0;
        this.deckLeader = new LeaderDeck(LeaderCardParser.parseLeadCards());
        this.deckDevelopment = new DevelopmentCardDeck[3][4];
        for(int r = 0; r< Constants.rows; r++) {
            for (int c = 0; c < Constants.cols; c++) {

                this.deckDevelopment[r][c] = new DevelopmentCardDeck();
            }
        }
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck(DevelopentCardParser.parseDevCards());
        CardColor[] colors = {CardColor.GREEN, CardColor.YELLOW, CardColor.PURPLE, CardColor.BLUE};
        for(int r = 0; r< Constants.rows; r++){
            for(int c = 0; c< Constants.cols; c++){
                for(int i = 0; i < Constants.smallDecks; i++) {
                    this.deckDevelopment[r][c].addCard(developmentCardDeck.getByColorAndLevel(colors[c], r + 1));
                }
            }
        }

    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    /**
     * it starts the game and creates the principal classes
     */
      public void startGame() {
          for(Player player: players) {
              for (int i = 0; i < Constants.smallDecks; i++) {
                  player.addLeaderCard(this.deckLeader.popCard());
              }
              if(player.getInkwell()){
                  this.currentPlayer = players.indexOf(player);
              }
          }
      }


    public DevelopmentCardDeck[][] getDeckDevelopment() {
        return deckDevelopment;
    }


    /**
     * it adds a Player for the game
     * @param p player to add
     */
    public void addPlayer(Player p){}

    /**
     * it checks if the Game has to end
     * @return
     */
     public boolean checkEndGame(){return false;}

    /**
     * it decides who is the player that has to play next
     * @return the player that has to play next
     */
     public  Player nextPlayer(){return null;}


    public LeaderDeck getDeckLeader() { return deckLeader;}

    public  MarketTray getMarketTray(){return marketTray;}

    public  FaithTrack getFaithTrack(){return faithTrack;}

    public  ArrayList<Player> getPlayers(){return players;}
    /**
     * it controls if the players are in the report section of the faith track and if true it add
     * the points for pope to the victory points of the player
     */
     public  void getPopePoints(){}

     public boolean checkPopeSpace(){
         for(Player player: players){
             if(faithTrack.isPopeSpace(player.getPlayerBoard().getFaithMarker())){
                 getPopePoints();
                 return true;
             }
         }
         return false;
     }
     public ArrayList<Player> getWinners() { return winners;}

    public Player getPlayerByNickname(String nickname) throws InvalidNickname {
         for(Player p: players){
             if(p.getNickName().equals(nickname)){
                 return p;
             }
         }
         throw new InvalidNickname("That's not a player");
     }

    public FakePlayer getFakePlayer(){
         return null;
    }
}


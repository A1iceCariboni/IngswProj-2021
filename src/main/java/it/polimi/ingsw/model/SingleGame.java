package it.polimi.ingsw.model;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.exceptions.NullCardException;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.LeaderDeck;
import it.polimi.ingsw.utility.DevelopentCardParser;
import it.polimi.ingsw.utility.LeaderCardParser;

import java.util.ArrayList;

/**
 * @author Sofia Canestraci
 * the class controls the game if ther are only one player
 */

public class SingleGame extends Game{
    private Player player;
    private FaithTrack faithTrack;
    private LeaderDeck LeaderDeck;
    private FakePlayer fakePlayer;
    private MarketTray marketTray;
    private DevelopmentCardDeck[][] deckDevelopment;
    private Player winner;

    public SingleGame() throws JsonFileNotFoundException {
        this.player = null;
        this.LeaderDeck = new LeaderDeck(LeaderCardParser.parseLeadCards());
        this.fakePlayer = new FakePlayer(1);
        this.marketTray = new MarketTray();
        this.faithTrack = new FaithTrack();
        this.winner = null;
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
     * it assigns the cards to the single player
     */
    @Override
    public void startGame() {

    }

    /**
     * it assignes player as a winner
     * @param player the player that wins
     */
    @Override
    public void endGame(Player player) {
           this.winner = player;
    }

    /**
     * it adds the single player and set his inkwell true
     * @param p: single player to add
     */
    @Override
    public void addPlayer(Player p) {
        this.player = p;
        p.setInkwell(true);
    }

    /**
     *it checks if the game has to end and if is the player to end the game calls endGame()
     * to make player as the winner
     * @return true if the game has to end
     */
    @Override
    public boolean checkEndGame() {
        boolean b = false;
        if (fakePlayer.getBlackCross() == 24){
            b = true;
        }
        for(int c=0; c<4; c++){
            if(getColDevCards(c).isEmpty()){
                b = true;
            }
        }
        if (player.getPlayerBoard().getFaithMarker() == 24){
            b = true;
            endGame(player);
        }
        if(player.getPlayerBoard().getCountDevCards() == 7){
            b = true;
            endGame(player);
        }
        return b;
    }

    /**
     * it returns the column of the development deck of the column selected with the parameter
     * @param c the column of the deckDevelopment to return
     * @return colDevCards: the arraylist contains the development card in the column of the development card deck
     */
    public ArrayList<DevelopmentCardDeck> getColDevCards(int c){
        int k=0;
        ArrayList<DevelopmentCardDeck> colDevCards = new ArrayList<>();
        for(int r=0; r<3; r++){
            colDevCards.add(deckDevelopment[r][c]);
            }
        return colDevCards;
    }

    /**
     *it return the player, it is always the player's turn, but first get a tocken card of Fake Player
     * @param player the player that ends the turn
     * @return the same single player that has to play again after the action of the fake player
     */
    @Override
    public Player nextPlayer(Player player) {
        fakePlayer.getToken();
        return player;
    }

    /**
     * it controls if the player is in the report section of the faith track and if true it add
     * the points for pope to the victory points of the player
     */
    @Override
    public void getPopePoints(){
        if (faithTrack.isReportSection(player.getPlayerBoard().getFaithMarker())){
                player.addVictoryPoints(faithTrack.getPointsForPope(player.getPlayerBoard().getFaithMarker()));
        }
    }

    @Override
    public MarketTray getMarketTray() {
        return marketTray;
    }

    @Override
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Player getWinner() { return winner;}

    public Player getPlayer(){
        return player;
    }

    public FakePlayer getFakePlayer(){
        return fakePlayer;
    }

    public DevelopmentCardDeck[][] getDevelopmentCardDeck(){
        return deckDevelopment;
    }
// da modificare
    public void discardCard(CardColor cardColor, int quantity) {

    }
}
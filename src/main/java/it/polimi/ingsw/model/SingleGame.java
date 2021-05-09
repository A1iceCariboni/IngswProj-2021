package it.polimi.ingsw.model;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.Deck;
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

    private FakePlayer fakePlayer;

    public SingleGame() throws JsonFileNotFoundException {
        super();
        this.fakePlayer = new FakePlayer(1);
    }

    /**
     * it assigns the cards to the single player
     */
    @Override
    public void startGame() {
        super.startGame();
    }
    /**
     * it assignes player as a winner
     * @param player the player that wins
     */
    @Override
    public void endGame(Player player) {
        this.winners.add(player);
    }

    /**
     * it adds the single player and set his inkwell true
     * @param p: single player to add
     */
    @Override
    public void addPlayer(Player p) {
        this.players.add(p);
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
        if (fakePlayer.getBlackCross() == Constants.winFaith){
            b = true;
        }
        for(int c=0; c<Constants.cols; c++){
            if(getColDevCards(c).isEmpty()){
                b = true;
            }
        }
        if (players.get(0).getPlayerBoard().getFaithMarker() == Constants.winFaith){
            b = true;
            endGame(players.get(0));
        }
        if(players.get(0).getPlayerBoard().getCountDevCards() == Constants.winDev){
            b = true;
            endGame(players.get(0));
        }
        return b;
    }

    /**
     * it returns the column of the development deck of the column selected with the parameter
     * @param c the column of the deckDevelopment to return
     * @return colDevCards: the arraylist contains the development card in the column of the development card deck
     */
    public ArrayList<DevelopmentCardDeck> getColDevCards(int c){
        ArrayList<DevelopmentCardDeck> colDevCard = new ArrayList<>();
        for(int r=0; r<Constants.rows; r++){
            colDevCard.add(deckDevelopment[r][c]);
        }
        return colDevCard;
    }

    /**
     *it return the player, it is always the player's turn, but first get a tocken card of Fake Player
     * @return the same single player that has to play again after the action of the fake player
     */
    @Override
    public Player nextPlayer() {
        fakePlayer.getToken();
        return players.get(0);
    }

    /**
     * it controls if the player is in the report section of the faith track and if true it add
     * the points for pope to the victory points of the player
     */
    @Override
    public void getPopePoints(){
        if (faithTrack.isReportSection(players.get(0).getPlayerBoard().getFaithMarker())){
            players.get(0).addVictoryPoints(faithTrack.getPointsForPope(players.get(0).getPlayerBoard().getFaithMarker()));
        }
    }


     public Player getCurrentPlayer(){
        return players.get(0);
     }



    public FakePlayer getFakePlayer(){
        return fakePlayer;
    }


    public void discardCard(CardColor cardColor, int quantity) {
        int i = quantity;
        for(int r = 0; r < Constants.rows; r++){
            for(int c = 0; c < Constants.cols ; c++){
                for(int ind = 0; ind < Constants.smallDecks; ind++) {
                    if (!this.deckDevelopment[r][c].isEmpty() && this.deckDevelopment[r][c].getCardDeck().get(0).getColor() == cardColor && i > 0) {
                        this.deckDevelopment[r][c].popCard();
                        i--;
                    }
                }
            }
        }
    }
}
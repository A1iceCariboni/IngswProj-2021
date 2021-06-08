package it.polimi.ingsw.model;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.InvalidNickname;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;

/**
 * @author Sofia Canestraci
 * the class controls the game if ther are only one player
 */

public class SingleGame extends Game{


    public SingleGame() throws JsonFileNotFoundException {
        players.add(new FakePlayer());
    }

    /**
     * it assigns the cards to the single player
     */
    @Override
    public void startGame() {
        for (int i = 0; i < Constants.smallDecks; i++) {
            players.get(1).addLeaderCard(deckLeader.popCard());
        }
    }

    /**
     * it adds the single player and set his inkwell true
     * @param p: single player to add
     */
    @Override
    public void addPlayer(Player p) {
        p.setInkwell(true);
        this.players.add(p);
    }

    /**
     *it checks if the game has to end and if is the player to end the game calls endGame()
     * to make player as the winner
     * @return true if the game has to end
     */
    @Override
    public boolean checkEndGame() {
        boolean b = players.get(0).getBlackCross() >= Constants.winFaith;
        for(int c=0; c<Constants.cols; c++){
            if(getColDevCards(c).isEmpty()){
                b = true;
            }
        }
        if (players.get(1).getPlayerBoard().getFaithMarker() >= Constants.winFaith){
            b = true;
        }
        if(players.get(1).getPlayerBoard().getCountDevCards() >= Constants.winDev){
            b = true;
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
     *it return the player, it is always the player's turn, but first get a token card of Fake Player
     * @return the same single player that has to play again after the action of the fake player
     */
    @Override
    public Player nextPlayer() {
        players.get(0).getToken().applyEffect(this, (FakePlayer) players.get(0));
        return players.get(1);
    }

    /**
     * it controls if the player is in the report section of the faith track and if true it add
     * the points for pope to the victory points of the player
     */
    @Override
    public void getPopePoints(){
        if (faithTrack.isReportSection(players.get(1).getPlayerBoard().getFaithMarker())){
            players.get(1).addVictoryPoints(faithTrack.getPointsForPope(players.get(1).getPlayerBoard().getFaithMarker()));
        }
    }

@Override
     public Player getCurrentPlayer(){
        return players.get(1);
     }

    /**
     * return an arraylist of winners that can be empty if the winner is the fake player
     * @return the arraylist of winners
     */
    @Override
public ArrayList<Player> getWinners(){
    boolean b = players.get(0).getBlackCross() == Constants.winFaith;
    for(int c=0; c<Constants.cols; c++){
        if(getColDevCards(c).isEmpty()){
            b = true;
        }
    }
    if(!b){
        this.winners.add(players.get(1));
    }
    Player p = players.get(0);
    p.addVictoryPoints(faithTrack.getLastVictoryPoint(p.getPlayerBoard().getFaithMarker()));
    for(DevelopmentCard developmentCard: p.getPlayerBoard().getAllDevelopmentCards()){
        p.addVictoryPoints(developmentCard.getVictoryPoints());
    }
    for(LeaderCard leaderCard: p.getActiveLeaderCards()){
        p.addVictoryPoints(leaderCard.getVictoryPoints());
    }
    p.addVictoryPoints(p.getPlayerBoard().getResources().size()/5);
    return winners;
}


    public void discardCard(CardColor cardColor, int quantity) {
        int i = quantity;
        for(int r = 0; r < Constants.rows; r++){
            for(int c = 0; c < Constants.cols ; c++){
                for(int ind = 0; ind < Constants.smallDecks; ind++) {
                    if (!this.deckDevelopment[r][c].isEmpty() && this.deckDevelopment[r][c].getDevelopmentCards().get(0).getColor() == cardColor && i > 0) {
                        this.deckDevelopment[r][c].popCard();
                        i--;
                    }
                }
            }
        }
    }

    @Override
    public FakePlayer getFakePlayer(){
        return (FakePlayer) players.get(0);
    }

    @Override
    public ArrayList<Player> getPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(this.players.get(1));
        return  players;
    }

    @Override
    public Player getPlayerByNickname(String nickname) throws InvalidNickname {
        if(nickname.equals(players.get(1).getNickName())) return players.get(1);
        throw new InvalidNickname("Not a nickname");
    }
}
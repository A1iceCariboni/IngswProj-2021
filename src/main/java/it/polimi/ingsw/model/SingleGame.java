package it.polimi.ingsw.model;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.LeaderDeck;
public class SingleGame{
    Player player;
    FaithTrack faithTrack;
    LeaderDeck LeaderDeck;
    FakePlayer fakePlayer;
    MarketTray marketTray;
    public SingleGame(Player player) {
        this.player = player;
    }
    public void discardCard(CardColor cardColor, int quantity) {
    }
}
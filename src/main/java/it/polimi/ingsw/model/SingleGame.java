package it.polimi.ingsw.model;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.DevelopmentCardDeck;
import it.polimi.ingsw.model.cards.LeaderDeck;
import it.polimi.ingsw.utility.DevelopentCardParser;
import it.polimi.ingsw.utility.LeaderCardParser;

public class SingleGame{
    private Player player;
    private FaithTrack faithTrack;
    private LeaderDeck LeaderDeck;
    private FakePlayer fakePlayer;
    private MarketTray marketTray;
    private DevelopmentCardDeck[][] deckDevelopment;

    public SingleGame(Player player) throws JsonFileNotFoundException {
        this.player = player;
        this.LeaderDeck = new LeaderDeck(LeaderCardParser.parseLeadCards());
        this.fakePlayer = new FakePlayer(1);
        this.marketTray = new MarketTray();
        this.faithTrack = new FaithTrack();
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
    public void discardCard(CardColor cardColor, int quantity) {
    }
}
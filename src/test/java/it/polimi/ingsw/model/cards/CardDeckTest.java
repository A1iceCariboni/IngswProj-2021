package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.utility.DevelopentCardParser;
import it.polimi.ingsw.utility.LeaderCardParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {
    @Test
    public void leaderTest() throws JsonFileNotFoundException {
        LeaderDeck leaderDeck = new LeaderDeck(LeaderCardParser.parseLeadCards());
        assertFalse(leaderDeck.isEmpty());
        int beforeSize = leaderDeck.getCardDeck().size();
        leaderDeck.shuffle();
        assertEquals(leaderDeck.getCardDeck().size(),beforeSize);
        LeaderCard leaderCard = leaderDeck.popCard();
        assertTrue(beforeSize > leaderDeck.getCardDeck().size());
        leaderDeck.addCard(leaderCard);
        assertTrue(beforeSize==leaderDeck.getCardDeck().size());
    }


    @Test
    public void devTest(){
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck(DevelopentCardParser.parseDevCards());
        int beforeSize = developmentCardDeck.getDevelopmentCards().size();
        DevelopmentCard developmentCard = developmentCardDeck.popCard();
        assertTrue(beforeSize>developmentCardDeck.getDevelopmentCards().size());
        developmentCardDeck.addCard(developmentCard);
        assertTrue(developmentCardDeck.getDevelopmentCards().size() == beforeSize);
        developmentCard = developmentCardDeck.getByColor(CardColor.GREEN);
        assertTrue(developmentCard.getColor() == CardColor.GREEN);
        developmentCard = developmentCardDeck.getByLevel(1);
        assertTrue(developmentCard.getLevel() == 1);
        developmentCard = developmentCardDeck.getByColorAndLevel(CardColor.BLUE,3);
        assertTrue(developmentCard.getColor() == CardColor.BLUE && developmentCard.getLevel() == 3);
    }
}
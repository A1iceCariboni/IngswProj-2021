package it.polimi.ingsw.messages.request;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.cards.DevelopmentCard;

/**
 * message from the client to buy a development card to add to his playerboard
 */
public class BuyDevelopmentCard extends Message {

    private int row;
    private int col;
    private DevelopmentCard developmentCard;

    /**
     * constructor to buy a development card from the game board
     * @param payload
     * @param developmentCard card to add to the player board if possible
     */
    public BuyDevelopmentCard(String payload, DevelopmentCard developmentCard, int row, int col) {
        super(MessageType.BUY_DEVELOPMENT_CARD, payload);
        this.row = row;
        this.col = col;
        this.developmentCard = developmentCard;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public DevelopmentCard getDevelopmentCard(){
        return this.developmentCard;
    }
}

package it.polimi.ingsw.model;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.client.DummyModel.DummyMarket;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.enumerations.MarbleColor;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @autor Sofia Canestraci
 * this class represents the Market Tray: the area where the marbles can be bought
 */
public class MarketTray implements Serializable {
    private static final long serialVersionUID = -8136525021121205628L;

    private Marble slidingMarble;
    private Marble[][] marbles = new Marble[Constants.MARKET_ROWS][Constants.MARKET_COLS];
    /**
     *
     * it puts the marbles in the matrix and it sets the sliding marble

     */
    public MarketTray(Marble[][] marbles, final Marble slidingMarble) {
        this.marbles = marbles;
        this.slidingMarble = slidingMarble;
    }




    /**
     * return a column of the Market Tray
     * @param c the number of the column required
     * @return colMarble: an array list that contains the marbles in the column required
     */
    public ArrayList<Marble> getCol(int c) {
        ArrayList<Marble> colMarble = new ArrayList<Marble>();
        for (int r=0; r <3; r++) {
            colMarble.add(marbles[r][c]);
        }
        Marble temporaryFreeMarble = slidingMarble;
        this.slidingMarble = marbles[2][c];
        for (int r=2; r>0; r--){
            this.marbles[r][c] = marbles[r-1][c];
        }
        this.marbles[0][c] = temporaryFreeMarble;
        return colMarble;
    }

    /**
     * return a row of the Market Tray
     * @param r the number of the row required
     * @return rowMarble: an array list that contains the marbles in the row required
     */
    public ArrayList<Marble> getRow(int r){
        ArrayList<Marble> rowMarble= new ArrayList<Marble>();
        for (int c=0; c<4; c=c+1){
            rowMarble.add(marbles[r][c]);
        }
        Marble temporaryFreeMarble = slidingMarble;
        this.slidingMarble = marbles[r][3];
        for (int k=3; k>0; k--){
            this.marbles[r][k] = marbles[r][k-1];
        }
        this.marbles[r][0] = temporaryFreeMarble;
        return rowMarble;
    }

    /**
     * it returns the marble of the market tray that it is not possible to buy
     * @return slidingMarble: a marble of the market tray not in the matrix
     */
    public Marble getSlidingMarble() { return slidingMarble; }

    /**
     * it returns the structure of the matrix with the marbles that it is possible to buy
     * @return marbles: the marbles contained in the market tray, excepted the free marble
     */
    public Marble[][] getMarketTrayMarbles() { return marbles; }


    public DummyMarket getDummy(){
        String[][] dummyMarbles = new String[Constants.MARKET_ROWS][Constants.MARKET_COLS];

        for(int r = 0; r < Constants.MARKET_ROWS; r++) {
            for (int c = 0; c < Constants.MARKET_COLS; c++) {
                dummyMarbles[r][c] = marbles[r][c].getMarbleColor().name();
            }
        }
        return new DummyMarket(dummyMarbles, slidingMarble.getMarbleColor().name());
    }
}


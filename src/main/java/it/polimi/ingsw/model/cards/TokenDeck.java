package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.TokenType;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.cards.effects.TokenEffect;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class TokenDeck extends Deck{
    ArrayList<ActionToken> actionTokens = new ArrayList<>();
    ArrayList<ActionToken> pickedTokens = new ArrayList<>();

    /**
     * this constructor loads the action tokens for the game from actiotokens json in resources
     * and creates the effect of the action token using lambda functions
     * @author Alice Cariboni
     * @throws JsonFileNotFoundException
     */
    public TokenDeck() throws JsonFileNotFoundException {

    String path = "/json/actiontokens.json";


    InputStream is = TokenDeck.class.getResourceAsStream(path);

    if (is == null) throw new JsonFileNotFoundException("File " + path + " not found");

    JsonParser parser = new JsonParser();


    JsonObject json = parser.parse(new InputStreamReader(is)).getAsJsonObject();
    JsonArray tokenTypes = json.getAsJsonArray("actiontokens");


    TokenEffect tokenEffect;

    for(JsonElement tokenTypeElem : tokenTypes){

     JsonObject tokenType = tokenTypeElem.getAsJsonObject();
     String tokenName = tokenType.get("name").getAsString();
     TokenType tokenEnum = TokenType.valueOf(tokenName);


       switch(tokenEnum){
            case DRAW_YELLOW:
                tokenEffect = (singleGame, fakePlayer) -> singleGame.discardCard(CardColor.YELLOW,2);
                this.actionTokens.add(new ActionToken(tokenEnum,tokenEffect));

                break;
            case DRAW_BLUE:
                tokenEffect = (singleGame, fakePlayer) -> singleGame.discardCard(CardColor.BLUE,2);
                this.actionTokens.add(new ActionToken(tokenEnum,tokenEffect));

                break;
            case DRAW_GREEN:
                tokenEffect = (singleGame, fakePlayer) -> singleGame.discardCard(CardColor.GREEN,2);
                this.actionTokens.add(new ActionToken(tokenEnum,tokenEffect));

                break;
            case DRAW_PURPLE:
                tokenEffect = (singleGame, fakePlayer) -> singleGame.discardCard(CardColor.PURPLE,2);
                this.actionTokens.add(new ActionToken(tokenEnum,tokenEffect));

                break;
            case MOVE_2:
                tokenEffect = (singleGame, fakePlayer) -> fakePlayer.moveBlackCross(2);
                this.actionTokens.add(new ActionToken(tokenEnum,tokenEffect));

                break;
            case MOVE_AND_SHUFFLE:
                tokenEffect = (singleGame, fakePlayer) -> {
                    fakePlayer.moveBlackCross(1);
                    fakePlayer.shuffleToken();
                };
                this.actionTokens.add(new ActionToken(tokenEnum,tokenEffect));

                break;

        }
    }
    this.shuffle();
    System.out.println(this);
}

    /**
     * move the first token from the token deck to the picked token deck
     * @return the picked token
     */
    public ActionToken pickToken(){
        ActionToken actionToken = this.actionTokens.get(0);
        this.actionTokens.remove(actionToken);
        this.pickedTokens.add(actionToken);
        return actionToken;
    }

    /**
     * shuffle the deck
     */
    @Override
    public void shuffle() {
        this.actionTokens.addAll(this.pickedTokens);
        Collections.shuffle(this.actionTokens);
    }

    public ArrayList<ActionToken> getActionTokens() {
        return actionTokens;
    }

    public ArrayList<ActionToken> getPickedTokens() {
        return pickedTokens;
    }
}



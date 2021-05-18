package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.CLI.Cli;
import it.polimi.ingsw.client.DummyModel.*;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.model.FaithCell;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.MarketTray;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.utility.LeaderCardParser;
import static it.polimi.ingsw.enumerations.Constants.*;
import static it.polimi.ingsw.enumerations.Constants.ANSI_RESET;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * simplified version of the player model in the client
 * @authors Alessandra Atria and Sofia Canestraci
 */



public class VirtualModel {
    private DummyPlayerBoard playerBoard;
    private DummyDepot depot;
    private ArrayList<DummyLeaderCard> leaderCards;
    private DummyDev[][] boardDevCard;
    private DummyMarket dummyMarket;
    private DummyFaithTrack faithTrack;

    public VirtualModel() {
        faithTrack = new DummyFaithTrack();
        playerBoard = new DummyPlayerBoard();
        leaderCards = new ArrayList<>();
        boardDevCard = new DummyDev[Constants.DEV_ROWS][Constants.DEV_COLS];
    }

    public static void main(String[] args) throws JsonFileNotFoundException {
        VirtualModel virtualModel = new VirtualModel();
        virtualModel.initialize();
        virtualModel.showFaithTrack();
        virtualModel.showWarewouse();
        virtualModel.showStrongbox();
        virtualModel.showPlayerDevCards();
        virtualModel.showDevCards();
        virtualModel.showLeaderCards();


    }


    //TODO DA CANCELLARE, SOLO PER TESTARE CLI
    public void initialize() throws JsonFileNotFoundException {
        //faith track
        String path = "/json/faithtrack.json";
        Gson gson = new Gson();
        Reader reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));
        this.playerBoard.setFaithTrack(new DummyFaithTrack(gson.fromJson(reader, DummyCell[].class)));
        String path1 = "/json/test/testdevelopment.json";

        //development card market
        Reader reader1 = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path1)));
        DevelopmentCard[] developmentCard = gson.fromJson(reader1, DevelopmentCard[].class);
        DummyDev[][] dummyDevs = new DummyDev[Constants.DEV_ROWS][Constants.DEV_COLS];
        int ind = 0;
        for (int i = 0; i < Constants.DEV_ROWS; i++) {
            for (int j = 0; j < Constants.DEV_COLS; j++) {
                dummyDevs[i][j] = developmentCard[ind].getDummy();
                ind++;
            }
        }
        this.boardDevCard = dummyDevs;
        //development card slots
        path = "/json/test/testslots.json";
        reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));

        developmentCard = gson.fromJson(reader, DevelopmentCard[].class);
        DummyDev[] dummyDevs1 = new DummyDev[Constants.DEV_SLOTS];
        for (int i = 0; i < Constants.DEV_SLOTS; i++) {
            dummyDevs1[i] = developmentCard[i].getDummy();
        }
        this.playerBoard.setDevSections(dummyDevs1);
        //leader cards
        ArrayList<LeaderCard> leaderCards = LeaderCardParser.parseLeadCards();
        for (int i = 0; i < 2; i++) {
            this.leaderCards.add(leaderCards.get(i).getDummy());
        }

        //market
        MarketTray marketTray = new MarketTray();
        this.dummyMarket = marketTray.getDummy();

        //dummy depots warehouse (tutti depot pieni + 1 extradepot di tipo coin vuoto)
        path = "/json/test/testwarehouse.json";
        reader = new BufferedReader(new InputStreamReader(
                JsonObject.class.getResourceAsStream(path)));
        this.playerBoard.setWareHouse(gson.fromJson(reader, DummyWareHouse.class));
        DummyExtraDepot dummyExtraDepot = new DummyExtraDepot(4, 2, new ArrayList<>(), "COIN");
        this.playerBoard.getWareHouse().setExtraDepot1(dummyExtraDepot);
        System.out.println(gson.toJson(this.playerBoard.getWareHouse()));

        //white marble power, extra production power e discount power e strongbox
        ArrayList<String> resources = new ArrayList<>();
        resources.add("COIN");
        resources.add("SERVANT");
        DummyExtraProduction[] dummyExtraProduction = new DummyExtraProduction[]{new DummyExtraProduction(resources, 1)};
        this.playerBoard.setExtraProduction(dummyExtraProduction);
        this.playerBoard.setWhiteMarblePower(resources);
        resources.add("SHIELD");
        this.playerBoard.setDiscountedResources(resources);
        resources.add("SHIELD");
        resources.add("SHIELD");
        this.playerBoard.setStrongBox(new DummyStrongbox(resources));
        System.out.println(gson.toJson(this.playerBoard));


    }

    /**
     * This method displays the leadercards
     */
    public void showLeaderCards() {
        for (int i = 0; i < leaderCards.size(); i++) {
            DummyLeaderCard card = leaderCards.get(i);
            String cards =
                    "\n" + ANSI_YELLOW
                            + " CardID: "
                            + ANSI_RESET
                            + card.getId()
                            + ANSI_BLUE
                            + "\n Effect:"
                            + ANSI_RESET
                            + card.getEffect()
                            + ANSI_PURPLE
                            + "\n Requirements:"
                            + ANSI_RESET
                            + card.getRequirements()
                            + ANSI_GREEN
                            + "\n ActiveState: "
                            + ANSI_RESET
                            + card.isActive()
                            + ANSI_CYAN
                            + "\n VictoryPoints:"
                            + ANSI_RESET
                            + card.getVictoryPoints() + "\n";

            System.out.println(cards + "\n");
        }
    }

    /**displays development Cards on the board */
    public void showDevCards() {
        for (int i = 0; i < MARKET_ROWS; i++) {
            for (int j = 0; j < MARKET_COLS ; j++) {
                DummyDev card = boardDevCard[i][j];
                String cards =  showDevColor(i,j) 
                                + "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐\n"
                                + " CardID: "
                                + card.getId()
                                + "\n Color:"
                                + card.getColor()
                                + "\n Level:"
                                + card.getLevel()
                                + "\n Production Power: "
                                + card.getProductionPower() + "\n"
                                + "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"
                                + ANSI_RESET;
                System.out.println(cards + "\n");

            }
        }
    }

       /** This method colors the background based on the development card color*/
    public String showDevColor(int i, int j){
        String card = new String();
        switch (boardDevCard[i][j].getColor()) {
            case ("YELLOW"):
                 card = ANSI_BG_YELLOW;
                break;
            case ("PURPLE"):
                card = ANSI_BG_PURPLE;
                break;
            case ("BLUE"):
                card = ANSI_BG_BLUE;
                break;
            case ("GREEN"):
                card = ANSI_BG_GREEN;
                break;
        }
        return card;
    }


    public void showPlayerDevCards(){
         for (int i = 0; i < playerBoard.getDevSections().length; i++) {
             DummyDev devCards= playerBoard.getDevSections()[i];
              String cards =   showDevColor(i,0)
                     + "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐\n"
                     + " CardID: "
                     + devCards.getId()
                     + "\n Color:"
                     + devCards.getColor()
                     + "\n Level:"
                     + devCards.getLevel()
                     + "\n Production Power: "
                     + devCards.getProductionPower() + "\n"
                     + "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"
                     + ANSI_RESET;
             System.out.println(cards + "\n");
         }

    }


    /**
     * This method colors the player faithtrack based on his position and base
     **/
    private String getPlayerColor() {
        StringBuilder color = new StringBuilder();
        color.append("│  ");
        for (int i = 0; i < 10; i++) {
            if (playerBoard.getFaithMarker() == i) {
                if (playerBoard.getFaithTrack().isPopeSpace(i)) {
                    color.append(" ");
                    color.append(ANSI_BG_RED + "│ " + PLAYER + "  │" + ANSI_RESET);
                } else if (playerBoard.getFaithTrack().getReportSection(i) != 0) {
                    color.append(" ");
                    color.append(ANSI_BG_YELLOW + "│ " + PLAYER + "  │" + ANSI_RESET);
                } else {
                    color.append(" ");
                    color.append("│ " + PLAYER + "  │");
                }
            } else if (playerBoard.getFaithTrack().isPopeSpace(i)) {
                color.append(" ");
                color.append(ANSI_BG_RED + "│ " + i + "  │" + ANSI_RESET);
            } else if (playerBoard.getFaithTrack().getReportSection(i) != 0) {
                color.append(" ");
                color.append(ANSI_BG_YELLOW + "│ " + i + "  │" + ANSI_RESET);

            } else {
                color.append(" ");
                color.append("│ " + i + "  │");
            }
        }

        for (int i = 10; i < 25; i++) {
            if (playerBoard.getFaithMarker() == i) {
                if (playerBoard.getFaithTrack().isPopeSpace(i)) {
                    color.append(" ");
                    color.append(ANSI_BG_RED + "│ " + PLAYER + "  │" + ANSI_RESET);
                } else if (playerBoard.getFaithTrack().getReportSection(i) != 0) {
                    color.append(" ");
                    color.append(ANSI_BG_YELLOW + "│ " + PLAYER + "  │" + ANSI_RESET);
                }
            } else if (playerBoard.getFaithTrack().isPopeSpace(i)) {
                color.append(" ");
                color.append(ANSI_BG_RED + "│ " + i + "  │" + ANSI_RESET);
            } else if (playerBoard.getFaithTrack().getReportSection(i) != 0) {
                color.append(" ");
                color.append(ANSI_BG_YELLOW + "│ " + i + "  │" + ANSI_RESET);

            } else {
                color.append(" ");
                color.append("│ " + i + "  │");
            }
        }
        color.append("  │");

        return color.toString();
    }


    /**
     * This method displays each player's faithtrack
     */
    public void showFaithTrack() {
        String s = getPlayerColor();
        String track1 = "┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐\n" +
                "│   ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐  │\n";

        String track2 = "│   └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘ └─────┘  │\n" +
                "└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘\n";

        System.out.println(track1 + s + "\n" + track2);

    }

    /**
     * This method along with showResExtraDepot() displays the player's ExtraDepot if he has one
     */
    public void showExtraDepot() {
        String E1 = showResExtraDepot1();
        String E2 = showResExtraDepot2();

        String track1 = "     ┌────────────────┐\n" +
                "     │┌─────┐  ┌─────┐│\n";

        String track2 = "    │└─────┘  └─────┘│\n" +
                "     └────────────────┘\n";
        if (playerBoard.getWareHouse().getExtraDepot1().getId() != -1)
            System.out.println(track1 + E1 + track2);
        if (playerBoard.getWareHouse().getExtraDepot2().getId() != -1)
            System.out.println(track1 + E2 + track2);
    }


    private String showResExtraDepot1() {
        StringBuilder resE1 = new StringBuilder();
        String res1= " ";
        String res2 = " ";
        if( !playerBoard.getWareHouse().getExtraDepot1().getResources().isEmpty()) {
            res1 = playerBoard.getWareHouse().getExtraDepot1().getResources().get(0);
            if (playerBoard.getWareHouse().getExtraDepot1().getResources().size() == 2)
                res2 = playerBoard.getWareHouse().getDepot1().getResources().get(1);

        }
        resE1.append(" ");
        resE1.append("    ││  " + showResource(res1) + "  │  │  " + showResource(res2) + "  ││\n ");


        return resE1.toString();
    }

    private String showResExtraDepot2() {
        StringBuilder resE2 = new StringBuilder();
        String res1 = " ";
        String res2 = " ";
            if (!playerBoard.getWareHouse().getExtraDepot2().getResources().isEmpty()) {
                res1 = playerBoard.getWareHouse().getExtraDepot2().getResources().get(0);
                if (playerBoard.getWareHouse().getExtraDepot2().getResources().size() == 2)
                    res2 = playerBoard.getWareHouse().getExtraDepot2().getResources().get(1);
            }

        resE2.append(" ");
        resE2.append("    ││  " + showResource(res1) + "  │  │  " + showResource(res2) + "  ││\n ");


        return resE2.toString();

    }




    /**
     * This method shows the player's warehouse, and his extradepots if he has them
     */
    public void showWarewouse() {
        String resD1 = showResDep1();
        String resD2 = showResDep2();
        String resD3 = showResDep3();

        String w = "        ┌─────────┐\n" +
                "        │ ┌─────┐ │\n";

        String y = "     ┌──┘ └─────┘ └───┐\n" +
                "     │┌─────┐  ┌─────┐│\n";

        String z = "┌───┘└─────┘  └─────┘└───┐\n" +
                " │┌─────┐ ┌─────┐  ┌─────┐│\n";

        String x = "│└─────┘ └─────┘  └─────┘│\n" +
                " └────────────────────────┘\n";
        System.out.println(w + resD1 + y + resD2 + z + resD3 + x);
        showExtraDepot();
    }

    /**
     * This method displays the resources in depot 1
     */
    private String showResDep1() {
        StringBuilder resD1 = new StringBuilder();
        String res1 = " ";
        if(!playerBoard.getWareHouse().getDepot1().getResources().isEmpty())
             res1 = playerBoard.getWareHouse().getDepot1().getResources().get(0);

         resD1.append(" ");
         resD1.append("       │ │  " + showResource(res1) + "  │ │\n");


        return resD1.toString();
    }


    /**
     * This method displays the resources in depot 2
     */
    private String showResDep2() {
        StringBuilder resD2 = new StringBuilder();
        String res1 = " ";
        String res2 = " ";
        if(!playerBoard.getWareHouse().getDepot2().getResources().isEmpty()) {
            res1 = playerBoard.getWareHouse().getDepot2().getResources().get(0);
            if (playerBoard.getWareHouse().getDepot2().getResources().size() == 2)
                res2 = playerBoard.getWareHouse().getDepot2().getResources().get(1);
        }

        resD2.append(" ");
        resD2.append("    ││  " + showResource(res1) + "  │  │  " + showResource(res2) + "  ││\n ");

        return resD2.toString();
    }

    /**
     * This method displays the resources in depot 3
     */
    private String showResDep3() {
        String res1 = " ";
        String res2 = " ";
        String res3 = " ";
        StringBuilder resD3 = new StringBuilder();
        if(!playerBoard.getWareHouse().getDepot3().getResources().isEmpty()) {
            res1 = playerBoard.getWareHouse().getDepot3().getResources().get(0);
            if (playerBoard.getWareHouse().getDepot3().getResources().size() == 2)
                res2 = playerBoard.getWareHouse().getDepot3().getResources().get(1);
            if (playerBoard.getWareHouse().getDepot3().getResources().size() == 3)
                res2 = playerBoard.getWareHouse().getDepot3().getResources().get(1);
                res3 = playerBoard.getWareHouse().getDepot3().getResources().get(2);
        }
        resD3.append(" ");
        resD3.append("││  " + showResource(res1) + "  │ │  " + showResource(res2) + "  │  │  " + showResource(res3) + "  ││\n ");

        return resD3.toString();

    }


    public DummyPlayerBoard getPlayerBoard() {
        return this.playerBoard;
    }

    public void setLeaderCard(DummyLeaderCard[] card) {
        ArrayList<DummyLeaderCard> dummyLeaderCards = new ArrayList<>(Arrays.asList(card));
        this.leaderCards = dummyLeaderCards;
    }

    public ArrayList<DummyLeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void removeLeaderCard(int pos) {
        leaderCards.remove(pos);
    }

    public void setBoardDevCard(DummyDev[][] cards) {
        this.boardDevCard = cards;
    }

    /**
     * This method displays the market and its marbles
     */
    public void showMarket() {
        StringBuilder r1 = new StringBuilder();
        StringBuilder r2 = new StringBuilder();
        StringBuilder r3 = new StringBuilder();
        String marbles[][] = dummyMarket.getDummyMarbles();
        r1.append(" ");
        r1.append("││  " + showMarble(marbles[0][0]) + "  ││  " + showMarble(marbles[0][1]) + "  ││  " + showMarble(marbles[0][2]) + "  ││  " + showMarble(marbles[0][3]) + "  ││\n ");

        r2.append(" ");
        r2.append("││  " + showMarble(marbles[1][0]) + "  ││  " + showMarble(marbles[1][1]) + "  ││  " + showMarble(marbles[1][2]) + "  ││  " + showMarble(marbles[1][3]) + "  ││\n ");
        r3.append(" ");
        r3.append("││  " + showMarble(marbles[2][0]) + "  ││  " + showMarble(marbles[2][1]) + "  ││  " + showMarble(marbles[2][2]) + "  ││  " + showMarble(marbles[2][3]) + "  ││ \n ");
        r1.toString();
        r2.toString();
        r3.toString();


        String x = " ┌────────────────────────────┐\n" +
                " │┌─────┐┌─────┐┌─────┐┌─────┐│\n";

        String y = "│└─────┘└─────┘└─────┘└─────┘│\n";

        String z = " │┌─────┐┌─────┐┌─────┐┌─────┐│\n";

        String w = "│└─────┘└─────┘└─────┘└─────┘│\n" +
                " │┌─────┐┌─────┐┌─────┐┌─────┐│\n";

        String h = "│└─────┘└─────┘└─────┘└─────┘│\n" +
                " └────────────────────────────┘\n";
        System.out.println(x + r1 + y + z + r2 + w + r3 + h);

    }


    public void setDummyMarket(DummyMarket market) {
        this.dummyMarket = market;
    }

    /**
     * This method displays a resource
     */
    public String showResource(String res) {
        String resType = new String();
        switch (res) {
            case ("COIN"):
                resType = ANSI_YELLOW + RES + ANSI_RESET;
                break;
            case ("SHIELD"):
                resType = ANSI_PURPLE + RES + ANSI_RESET;
                break;
            case ("SERVANT"):
                resType = ANSI_BLUE + RES + ANSI_RESET;
                break;
            case ("STONE"):
                resType = ANSI_WHITE + RES + ANSI_RESET;
                break;
            case(" "):
                resType = " ";
                break;

        }
        return resType;
    }


    /**
     * This method displays a marble
     */
    public String showMarble(String res) {
        String MarbleType = "";
        switch (res) {
            case ("YELLOW"):
                MarbleType = ANSI_YELLOW + MARBLE + ANSI_RESET;
                break;
            case ("PURPLE"):
                MarbleType = ANSI_PURPLE + MARBLE + ANSI_RESET;
                break;
            case ("BLUE"):
                MarbleType = ANSI_BLUE + MARBLE + ANSI_RESET;
                break;
            case ("GREY"):
                MarbleType = ANSI_WHITE + MARBLE + ANSI_RESET;
                break;
            case ("WHITE"):
                MarbleType = MARBLE;
                break;
            case ("RED"):
                MarbleType = ANSI_RED + MARBLE + ANSI_RESET;
                break;

        }
        return MarbleType;
    }

    /**
     * Displays the player's strongbox
     */
    public void showStrongbox() {
        int size = playerBoard.getStrongBox().getResources().size();

        for (int i = 0; i < size; i++) {
                System.out.print( showResource(playerBoard.getStrongBox().getResources().get(i))+ " "  );

        }
        System.out.println("\n");
    }


}

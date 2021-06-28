package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.DummyModel.DummyLeaderCard;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;


/** @author Alessandra Atria
 * this class represents the player's board
 */
public class Board extends ViewObservable {
    @FXML
    public Button payBtn;
    public Button confirm;
    public Button confirm3;
    public Button confirm2;
    public Button DiscardResource;
    public Button ViewButton;
    public Button EndTButton;
    public Button MarketButton;
    public Button rearrangew;

    private String type;
    private VirtualModel virtualModel;
    public Button LeaderActiveButton;
    public ArrayList<ImageView> faithMarker;
    Gson gson = new Gson();
    int id;
    ArrayList<Integer> ids;
    int ledid;
    int i, j, count, z;
    ArrayList<String> res;
    String [] command1;
    String [] command;

    @FXML
    public ImageView servantbp, stonebp, shieldbp, coinbp;
    @FXML
    public ImageView servantbp1, stonebp1, shieldbp1, coinbp1;
    @FXML
    public Label bplabel;

    @FXML
    public ImageView bpback;


    @FXML
    Label ssh, sc, sv, sst;

    @FXML
    public ImageView res1, res2, res3,res4,res5,res6;

    @FXML
    public ImageView p0, p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24;

    @FXML
    public ImageView led1;
    @FXML
    public ImageView led2;

    @FXML
    public ImageView e1, e2, e3, e4;


    @FXML
    public ImageView devb1, devb2, devb3;

    @FXML
    Button DiscardButton, sl1, sl2, sl3;

    @FXML
    Button DevButton;

    @FXML
    Button activeLp, activatedp, activateB ;
    @FXML
    public ImageView pope2, pope3, pope4;

    @FXML
    public Label victoryPoints;


    public Board() {
        this.virtualModel = new VirtualModel();
        this.faithMarker = new ArrayList<>();
        this.ids= new ArrayList<>();
        ledid = 0;
        id=0;
        i=0;
        j=0;
        z=0;
        command1 = new String[3];
        command = new String[2];
    }

 /** this methods sets all the image positions of the player on the faith track */
    @FXML
    public void initialize() {
        p0.setVisible(false);
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
        p5.setVisible(false);
        p6.setVisible(false);
        p7.setVisible(false);
        p8.setVisible(false);
        p9.setVisible(false);
        p10.setVisible(false);
        p11.setVisible(false);
        p12.setVisible(false);
        p13.setVisible(false);
        p14.setVisible(false);
        p15.setVisible(false);
        p16.setVisible(false);
        p17.setVisible(false);
        p18.setVisible(false);
        p19.setVisible(false);
        p20.setVisible(false);
        p21.setVisible(false);
        p22.setVisible(false);
        p23.setVisible(false);
        p23.setVisible(false);
        p24.setVisible(false);

        faithMarker.add(p0);
        faithMarker.add(p1);
        faithMarker.add(p2);
        faithMarker.add(p3);
        faithMarker.add(p4);
        faithMarker.add(p5);
        faithMarker.add(p6);
        faithMarker.add(p7);
        faithMarker.add(p8);
        faithMarker.add(p9);
        faithMarker.add(p10);
        faithMarker.add(p11);
        faithMarker.add(p12);
        faithMarker.add(p13);
        faithMarker.add(p14);
        faithMarker.add(p15);
        faithMarker.add(p16);
        faithMarker.add(p17);
        faithMarker.add(p18);
        faithMarker.add(p19);
        faithMarker.add(p20);
        faithMarker.add(p21);
        faithMarker.add(p22);
        faithMarker.add(p23);
        faithMarker.add(p24);
    }




    /** this method is used to choose where to insert the development cards bought from the market*/
    @FXML
    public void devslot1() {
        Message messageSlot = new SlotChoice(0);
        notifyObserver(obs -> obs.onReadyReply(messageSlot));
    }

    /** this method is used to choose where to insert the development cards bought from the market*/
    @FXML
    public void devslot2() {
        Message messageSlot = new SlotChoice(1);
        notifyObserver(obs -> obs.onReadyReply(messageSlot));
    }

    /** this method is used to choose where to insert the development cards bought from the market*/
    @FXML
    public void devslot3() {
        Message messageSlot = new SlotChoice(2);
        notifyObserver(obs -> obs.onReadyReply(messageSlot));
    }




    /** this method is used to show the current position on the faith track*/
    @FXML
    public void updateFaithTrack() {
        for(int i=0; i<24; i++){
            faithMarker.get(i).setVisible(false);
        }
        int p = virtualModel.getPlayerBoard().getFaithMarker();
        faithMarker.get(p).setVisible(true);
    }



    /** opens a scene where the player can choose which leader cards he wants to discard */
    @FXML
    public void DiscardLeaderCards() {
        DiscarLeaderCards f = new DiscarLeaderCards();
        Platform.runLater(() -> GUIRunnable.FirstScene(f, observers).setLeaderCards(virtualModel));

    }




    /** opens a scene where the player can choose which development cards he wants to buy */
    @FXML
    public void buyDev() {
        notifyObserver(obs -> obs.setTurnPhase(TurnPhase.BUY_DEV));
        DevelopmentMarket deck = new DevelopmentMarket();
        Platform.runLater(() -> GUIRunnable.buyDevCard(deck, observers).setDevCards(virtualModel));
    }

    /** sets slots choice buttons visible and disables the other buttons */
    public void setButtons(){
        sl1.setVisible(true);
        sl2.setVisible(true);
        sl3.setVisible(true);
        DiscardButton.setDisable(true);
        DevButton.setDisable(true);
        DiscardResource.setDisable(true);
        ViewButton.setDisable(true);
        EndTButton.setDisable(true);
        MarketButton.setDisable(true);
        LeaderActiveButton.setDisable(true);
        rearrangew.setDisable(true);

    }

    /** sets slots choice buttons not visible*/
    public void disable(){
        sl1.setVisible(false);
        sl2.setVisible(false);
        sl3.setVisible(false);

    }

    /** when the players starts a production all the actions buttons are disabled*/
    public void setPayBtn(){
        payBtn.setVisible(true);
        DiscardButton.setDisable(true);
        DevButton.setDisable(true);
        DiscardResource.setDisable(true);
        ViewButton.setDisable(true);
        EndTButton.setDisable(true);
        MarketButton.setDisable(true);
        LeaderActiveButton.setDisable(true);
        rearrangew.setDisable(true);

    }

    public void setPBtn(){
        DiscardButton.setDisable(true);
        DevButton.setDisable(true);
        DiscardResource.setDisable(true);
        ViewButton.setDisable(true);
        EndTButton.setDisable(true);
        MarketButton.setDisable(true);
        activateB.setDisable(true);
        activeLp.setDisable(true);
        activatedp.setDisable(true);
        LeaderActiveButton.setDisable(true);
        rearrangew.setDisable(true);

    }



    /** opens a scene where the player can choose how to rearrange his warehouse */
    @FXML
    public void rearrangeWarehouse(){
        RearrangeWarehouse r = new RearrangeWarehouse();
        Platform.runLater(() -> {
            try {
                GUIRunnable.moveFromDepot(r, observers).setWarehouse(virtualModel);
            } catch (JsonFileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    /** opens a scene where the player can choose which resources he wants to buy */
    @FXML
    public void buyfromMarket(ActionEvent actionEvent) {
        Market market = new Market();
        Platform.runLater(() -> GUIRunnable.buyMarket(market, observers).setMarket(virtualModel));
    }




    /** opens a scene where the player can choose which leader cards he wants to activate*/
    @FXML
    public void ActivateLeader(ActionEvent actionEvent) {
        DiscarLeaderCards f = new DiscarLeaderCards();
        Platform.runLater(() -> GUIRunnable.FirstScene(f, observers).setLeaderCards(virtualModel));
    }




    /** This method actives basic production  */
    @FXML
    public void activateBasicProducion(ActionEvent actionEvent) {
        bpback.setOpacity(1);
        bplabel.setOpacity(1);
        stonebp.setOpacity(1);
        stonebp.setDisable(false);
        shieldbp.setOpacity(1);
        shieldbp.setDisable(false);
        servantbp.setDisable(false);
        servantbp.setOpacity(1);
        coinbp.setOpacity(1);
        coinbp.setDisable(false);
        notifyObserver(obs -> obs.setTurnPhase(TurnPhase.ACTIVATE_PRODUCTION));
        setPBtn();

    }

    /** This method actives development cards production  */
    @FXML
    public void activateDevProducion(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.setTurnPhase(TurnPhase.ACTIVATE_PRODUCTION));
        confirm.setOpacity(1);
        confirm.setDisable(false);
        devb1.setDisable(false);
        devb2.setDisable(false);
        devb3.setDisable(false);
        setPBtn();
    }

    /** This method actives leader cards production  */
    @FXML
    public void activateLeaderProducion(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.setTurnPhase(TurnPhase.ACTIVATE_PRODUCTION));
        setPBtn();
    }

    /** on image click the player choose this development card to start the production*/
    @FXML
    public void pick1(){
        ids.add(i, virtualModel.getPlayerBoard().getDevSections()[0].getId());
        devb1.setDisable(true);
        devb1.setOpacity(0.5);
    }

    /** on image click the player choose this development card to start the production*/
    @FXML
    public void pick2(){
        ids.add(i, virtualModel.getPlayerBoard().getDevSections()[1].getId());
        devb2.setDisable(true);
        devb2.setOpacity(0.5);
    }

    /** on image click the player choose this development card to start the production*/
    @FXML
    public void pick3(){
        ids.add(virtualModel.getPlayerBoard().getDevSections()[2].getId());
        devb3.setDisable(true);
        devb3.setOpacity(0.5);
    }

    /** on image click the player choose this leader card to start the production*/
    @FXML
    public void pickL1(){
        ledid = virtualModel.getLeaderCards().get(0).getId();
        command[0] = Integer.toString(ledid);
        led1.setDisable(true);
        led1.setOpacity(0.5);
        led2.setDisable(true);
        bpback.setOpacity(1);
        bplabel.setOpacity(1);
        stonebp1.setOpacity(1);
        stonebp1.setDisable(false);
        shieldbp1.setOpacity(1);
        shieldbp1.setDisable(false);
        servantbp1.setDisable(false);
        servantbp1.setOpacity(1);
        coinbp1.setOpacity(1);
        coinbp1.setDisable(false);
    }

    /** on image click the player choose this leader card to start the production*/
    @FXML
    public void pickL2(){
        ledid = virtualModel.getLeaderCards().get(1).getId() ;
        command[0] = Integer.toString(ledid);
        led2.setDisable(true);
        led1.setDisable(true);
        led2.setOpacity(0.5);
        bpback.setOpacity(1);
        bplabel.setOpacity(1);
        stonebp1.setOpacity(1);
        stonebp1.setDisable(false);
        shieldbp1.setOpacity(1);
        shieldbp1.setDisable(false);
        servantbp1.setDisable(false);
        servantbp1.setOpacity(1);
        coinbp1.setOpacity(1);
        coinbp1.setDisable(false);
    }

    /** on image click the player choose these resources to start the production*/
    public void pickServant(MouseEvent mouseEvent) {
        command1[z] = "SERVANT";
        servantbp.setOpacity(0.5);
        z++;
        if(z == 3) {
            confirm2.setDisable(false);
            confirm2.setOpacity(1);

        }
    }

    public void pickCoin(MouseEvent mouseEvent) {
        command1[z] = "COIN";
        coinbp.setOpacity(0.5);
        z++;
        if(z == 3) {
            confirm2.setDisable(false);
            confirm2.setOpacity(1);

        }
    }

    public void pickShield(MouseEvent mouseEvent) {
        command1[z] = "SHIELD";
        shieldbp.setOpacity(0.5);
        z++;
        if(z == 3) {
            confirm2.setDisable(false);
            confirm2.setOpacity(1);
        }
    }

    public void pickStone(MouseEvent mouseEvent) {
        command1[z] = "STONE";
        stonebp.setOpacity(0.5);
        z++;
        if(z == 3) {
            confirm2.setDisable(false);
            confirm2.setOpacity(1);
        }
    }



    /** on image click the player choose these resources to start the production*/
    public void pickServantL(MouseEvent mouseEvent) {
        command[1] = "SERVANT";
        servantbp1.setOpacity(0.5);
        confirm3.setOpacity(1);
        confirm3.setDisable(false);
        coinbp1.setDisable(true);
        shieldbp1.setDisable(true);
        stonebp1.setDisable(true);
    }

    public void pickCoinL(MouseEvent mouseEvent) {
        command[1] = "COIN";
        coinbp1.setOpacity(0.5);
        confirm3.setOpacity(1);
        confirm3.setDisable(false);
        servantbp1.setDisable(true);
        shieldbp1.setDisable(true);
        stonebp1.setDisable(true);
    }

    public void pickShieldL(MouseEvent mouseEvent) {
        shieldbp1.setOpacity(0.5);
        command[1] = "SHIELD";
        confirm3.setOpacity(1);
        confirm3.setDisable(false);
        coinbp1.setDisable(true);
        servantbp1.setDisable(true);
        stonebp1.setDisable(true);

    }

    public void pickStoneL(MouseEvent mouseEvent) {
        command[1] = "STONE";
        stonebp1.setOpacity(0.5);
        coinbp1.setDisable(true);
        servantbp1.setDisable(true);
        shieldbp1.setDisable(true);
        confirm3.setOpacity(1);
        confirm3.setDisable(false);


    }





    /** To confirm to activate basic production  */
    @FXML
    public void okbtnBp(){
        Message message = new ActivateBaseProd(command1);
        notifyObserver(obs -> obs.onReadyReply(message));

    }


    /** To confirm to activate leader production  */
    @FXML
    public void okbtnL(){
        Message message = new ExtraProductionToActivate(Integer.parseInt(command[0]) , command[1]);
        notifyObserver(obs -> obs.onReadyReply(message));
    }



    /** To confirm to basic production  */
    @FXML
    public void okbtnDp(){
        int i = 0;
        int[] id = new int[ids.size()];
        for(int j: ids){
            id[i] = j;
            i++;
        }
        Message message = new ActivateDevProd(id);
        notifyObserver(obs -> obs.onReadyReply(message));
    }


    public void setPay(ActionEvent actionEvent) {
        AddtoWarehouse c = new AddtoWarehouse();
        Platform.runLater(() -> GUIRunnable.payWithRes(c, observers, res, virtualModel));
    }

   /** to pay everything the player wants to activate  */
    public  void setPay(String[] toPay){
        count = toPay.length;
        res = new ArrayList<>();
        res.addAll(Arrays.asList(toPay));
        payBtn.setDisable(false);
    }

    /** this method is used ends the player's turn */
    @FXML
    public void EndTurn(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new EndTurn()));
    }


    /** this method is used to see other players' boards*/
    @FXML
    public void SeeOthers(ActionEvent actionEvent) {
        QuestionGame q = new QuestionGame();
        Platform.runLater(() -> GUIRunnable.questionGame(q, observers).setVirtualModel(virtualModel));
    }


    /** opens a scene where the player can choose which resources he wants to discard */
    @FXML
    public void discardRes(ActionEvent actionEvent) {
        DiscardResource f = new DiscardResource();
        Platform.runLater(() -> GUIRunnable.discRes(f, observers).setRes(virtualModel));
    }



 /** this method sets the board with its current cards and resources */
    public void setVirtualModel(VirtualModel virtualModel) {
        this.virtualModel = virtualModel;


        //to set the warehouse
        if (virtualModel.getSlot1() != "") {
            Image i1 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot1() + ".png"));
            res1.setImage(i1);
            res1.setOpacity(1);
        }
        if (virtualModel.getSlot2() != "") {
            Image i2 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot2() + ".png"));
            res2.setImage(i2);
            res2.setOpacity(1);
        }
        if (virtualModel.getSlot3() != "") {
            Image i3 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot3() + ".png"));
            res3.setImage(i3);
            res3.setOpacity(1);
        }
        if (virtualModel.getSlot4() != "") {
            Image i4 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot4() + ".png"));
            res4.setImage(i4);
            res4.setOpacity(1);
        }
        if (virtualModel.getSlot5() != "") {
            Image i5 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot5() + ".png"));
            res5.setImage(i5);
            res5.setOpacity(1);
        }
        if (virtualModel.getSlot6() != "") {
            Image i6 = new Image(getClass().getResourceAsStream("/PunchBoard/" + virtualModel.getSlot6() + ".png"));
            res6.setImage(i6);
            res6.setOpacity(1);
        }


        if (virtualModel.getPlayerBoard().getDevSections()[0] != null) {
                Image im1 = new Image(getClass().getResourceAsStream("/CardsFront/devCard" + virtualModel.getPlayerBoard().getDevSections()[0].getId() + ".png"));
                devb1.setImage(im1);
                devb1.setOpacity(1);
        }
        if (virtualModel.getPlayerBoard().getDevSections()[1] != null) {
                Image im2 = new Image((getClass().getResourceAsStream("/CardsFront/devCard" + virtualModel.getPlayerBoard().getDevSections()[1].getId() + ".png")));
                devb2.setImage(im2);
                devb2.setOpacity(1);
        }
        if (virtualModel.getPlayerBoard().getDevSections()[2] != null) {
                Image im3 = new Image((getClass().getResourceAsStream("/CardsFront/devCard" + virtualModel.getPlayerBoard().getDevSections()[2].getId() + ".png")));
                devb3.setImage(im3);
                devb3.setOpacity(1);
        }
        if(virtualModel.getLeaderCards().get(0)!= null) {
            if (virtualModel.getLeaderCards().get(0).isActive()) {
                Image image1 = new Image((getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(0).getId() + ".png")));
                led1.setImage(image1);
            }
        }
        if(virtualModel.getLeaderCards().get(1)!= null){
            if(virtualModel.getLeaderCards().get(1).isActive()) {
                Image image2 = new Image((getClass().getResourceAsStream("/CardsFront/led" + virtualModel.getLeaderCards().get(1).getId() + ".png")));
                led2.setImage(image2);
            }

              if(!virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().isEmpty()) {
                  type = virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResourceType();
                  Image ex1 = null;
                  switch (type) {
                      case ("COIN"):
                          ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/COIN.png")));
                          break;
                      case ("STONE"):
                          ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/STONE.png")));
                          break;

                      case ("SERVANT"):
                          ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/SERVANT.png")));
                          break;

                      case ("SHIELD"):
                          ex1 = new Image((getClass().getResourceAsStream("/PunchBoard/SHIELD.png")));
                          break;

                  }
                  DummyLeaderCard lc = virtualModel.getLeaderCards().get(0);


                  if(lc.getEffectName().equals("EXTRA_SLOT") && lc.getType().equals(type)){
                      e1.setImage(ex1);
                      e1.setOpacity(1);
                      if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() == 2) {
                          e2.setImage(ex1);
                          e2.setOpacity(1);
                      }
                  }else{
                      e3.setImage(ex1);
                      e3.setOpacity(1);
                      if(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot1().getResources().size() == 2)
                      { e4.setImage(ex1);
                          e4.setOpacity(1);}
                  }

              }

            if(!virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().isEmpty()){
                type =virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResourceType();
                Image ex21 = null;
                switch(type){
                    case("COIN"):
                        ex21 = new Image((getClass().getResourceAsStream("/PunchBoard/COIN.png")));
                        break;

                    case("STONE"):
                        ex21 = new Image((getClass().getResourceAsStream("/PunchBoard/STONE.png")));

                        break;
                    case("SERVANT"):
                        ex21 = new Image((getClass().getResourceAsStream("/PunchBoard/SERVANT.png")));

                        break;

                    case("SHIELD"):
                        ex21 = new Image((getClass().getResourceAsStream("/PunchBoard/SHIELD.png")));
                        break;

                }
                DummyLeaderCard lc = virtualModel.getLeaderCards().get(0);

                if(lc.getEffectName().equals("EXTRA_SLOT") && lc.getType().equals(type)){
                    e1.setImage(ex21);
                    e1.setOpacity(1);
                    if (virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() == 2) {
                        e2.setImage(ex21);
                        e2.setOpacity(1);
                    }
                }else{
                    e3.setImage(ex21);
                    e3.setOpacity(1);
                    if(virtualModel.getPlayerBoard().getWareHouse().getExtraDepot2().getResources().size() == 2)
                    { e4.setImage(ex21);
                        e4.setOpacity(1);}
                }

            }


        }
        confirm.setDisable(true);
        confirm2.setDisable(true);
        confirm3.setDisable(true);
        devb1.setDisable(true);
        devb2.setDisable(true);
        bplabel.setDisable(true);
        bpback.setDisable(true);
        coinbp.setDisable(true);
        servantbp.setDisable(true);
        shieldbp.setDisable(true);
        stonebp.setDisable(true);
        coinbp1.setDisable(true);
        servantbp1.setDisable(true);
        shieldbp1.setDisable(true);
        stonebp1.setDisable(true);
        int coin =0;
        int stone =0;
        int servant=0;
        int shield=0;
        for (int i=0; i<virtualModel.getPlayerBoard().getStrongBox().getResources().size(); i++) {
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("COIN"))
                    coin++;
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("STONE"))
                    stone++;
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("SHIELD"))
                    shield++;
            if (virtualModel.getPlayerBoard().getStrongBox().getResources().get(i).equals("SERVANT"))
                    servant++;
        }
        sc.setText("x"+ coin);
        sst.setText("x"+ stone);
        ssh.setText("x"+ shield);
        sv.setText("x"+ servant);

        victoryPoints.setText(Integer.toString(virtualModel.getVictoryPoints()));

    }




}
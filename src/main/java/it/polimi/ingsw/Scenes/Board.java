package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.enumerations.TurnPhase;
import it.polimi.ingsw.exceptions.JsonFileNotFoundException;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;


/** @author Alessandra Atria
 * this class represents the player's board
 */
public class Board extends ViewObservable {
    int coin;
    int stone;
    int servant;
    int shield;
    public Button payBtn;
    public Button confirm;
    public Button confirm3;
    public Button confirm2;
    private VirtualModel virtualModel;
    public Button LeaderActiveButton;
    public ArrayList<ImageView> faithMarker;
    Gson gson = new Gson();
    int id;
    ArrayList<Integer> ids;
    ArrayList<Integer> ledid;
    int i, j, count;
    String[] res;

    @FXML
    Label ssh, sc, sv;

    @FXML
    public ImageView res1, res2, res3,res4,res5,res6;

    @FXML
    public ImageView p0, p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24;

    @FXML
    public ImageView led1;
    @FXML
    public ImageView led2;

    @FXML
    public ImageView devb1, devb2, devb3;

    @FXML
    Button DiscardButton, sl1, sl2, sl3;

    @FXML
    Button DevButton;


    public Board() {
        this.virtualModel = new VirtualModel();
        this.faithMarker = new ArrayList<>();
        this.ids= new ArrayList<>();
        this.ledid = new ArrayList<>();
        this.res = new String[count];
        id=0;
        i=0;
        j=0;
        coin=0;
        shield=0;
        stone=0;
        servant=0;


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
        Message messageSlot = new Message(MessageType.SLOT_CHOICE, gson.toJson(0));
        notifyObserver(obs -> obs.onReadyReply(messageSlot));
    }

    /** this method is used to choose where to insert the development cards bought from the market*/
    @FXML
    public void devslot2() {
        Message messageSlot = new Message(MessageType.SLOT_CHOICE, gson.toJson(1));
        notifyObserver(obs -> obs.onReadyReply(messageSlot));
    }

    /** this method is used to choose where to insert the development cards bought from the market*/
    @FXML
    public void devslot3() {
        Message messageSlot = new Message(MessageType.SLOT_CHOICE, gson.toJson(2));
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

    /** sets slots choice buttons visible */
    public void setButtons(){
        sl1.setVisible(true);
        sl2.setVisible(true);
        sl3.setVisible(true);

    }

    /** sets slots choice buttons not visible*/
    public void disable(){
        sl1.setVisible(false);
        sl2.setVisible(false);
        sl3.setVisible(false);

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
        confirm2.setOpacity(1);
        confirm2.setDisable(false);
        notifyObserver(obs -> obs.setTurnPhase(TurnPhase.ACTIVATE_PRODUCTION));

    }

    /** This method actives development cards production  */
    @FXML
    public void activateDevProducion(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.setTurnPhase(TurnPhase.ACTIVATE_PRODUCTION));
        confirm.setOpacity(1);
        confirm.setDisable(false);
        devb1.setDisable(false);
        devb2.setDisable(false);
        devb3.setDisable(false);}

    /** This method actives leader cards production  */
    @FXML
    public void activateLeaderProducion(ActionEvent actionEvent) {
        confirm3.setOpacity(1);
        confirm3.setDisable(false);
        notifyObserver(obs -> obs.setTurnPhase(TurnPhase.ACTIVATE_PRODUCTION));
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
        ledid.add(j, virtualModel.getLeaderCards().get(0).getId());
        led1.setDisable(true);
        led1.setOpacity(0.5);
    }

    /** on image click the player choose this leader card to start the production*/
    @FXML
    public void pickL2(){
        ledid.add(j, virtualModel.getLeaderCards().get(0).getId()) ;
        led2.setDisable(true);
        led2.setOpacity(0.5);
    }


    /** To confirm to activate basic production  */
    @FXML
    public void okbtnBp(){
        Message message = new Message(MessageType.ACTIVATE_PRODUCTION, gson.toJson(ids));
        notifyObserver(obs -> obs.onReadyReply(message));
    }


    /** To confirm to activate leader production  */
    @FXML
    public void okbtnL(){
        Message message = new Message(MessageType.ACTIVATE_PRODUCTION, gson.toJson(ids));
        notifyObserver(obs -> obs.onReadyReply(message));
    }



    /** To confirm to basic production  */
    @FXML
    public void okbtnDp(){

        Message message = new Message(MessageType.ACTIVATE_PRODUCTION, gson.toJson(ids));
        notifyObserver(obs -> obs.onReadyReply(message));
    }


    public void setPay(ActionEvent actionEvent) {
        AddtoWarehouse c = new AddtoWarehouse();
        Platform.runLater(() -> GUIRunnable.payWithRes(c, observers, res, virtualModel));
    }

   /** to pay everything the player wants to activate  */
    public  void setPay(String[] toPay){
        count = toPay.length;
        res = toPay;
        payBtn.setVisible(true);
        payBtn.setDisable(false);
    }

    /** this method is used ends the player's turn */
    @FXML
    public void EndTurn(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.END_TURN, "")));
    }


    /** this method is used to see other players' boards*/
    @FXML
    public void SeeOthers(ActionEvent actionEvent) {
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
        if(virtualModel.getLeaderCards().get(0)!= null){
              if(virtualModel.getLeaderCards().get(0).isActive()) {
                  Image image1 = new Image((getClass().getResourceAsStream("/CardsFront/devCard" + virtualModel.getLeaderCards().get(0).getId() + ".png")));
                  led1.setImage(image1);
              }
        }
        if(virtualModel.getLeaderCards().get(1)!= null){
              if(virtualModel.getLeaderCards().get(1).isActive()) {
                Image image2 = new Image((getClass().getResourceAsStream("/CardsFront/devCard" + virtualModel.getLeaderCards().get(1).getId() + ".png")));
                led2.setImage(image2);
              }
        }
        confirm.setDisable(true);
        confirm2.setDisable(true);
        confirm3.setDisable(true);
        devb1.setDisable(true);
        devb2.setDisable(true);


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
        //sst.setText("x"+ stone);
        ssh.setText("x"+ shield);
        sv.setText("x"+ servant);
        }




}
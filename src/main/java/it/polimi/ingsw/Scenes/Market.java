package it.polimi.ingsw.Scenes;

import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import static it.polimi.ingsw.enumerations.Constants.*;
import static it.polimi.ingsw.enumerations.Constants.ANSI_RESET;

public class Market extends ViewObservable {
    Gson gson = new Gson();
    public ImageView slide;
    public ImageView b00;
    public ImageView b01;
    public ImageView b02;
    public ImageView b03;
    public ImageView b10;
    public ImageView b11;
    public ImageView b12;
    public ImageView b13;
    public ImageView b20;
    public ImageView b21;
    public ImageView b22;
    public ImageView b23;




    private VirtualModel virtualModel;




        public Market() {
            this.virtualModel = new VirtualModel();
        }


        @FXML
        public void initialize() {

        }


        public void setMarket(VirtualModel virtualModel) {
            this.virtualModel = virtualModel;
            String[][] marbles = virtualModel.getDummyMarbles();
            String sliding = virtualModel.getSlindig();
            Image img00 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[0][0] + ".png"));
            b00.setImage(img00);
            Image img01 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[0][1] + ".png"));
            b01.setImage(img01);
            Image img02 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[0][2] + ".png"));
            b02.setImage(img02);
            Image img03 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[0][3] + ".png"));
            b03.setImage(img03);
            Image img10 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[1][0] + ".png"));
            b10.setImage(img10);
            Image img11 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[1][1] + ".png"));
            b11.setImage(img11);
            Image img12 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[1][2] + ".png"));
            b12.setImage(img12);
            Image img13 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[1][3] + ".png"));
            b13.setImage(img13);
            Image img20 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[2][0] + ".png"));
            b20.setImage(img20);
            Image img21 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[1][0] + ".png"));
            b21.setImage(img21);
            Image img22 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[2][2] + ".png"));
            b22.setImage(img22);
            Image img23 = new Image(getClass().getResourceAsStream("/PunchBoard/" + marbles[2][3] + ".png"));
            b23.setImage(img23);
            Image out = new Image(getClass().getResourceAsStream("/PunchBoard/" + sliding + ".png"));
            slide.setImage(out);


        }


    public void getrow1(ActionEvent actionEvent) {
        String[] payload = new String[2];
        String r = "row";
        payload[0] = (r);
        payload[1] = (Integer.toString(0));

        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void getrow2(ActionEvent actionEvent) {
        String[] payload = new String[2];
        String r = "row";
        payload[0] = (r);
        payload[1] = (Integer.toString(1));

        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }
    public void getrow3(ActionEvent actionEvent) {
        String[] payload = new String[2];
        String r = "row";
        payload[0] = (r);
        payload[1] = (Integer.toString(2));

        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void getcol1(ActionEvent actionEvent) {
        String[] payload = new String[2];
        String c = "col";
        payload[0] = (c);
        payload[1] = (Integer.toString(0));

        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }

    public void getcol2(ActionEvent actionEvent) {
        String[] payload = new String[2];
        String c = "col";
        payload[0] = (c);
        payload[1] = (Integer.toString(1));

        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }
    public void getcol3(ActionEvent actionEvent) {
        String[] payload = new String[2];
        String c = "col";
        payload[0] = (c);
        payload[1] = (Integer.toString(2));

        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }
    public void getcol4(ActionEvent actionEvent) {
        String[] payload = new String[2];
        String c = "col";
        payload[0] = (c);
        payload[1] = (Integer.toString(3));

        Message message = new Message(MessageType.BUY_MARKET, gson.toJson(payload));
        notifyObserver(obs -> obs.onReadyReply(message));
    }


    public void exit(ActionEvent actionEvent) {
        Board bc = new Board();
        GUIRunnable.changetoStart(bc, observers);

    }




}

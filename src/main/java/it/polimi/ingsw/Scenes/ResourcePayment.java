package it.polimi.ingsw.Scenes;
import com.google.gson.Gson;
import it.polimi.ingsw.Gui.GUIRunnable;
import it.polimi.ingsw.client.VirtualModel;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.observers.ViewObservable;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class ResourcePayment extends ViewObservable {
    Gson gson = new Gson();

    public void disc1(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.REMOVE_RESOURCES, gson.toJson(1))));
    }

    public void disc2(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.REMOVE_RESOURCES, gson.toJson(2))));
    }

    public void disc3(ActionEvent actionEvent) {
        notifyObserver(obs -> obs.onReadyReply(new Message(MessageType.REMOVE_RESOURCES, gson.toJson(3))));
    }

    public void back(ActionEvent actionEvent) {
        Board bc = new Board();
        Platform.runLater(() -> GUIRunnable.changetoStart(bc, observers));
    }
}

package it.polimi.ingsw.utility;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.enumerations.Constants;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.nio.file.Files;

public class Persistence {


    public void store(GameController gameController){

        try (FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + Constants.SAVED_GAME)) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(gameController);
            Server.LOGGER.info("Game Saved.");


        } catch(NotSerializableException ex) {
           Server.LOGGER.severe("Not serializable class");
        } catch (IOException e) {
            Server.LOGGER.severe("Couldn't save");
        }
    }

    public GameController restore()  {
       GameController gameController;

            try (FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + Constants.SAVED_GAME)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                gameController = (GameController) objectInputStream.readObject();

                return gameController;

            } catch (IOException e) {
                Server.LOGGER.severe("No File Found.");
            } catch (ClassNotFoundException e) {
                Server.LOGGER.severe("Couldn't restore");
            }
            return null;
    }

    public void delete(){
        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + Constants.SAVED_GAME);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            Server.LOGGER.severe("Failed to delete file.");
        }
    }
}

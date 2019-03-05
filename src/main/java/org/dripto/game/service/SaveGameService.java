package org.dripto.game.service;

import org.dripto.game.game.SaveGameState;
import org.dripto.game.util.GameConstants;

import java.io.*;

public enum SaveGameService {
    INSTANCE;
    public void saveGame(SaveGameState save) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GameConstants.SAVE_NAME))) {
            oos.writeObject(save);
        }
    }
    public SaveGameState loadGame() throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GameConstants.SAVE_NAME))) {
           return (SaveGameState) ois.readObject();
        }
    }
}

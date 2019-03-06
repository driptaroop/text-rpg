package org.dripto.game.service;

import org.dripto.game.game.SaveGameState;

import java.io.IOException;

public interface SaveGameService {
    void saveGame(SaveGameState save) throws IOException;

    SaveGameState loadGame() throws IOException, ClassNotFoundException;
}

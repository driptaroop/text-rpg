package org.dripto.game.game;

import org.dripto.game.exception.ExitGameException;
import org.dripto.game.service.ConsoleUIService;
import org.dripto.game.service.DefaultConsoleUIService;

public class GameManager {

    ConsoleUIService consoleUIService = new DefaultConsoleUIService();

    public void init(){
        try {
            consoleUIService.init();
        } catch (ExitGameException e) {
            System.err.println(e.getMessage());
            this.exitGame();
        }
    }

    private void exitGame() {
        System.exit(-1);
    }
}

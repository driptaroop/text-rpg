package org.dripto.game.game;

import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.PlayerDiedException;
import org.dripto.game.service.ConsoleUIService;
import org.dripto.game.service.impl.DefaultConsoleUIService;
import org.dripto.game.util.GameMessagePrinter;

import java.io.IOException;

public class GameManager {

    ConsoleUIService consoleUIService = DefaultConsoleUIService.INSTANCE;

    GameMessagePrinter printer = GameMessagePrinter.INSTANCE;
    public void init(){
        try {
            printer.printAscii("dnd");
            consoleUIService.init();
        } catch (ExitGameException | PlayerDiedException e) {
            System.err.println(e.getMessage());
            this.exitGame();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("error" + e.getMessage());
            exitGame();
        }
    }

    private void exitGame() {
        System.exit(-1);
    }
}

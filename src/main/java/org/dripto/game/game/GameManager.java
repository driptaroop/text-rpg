package org.dripto.game.game;

import org.dripto.game.service.ConsoleUIService;
import org.dripto.game.service.DefaultConsoleUIService;

public class GameManager {

    ConsoleUIService consoleUIService = new DefaultConsoleUIService();

    public void init(){
        consoleUIService.init();
    }
}

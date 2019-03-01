package org.dripto.game.service;

import org.dripto.game.characters.Player;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.map.Dungeon;

public class DefaultConsoleUIService implements ConsoleUIService {

    GameMessagePrinter printer = GameMessagePrinter.getInstance();
    GameInput input = GameInput.getInstance();
    CharacterCreatorService characterCreatorService = CharacterCreatorService.getInstance();
    Player player = null;

    @Override
    public void init(){
        //showMainMenu();
        drawMap();
    }

    private void drawMap() {
        Dungeon dungeon = new Dungeon();
        System.out.println(dungeon);
    }

    private void showMainMenu(){
        switch (input.readInput("welcome main_menu")){
            case "1":
                newGame();
                break;
            default:
                break;
        }
    }

    private void newGame() {
        printer.printMessages("new_game");
        player = characterCreatorService.createCharacter();
        System.out.println(player);
    }
}

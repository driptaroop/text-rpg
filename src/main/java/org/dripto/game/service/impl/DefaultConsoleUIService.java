package org.dripto.game.service.impl;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.PlayerDiedException;
import org.dripto.game.util.GameInput;
import org.dripto.game.util.GameMessagePrinter;
import org.dripto.game.game.SaveGameState;
import org.dripto.game.game.StartGameStatus;
import org.dripto.game.map.Dungeon;
import org.dripto.game.map.Room;
import org.dripto.game.service.CharacterCreatorService;
import org.dripto.game.service.ConsoleUIService;
import org.dripto.game.service.ExploreService;
import org.dripto.game.service.SaveGameService;
import org.dripto.game.util.ConsoleColors;

import java.io.IOException;
import java.util.Set;

public enum DefaultConsoleUIService implements ConsoleUIService {

    INSTANCE;

    GameMessagePrinter printer = GameMessagePrinter.INSTANCE;
    GameInput input = GameInput.INSTANCE;
    CharacterCreatorService characterCreatorService = DefaultCharacterCreatorService.INSTANCE;
    Player player = null;
    Set<Monster> monsters;
    Dungeon dungeon;
    ExploreService exploreService = DefaultExploreService.INSTANCE;
    SaveGameService saveGameService = DefaultSaveGameService.INSTANCE;

    @Override
    public void init() throws ExitGameException, PlayerDiedException, IOException, ClassNotFoundException {
        StartGameStatus status = showMainMenu();
        switch (status){
            case NEW:
                newGame();
                initMap();
                break;
            case RESUME:
                SaveGameState save = saveGameService.loadGame();
                dungeon = save.getDungeon();
                player = save.getPlayer();
                monsters = save.getMonster();
                printer.printMessageFormatter("load_game", ConsoleColors.PURPLE, player.getName());
                dungeon.showMap();
                break;
        }
        this.exploreService.explore(dungeon, player, monsters);
        printer.printMessageFormatter("game_complete_msg", ConsoleColors.PURPLE_BACKGROUND_BRIGHT, player.getName(), player.getName());
    }

    void initMap() {
        this.dungeon = new Dungeon();
        addCharactersToMap(dungeon, player, monsters);
        dungeon.showMap();
    }

    private void addCharactersToMap(Dungeon dungeon, Player player, Set<Monster> monsters) {
        placePlayer(dungeon, player);
        placeMonsters(dungeon, monsters);
    }

    private void placeMonsters(Dungeon dungeon, Set<Monster> monsters) {
        for(Monster m : monsters) {
            if(m.isAlive()) {
                Room room = dungeon.getEmptyRoom();
                m.setToRoom(dungeon, room.getX(), room.getY());
            }
        }
    }

    private void placePlayer(Dungeon dungeon, Player player) {
        player.setToRoom(dungeon, 0, 5);
    }

    StartGameStatus showMainMenu() throws ExitGameException {
        switch (input.readInput("welcome main_menu", ConsoleColors.YELLOW_UNDERLINED)){
            case "1":
                return StartGameStatus.NEW;
            case "2":
                return StartGameStatus.RESUME;
            default:
                throw new ExitGameException(printer.getMessage("exit_game"));
        }
    }

    void newGame() {
        printer.printMessages("new_game", ConsoleColors.CYAN_UNDERLINED);
        player = characterCreatorService.createCharacter();
        printer.printAscii("welcome");
        printer.printMessageFormatter("player_creation_welcome", ConsoleColors.GREEN_BOLD_BRIGHT, player.getName());
        monsters = characterCreatorService.getMonsters();
    }
}

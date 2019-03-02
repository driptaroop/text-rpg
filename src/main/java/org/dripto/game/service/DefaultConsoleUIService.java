package org.dripto.game.service;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.map.Dungeon;
import org.dripto.game.map.Room;

import java.util.Set;

public class DefaultConsoleUIService implements ConsoleUIService {

    GameMessagePrinter printer = GameMessagePrinter.getInstance();
    GameInput input = GameInput.getInstance();
    CharacterCreatorService characterCreatorService = CharacterCreatorService.getInstance();
    Player player = null;
    Set<Monster> monsters;
    Dungeon dungeon;
    private ExploreService exploreService = DefaultExploreService.getInstance();

    @Override
    public void init() throws ExitGameException {
        showMainMenu();
        initMap();
        this.exploreService.explore(dungeon, player);
    }

    private void initMap() {
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
        monsters = characterCreatorService.getMonsters();
        System.out.println(player);
    }
}

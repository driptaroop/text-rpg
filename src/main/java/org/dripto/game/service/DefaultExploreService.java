package org.dripto.game.service;

import org.dripto.game.characters.Player;
import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.map.Directions;
import org.dripto.game.map.Dungeon;
import org.dripto.game.util.GameConstants;

public class DefaultExploreService implements ExploreService {

    private static DefaultExploreService mInstance;
    GameMessagePrinter printer = GameMessagePrinter.getInstance();
    GameInput input = GameInput.getInstance();

    private DefaultExploreService() {
    }

    public static DefaultExploreService getInstance() {
        if (mInstance == null) {
            mInstance = new DefaultExploreService();
        }
        return mInstance;
    }

    @Override
    public void explore(Dungeon dungeon, Player player) throws CharacterOutOfBoundsException {
        Directions direction = showExplorationMenu();
        player.move(direction, dungeon, GameConstants.STEP_SIZE);
        dungeon.showMap();
    }

    private Directions showExplorationMenu() {
        String ex = input.readInput("exploration_menu");
        System.out.println(ex);
        switch (ex){
            case "w":
                return Directions.NORTH;
            case "a":
                return Directions.WEST;
            case "s":
                return Directions.SOUTH;
            case "d":
                return Directions.EAST;
            default:
                System.out.println("incorrect");
                return null;
        }
    }
}

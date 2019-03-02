package org.dripto.game.service;

import org.dripto.game.characters.NPC;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.WrongChoiceException;
import org.dripto.game.fight.FightStatus;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.map.Directions;
import org.dripto.game.map.Dungeon;
import org.dripto.game.util.GameConstants;

public class DefaultExploreService implements ExploreService {

    private static DefaultExploreService mInstance;
    GameMessagePrinter printer = GameMessagePrinter.getInstance();
    GameInput input = GameInput.getInstance();
    DefaultFightingService fightingService = new DefaultFightingService();

    private DefaultExploreService() {
    }

    public static DefaultExploreService getInstance() {
        if (mInstance == null) {
            mInstance = new DefaultExploreService();
        }
        return mInstance;
    }

    @Override
    public void explore(Dungeon dungeon, Player player) throws ExitGameException {
        boolean explore = true;
        while (explore) {
            try {
                Directions direction = showExplorationMenu();
                player.move(direction, dungeon, GameConstants.STEP_SIZE);
                NPC enemy = player.inConflictWith();
                if(enemy != null){
                    FightStatus fightStatus = fightingService.initiateFight(player, enemy);
                    System.out.println(fightStatus);
                }
            }catch (WrongChoiceException e){
                System.err.println(e.getMessage());
            } catch (CharacterOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
            dungeon.showMap();
        }
    }

    private Directions showExplorationMenu() throws WrongChoiceException, ExitGameException {
        switch (input.readInput("exploration_menu")){
            case "w":
                return Directions.NORTH;
            case "a":
                return Directions.WEST;
            case "s":
                return Directions.SOUTH;
            case "d":
                return Directions.EAST;
            case "quit":
                throw new ExitGameException(printer.getMessage("exit_game"));
            default:
                throw new WrongChoiceException(printer.getMessage("incorrect_choice"));
        }
    }
}

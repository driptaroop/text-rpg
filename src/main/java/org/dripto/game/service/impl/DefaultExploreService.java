package org.dripto.game.service.impl;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.NPC;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.PlayerDiedException;
import org.dripto.game.exception.WrongChoiceException;
import org.dripto.game.fight.FightResult;
import org.dripto.game.util.GameInput;
import org.dripto.game.util.GameMessagePrinter;
import org.dripto.game.game.SaveGameState;
import org.dripto.game.map.Dungeon;
import org.dripto.game.map.Explore;
import org.dripto.game.service.ExploreService;
import org.dripto.game.service.FightingService;
import org.dripto.game.service.SaveGameService;
import org.dripto.game.util.ConsoleColors;
import org.dripto.game.util.GameConstants;

import java.io.IOException;
import java.util.Set;

public enum DefaultExploreService implements ExploreService {
    INSTANCE;

    GameMessagePrinter printer = GameMessagePrinter.INSTANCE;
    GameInput input = GameInput.INSTANCE;
    FightingService fightingService = DefaultFightingService.INSTANCE;
    SaveGameService saveGameService = DefaultSaveGameService.INSTANCE;

    @Override
    public void explore(Dungeon dungeon, Player player, Set<Monster> monsters) throws ExitGameException, PlayerDiedException, IOException {
        boolean explore = true;
        while (explore) {
            try {
                int lastx = player.getRoom().getX();
                int lasty = player.getRoom().getY();
                Explore direction = showExplorationMenu();
                if(direction == Explore.SAVE){
                    SaveGameState save = new SaveGameState(dungeon, player, monsters);
                    saveGameService.saveGame(save);
                    printer.printMessage("save_game", ConsoleColors.YELLOW);
                }else {
                    player.move(direction, dungeon, GameConstants.STEP_SIZE);
                    NPC enemy = player.inConflictWith();
                    if (enemy != null) {
                        FightResult fightResult = fightingService.initiateFight(player, enemy);
                        switch (fightResult) {
                            case WON:
                                printer.printMessageFormatter("win_msg", ConsoleColors.GREEN_BOLD, player.getName(), enemy.getName(),
                                        Integer.toString(fightingService.gainExperience(player, enemy)));
                                explore = !checkIfWon(monsters);
                                break;
                            case FLEE:
                                printer.printMessage("flee_msg", ConsoleColors.RED);
                                player.setToRoom(dungeon, lastx, lasty);
                                break;
                            case DIED:
                                printer.printMessage("die_msg", ConsoleColors.RED_BOLD_BRIGHT);
                                throw new PlayerDiedException("YOU DIED");
                        }
                    }
                }
            }catch (WrongChoiceException | CharacterOutOfBoundsException e){
                printer.printString(e.getMessage(), ConsoleColors.RED);
            }
            dungeon.showMap();
        }
    }

    boolean checkIfWon(Set<Monster> monsters) {
        for(Monster monster : monsters){
            if(monster.isAlive())
                return false;
        }
        return true;
    }

    Explore showExplorationMenu() throws WrongChoiceException, ExitGameException {
        switch (input.readInput("exploration_menu", ConsoleColors.CYAN)){
            case "w":
                return Explore.NORTH;
            case "a":
                return Explore.WEST;
            case "s":
                return Explore.SOUTH;
            case "d":
                return Explore.EAST;
            case "9":
                return Explore.SAVE;
            case "0":
                throw new ExitGameException(printer.getMessage("exit_game"));
            default:
                throw new WrongChoiceException(printer.getMessage("incorrect_choice"));
        }
    }
}

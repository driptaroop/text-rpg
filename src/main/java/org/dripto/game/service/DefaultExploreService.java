package org.dripto.game.service;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.NPC;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.PlayerDiedException;
import org.dripto.game.exception.WrongChoiceException;
import org.dripto.game.fight.FightResult;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.game.SaveGameState;
import org.dripto.game.map.Dungeon;
import org.dripto.game.map.Explore;
import org.dripto.game.util.GameConstants;

import java.io.IOException;
import java.util.Set;

public class DefaultExploreService implements ExploreService {

    GameMessagePrinter printer = GameMessagePrinter.getInstance();
    GameInput input = GameInput.getInstance();
    DefaultFightingService fightingService = new DefaultFightingService();

    public DefaultExploreService() {
    }

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
                    SaveGameService.INSTANCE.saveGame(save);
                    printer.printMessage("save_game");
                }else {
                    player.move(direction, dungeon, GameConstants.STEP_SIZE);
                    NPC enemy = player.inConflictWith();
                    if (enemy != null) {
                        FightResult fightResult = fightingService.initiateFight(player, enemy);
                        switch (fightResult) {
                            case WON:
                                printer.printMessageFormatter("win_msg", player.getName(), enemy.getName(),
                                        Integer.toString(fightingService.gainExperience(player, enemy)));
                                explore = !checkIfWon(monsters);
                                break;
                            case FLEE:
                                printer.printMessage("flee_msg");
                                player.setToRoom(dungeon, lastx, lasty);
                                break;
                            case DIED:
                                printer.printMessage("die_msg");
                                throw new PlayerDiedException("YOU DIED");
                        }
                    }
                }
            }catch (WrongChoiceException e){
                System.err.println(e.getMessage());
            } catch (CharacterOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
            dungeon.showMap();
        }
    }

    private boolean checkIfWon(Set<Monster> monsters) {
        for(Monster monster : monsters){
            if(monster.isAlive())
                return false;
        }
        return true;
    }

    private Explore showExplorationMenu() throws WrongChoiceException, ExitGameException {
        switch (input.readInput("exploration_menu")){
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

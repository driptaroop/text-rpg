package org.dripto.game.service;

import org.dripto.game.characters.GameCharacters;
import org.dripto.game.characters.NPC;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.WrongChoiceException;
import org.dripto.game.fight.FightStatus;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.util.Gameutils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultFightingService {

    GameMessagePrinter printer = GameMessagePrinter.getInstance();
    GameInput gameInput = GameInput.getInstance();

    public FightStatus initiateFight(Player player, NPC enemy) {
        printer.printMessageFormatter("fight_initiate", enemy.getName());
        return fight(resolveInitiative(player, enemy));
    }

    private FightStatus fight(List<GameCharacters> order) {
        while(true){
            for(int i = 0; i < order.size(); i++) {
                if(order.get(i) instanceof Player) {
                    NPC npc = (NPC) order.get(1 - i);
                    Player player = (Player) order.get(i);
                    Optional<FightStatus> result = playerFights(player, npc);
                    if(result.isPresent()) {
                        return result.get();
                    }
                }
                else {
                    NPC npc = (NPC) order.get(i);
                    Player player = (Player) order.get(1-i);
                    int damage = npc.attacks(player);
                    printer.printMessageFormatter("fight_damage", npc.getName(), player.getName(), Integer.toString(damage));
                    if(player.isDead()) {
                        return FightStatus.DIED;
                    }
                }
            }
        }
    }

    Optional<FightStatus> playerFights(Player player, NPC npc){
        boolean invalidInput;
        do {
            invalidInput = false;
            try {
                switch (gameInput.readInput("fight_menu")) {
                    case "1":
                        int damage = player.attacks(npc);
                        printer.printMessageFormatter("fight_damage", player.getName(), npc.getName(), Integer.toString(damage));
                        if (npc.isDead()) {
                            player.resetHp();
                            return Optional.ofNullable(FightStatus.WON);
                        }
                        break;
                    case "2":
                        return Optional.ofNullable(FightStatus.FLEE);
                    default:
                        throw new WrongChoiceException(printer.getMessage("incorrect_choice"));
                }
            }catch (WrongChoiceException e) {
                invalidInput = true;
            }
        }while (invalidInput);
        return Optional.ofNullable(null);
    }

    private List<GameCharacters> resolveInitiative(Player player, NPC enemy){
        int playerInitiative = Gameutils.getRandomWithinRange(0, player.getLuck());
        int enemyInitiative = Gameutils.getRandomWithinRange(0, enemy.getLuck());
        if(playerInitiative > enemyInitiative) {
            printer.printMessage("player_initiative_first");
            return Arrays.asList(player, enemy);
        }
        else {
            printer.printMessageFormatter("NPC_initiative_first", enemy.getName());
            return Arrays.asList(enemy, player);
        }
    }
}

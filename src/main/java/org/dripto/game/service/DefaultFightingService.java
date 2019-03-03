package org.dripto.game.service;

import org.dripto.game.characters.GameCharacters;
import org.dripto.game.characters.NPC;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.WrongChoiceException;
import org.dripto.game.fight.FightResult;
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

    public FightResult initiateFight(Player player, NPC enemy) {
        printer.printMessageFormatter("fight_initiate", enemy.getName());
        FightResult result = fight(resolveInitiative(player, enemy));
        return result;
    }

    public int gainExperience(Player player, NPC enemy) {
        int exp = calculateExperience(player, enemy);
        player.addExperience(exp);
        return exp;
    }

    private int calculateExperience(Player player, NPC enemy) {
        //TODO: exp calc logic
        return 10;
    }

    private FightResult fight(List<GameCharacters> order) {
        while(true){
            for(int i = 0; i < order.size(); i++) {
                NPC npc;
                Player player;
                if(order.get(i) instanceof Player) {
                    npc = (NPC) order.get(1 - i);
                    player = (Player) order.get(i);
                    Optional<FightResult> result = playerFights(player, npc);
                    if(result.isPresent()) {
                        return result.get();
                    }
                }
                else {
                    npc = (NPC) order.get(i);
                    player = (Player) order.get(1-i);
                    int damage = npc.attacks(player);
                    printer.printMessageFormatter("fight_damage", npc.getName(), player.getName(), Integer.toString(damage));
                    if(player.isDead()) {
                        return FightResult.DIED;
                    }
                }
                printer.printString(FightStatus.getFightStatus(player, npc));
            }
        }
    }

    Optional<FightResult> playerFights(Player player, NPC npc){
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
                            player.loot(npc);
                            npc.remove();
                            return Optional.ofNullable(FightResult.WON);
                        }
                        break;
                    case "2":
                        return Optional.ofNullable(FightResult.FLEE);
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

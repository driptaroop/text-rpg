package org.dripto.game.service.impl;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.util.GameInput;
import org.dripto.game.util.GameMessagePrinter;
import org.dripto.game.items.Items;
import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;
import org.dripto.game.service.CharacterCreatorService;
import org.dripto.game.util.ConsoleColors;
import org.dripto.game.util.GameConstants;
import org.dripto.game.util.Gameutils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

public enum DefaultCharacterCreatorService implements CharacterCreatorService {

    INSTANCE;
    GameInput input = GameInput.INSTANCE;
    GameMessagePrinter printer = GameMessagePrinter.INSTANCE;

    @Override
    public Player createCharacter(){
        printer.printMessages("character_creation", ConsoleColors.GREEN);
        String name = input.readInput("cc_name", ConsoleColors.BLUE);
        String story = input.readInput("cc_story", ConsoleColors.BLUE);
        printer.printMessageFormatter("cc_skills_msg", ConsoleColors.GREEN, Integer.toString(GameConstants.ADDITIONAL_POINTS));
        boolean incorrectPointsDistribution = false;
        int health, attack, defense, luck;
        do {
            health = input.readIntegerInput("cc_health", ConsoleColors.BLUE);
            attack = input.readIntegerInput("cc_attack", ConsoleColors.BLUE);
            defense = input.readIntegerInput("cc_defense", ConsoleColors.BLUE);
            luck = input.readIntegerInput("cc_luck", ConsoleColors.BLUE);
            incorrectPointsDistribution = (health + attack + defense + luck) > GameConstants.ADDITIONAL_POINTS;
            if(incorrectPointsDistribution)
                printer.printMessage("incorrect_points", ConsoleColors.RED);
        }while (incorrectPointsDistribution);
        return new Player(
                name, health, attack, defense, null, null, luck, story, 0
        );
    }

    @Override
    public Set<Monster> getMonsters(){
        Set<Monster> monsters = new LinkedHashSet<>();
        IntStream.range(0, GameConstants.NUMBER_OF_MONSTERS)
                .forEachOrdered(v -> monsters.add(new Monster(
                        "Orc", Gameutils.getRandomWithinRange(0,2)
                        , Gameutils.getRandomWithinRange(2,4), Gameutils.getRandomWithinRange(1,2)
                        , (Weapon) getRandomItems(Weapon.class), (Shield) getRandomItems(Shield.class), Gameutils.getRandomWithinRange(0,1), false
                )));
        return monsters;
    }

    private Items getRandomItems(Class clazz) {
        int x = Gameutils.getRandomWithinRange(0, clazz.getEnumConstants().length - 1);
        return (Items) clazz.getEnumConstants()[x];
    }
}

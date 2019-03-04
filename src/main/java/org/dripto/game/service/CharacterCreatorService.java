package org.dripto.game.service;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.util.GameConstants;
import org.dripto.game.util.Gameutils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class CharacterCreatorService {

    private static CharacterCreatorService mInstance;
    GameInput input = GameInput.getInstance();
    GameMessagePrinter printer = GameMessagePrinter.getInstance();

    private CharacterCreatorService() {
    }

    public static CharacterCreatorService getInstance() {
        if (mInstance == null) {
            mInstance = new CharacterCreatorService();
        }
        return mInstance;
    }

    public Player createCharacter(){
        printer.printMessages("character_creation");
        String name = input.readInput("cc_name");
        String story = input.readInput("cc_story");
        printer.printMessageFormatter("cc_skills_msg", Integer.toString(GameConstants.ADDITIONAL_POINTS));
        boolean incorrectPointsDistribution = false;
        int health, attack, defense, luck;
        do {
            health = input.readIntegerInput("cc_health");
            attack = input.readIntegerInput("cc_attack");
            defense = input.readIntegerInput("cc_defense");
            luck = input.readIntegerInput("cc_luck");
            incorrectPointsDistribution = (health + attack + defense + luck) > GameConstants.ADDITIONAL_POINTS;
            if(incorrectPointsDistribution)
                printer.printMessage("incorrect_points");
        }while (incorrectPointsDistribution);
        return new Player(
                name, health, attack, defense, null, null, luck, story, 0
        );
    }

    public Set<Monster> getMonsters(){
        Set<Monster> monsters = new LinkedHashSet<>();
        IntStream.range(0, GameConstants.NUMBER_OF_MONSTERS)
                .forEachOrdered(v -> monsters.add(new Monster(
                        "Orc", Gameutils.getRandomWithinRange(0,2)
                        , Gameutils.getRandomWithinRange(2,4), Gameutils.getRandomWithinRange(1,2)
                        ,null, null, Gameutils.getRandomWithinRange(0,1), false
                )));
        return monsters;
    }
}

package org.dripto.game.service;

import org.dripto.game.characters.Player;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.GameMessagePrinter;

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
        printer.printMessages("cc_skills_msg");
        int health = input.readIntegerInput("cc_health");
        int attack = input.readIntegerInput("cc_attack");
        int defense = input.readIntegerInput("cc_defense");
        int luck = input.readIntegerInput("cc_luck");
        Player player = new Player(
                name, health, attack, defense, null, null, luck, story, 0
        );
        return player;
    }
}

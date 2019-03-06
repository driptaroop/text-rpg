package org.dripto.game.service;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;

import java.util.Set;

public interface CharacterCreatorService {
    Player createCharacter();

    Set<Monster> getMonsters();
}

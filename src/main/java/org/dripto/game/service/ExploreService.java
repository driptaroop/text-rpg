package org.dripto.game.service;

import org.dripto.game.characters.Player;
import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.map.Dungeon;

public interface ExploreService {
    void explore(Dungeon dungeon, Player player) throws ExitGameException;
}

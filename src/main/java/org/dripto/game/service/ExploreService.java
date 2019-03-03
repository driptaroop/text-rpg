package org.dripto.game.service;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.PlayerDiedException;
import org.dripto.game.map.Dungeon;

import java.util.Set;

public interface ExploreService {
    void explore(Dungeon dungeon, Player player, Set<Monster> monsters) throws ExitGameException, PlayerDiedException;
}

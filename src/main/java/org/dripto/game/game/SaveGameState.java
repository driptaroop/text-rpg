package org.dripto.game.game;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.map.Dungeon;

import java.io.Serializable;
import java.util.Set;

public class SaveGameState implements Serializable {
    Dungeon dungeon;
    Player player;
    Set<Monster> monster;

    public SaveGameState(Dungeon dungeon, Player player, Set<Monster> monster) {
        this.dungeon = dungeon;
        this.player = player;
        this.monster = monster;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<Monster> getMonster() {
        return monster;
    }
}

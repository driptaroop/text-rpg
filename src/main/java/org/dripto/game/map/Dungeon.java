package org.dripto.game.map;

import org.dripto.game.util.GameConstants;

public class Dungeon {
    private final Room[][] rooms;

    public Dungeon() {
        this.rooms = new Room[GameConstants.DUNGEON_SIZE][GameConstants.DUNGEON_SIZE];
        for(int i = 0; i < GameConstants.DUNGEON_SIZE; i++){
            for(int j = 0; j < GameConstants.DUNGEON_SIZE; j++){
                rooms[i][j] = new Room(i,j);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder map = new StringBuilder();
        for(int i = 0; i < GameConstants.DUNGEON_SIZE; i++){
            for(int j = 0; j < GameConstants.DUNGEON_SIZE; j++){
                map.append(rooms[i][j]);
            }
            map.append("\n");
        }
        return map.toString();
    }
}

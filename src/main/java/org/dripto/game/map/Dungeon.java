package org.dripto.game.map;

import org.dripto.game.util.GameConstants;
import org.dripto.game.util.Gameutils;

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

    public Room getEmptyRoom(){
        while(true){
            int x = Gameutils.getRandomWithinRange(0, GameConstants.DUNGEON_SIZE - 1);
            int y = Gameutils.getRandomWithinRange(0, GameConstants.DUNGEON_SIZE - 1);
            if(getRooms()[x][y].getCharactersInRoom().isEmpty())
                return getRooms()[x][y];
        }
    }

    public Room[][] getRooms() {
        return rooms;
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

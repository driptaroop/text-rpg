package org.dripto.game.map;

import org.dripto.game.characters.GameCharacters;
import org.dripto.game.characters.Player;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private final Set<GameCharacters> charactersInRoom = new HashSet<>();
    private int x;
    private int y;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Set<GameCharacters> getCharactersInRoom() {
        return charactersInRoom;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        if(charactersInRoom.isEmpty())
            return "|_ _|";
        else{
            for(GameCharacters gc : charactersInRoom){
                if(gc instanceof Player){
                    return "|_P_|";
                }
            }
            return "|_M_|";
        }
    }
}

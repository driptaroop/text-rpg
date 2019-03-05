package org.dripto.game.map;

import org.dripto.game.characters.GameCharacters;
import org.dripto.game.characters.Player;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Room implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;

        Room room = (Room) o;

        if (getX() != room.getX()) return false;
        if (getY() != room.getY()) return false;
        return getCharactersInRoom() != null ? getCharactersInRoom().equals(room.getCharactersInRoom()) : room.getCharactersInRoom() == null;
    }

    @Override
    public int hashCode() {
        int result = getCharactersInRoom() != null ? getCharactersInRoom().size() : 0;
        result = 31 * result + getX();
        result = 31 * result + getY();
        return result;
    }
}

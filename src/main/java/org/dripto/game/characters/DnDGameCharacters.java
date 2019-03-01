package org.dripto.game.characters;

import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;
import org.dripto.game.map.Directions;
import org.dripto.game.map.Dungeon;
import org.dripto.game.map.Room;
import org.dripto.game.util.GameConstants;

public abstract class DnDGameCharacters implements GameCharacters {

    private final String name;
    private int hp;
    private final int baseAttack;
    private final int baseDefense;
    private Weapon weapon;
    private Shield shield;
    private final int luck;
    private int baseHp = 5;
    private Room room;

    public DnDGameCharacters(String name, int hp, int baseAttack, int baseDefense, Weapon weapon, Shield shield, int luck) {
        this.name = name;
        this.hp = hp + baseHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.weapon = weapon;
        this.shield = shield;
        this.luck = luck;
    }

    public int getTotalAttack(){
        return baseAttack + weapon.getModifier();
    }

    public int getTotalDefense(){
        return baseDefense + shield.getModifier();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Shield getShield() {
        return shield;
    }

    public int getLuck() {
        return luck;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public void move(Directions direction, Dungeon map, int steps) throws CharacterOutOfBoundsException {
        int x = getRoom().getX();
        int y = getRoom().getY();
        getRoom().getCharactersInRoom().remove(this);
        switch (direction){
            case EAST:
                x -= steps;
                break;
            case WEST:
                x += steps;
                break;
            case NORTH:
                y += steps;
                break;
            case SOUTH:
                y -= steps;
                break;
        }
        if(x < 0 || y < 0 || x >= GameConstants.DUNGEON_SIZE || y >= GameConstants.DUNGEON_SIZE){
            throw new CharacterOutOfBoundsException();
        }
        setToRoom(map, x, y);
    }
    @Override
    public void setToRoom(Dungeon map, int x, int y){
        map.getRooms()[x][y].getCharactersInRoom().add(this);
        setRoom(map.getRooms()[x][y]);
    }

    @Override
    public String toString() {
        return "DnDGameCharacters{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", baseAttack=" + baseAttack +
                ", baseDefense=" + baseDefense +
                ", weapon=" + weapon +
                ", shield=" + shield +
                ", luck=" + luck +
                ", baseHp=" + baseHp +
                '}';
    }
}

package org.dripto.game.characters;

import org.dripto.game.exception.CharacterOutOfBoundsException;
import org.dripto.game.game.GameMessagePrinter;
import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;
import org.dripto.game.map.Explore;
import org.dripto.game.map.Dungeon;
import org.dripto.game.map.Room;
import org.dripto.game.util.GameConstants;

import java.io.Serializable;

public abstract class DnDGameCharacters implements GameCharacters, Serializable {

    private final String name;
    private int hp;
    private final int baseAttack;
    private final int baseDefense;
    private Weapon weapon;
    private Shield shield;
    private final int luck;
    private int fullHp;
    private Room room;

    private final transient GameMessagePrinter printer = GameMessagePrinter.getInstance();

    public DnDGameCharacters(String name, int hp, int baseAttack, int baseDefense, Weapon weapon, Shield shield, int luck) {
        this.name = name;
        this.hp = hp + GameConstants.BASE_HP;
        this.fullHp = this.hp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.weapon = weapon;
        this.shield = shield;
        this.luck = luck;
    }

    public int getTotalAttack(){
        int totalAttack = baseAttack;
        if(getWeapon() != null)
            totalAttack += getWeapon().getModifier();
        return totalAttack;
    }

    public int getTotalDefense(){
        int totalDefense = baseDefense;
        if(getShield() != null)
            totalDefense += shield.getModifier();
        return totalDefense;
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
    public void move(Explore direction, Dungeon map, int steps) throws CharacterOutOfBoundsException {
        int x = getRoom().getX();
        int y = getRoom().getY();
        switch (direction){
            case EAST:
                y += steps;
                break;
            case WEST:
                y -= steps;
                break;
            case NORTH:
                x -= steps;
                break;
            case SOUTH:
                x += steps;
                break;
        }
        if(x < 0 || y < 0 || x >= GameConstants.DUNGEON_SIZE || y >= GameConstants.DUNGEON_SIZE){
            throw new CharacterOutOfBoundsException(printer.getMessage("character_out_of_bounds"));
        }
        getRoom().getCharactersInRoom().remove(this);
        setToRoom(map, x, y);
    }
    @Override
    public void setToRoom(Dungeon map, int x, int y){
        if(getRoom() != null)
            map.getRooms()[getRoom().getX()][getRoom().getY()].getCharactersInRoom().remove(this);
        map.getRooms()[x][y].getCharactersInRoom().add(this);
        setRoom(map.getRooms()[x][y]);
    }

    @Override
    public int attacks(GameCharacters other){
        int attack = getTotalAttack();
        int otherDefense = other.getTotalDefense();

        int damage = attack - otherDefense;
        if (damage <= 0) damage = 0;

        other.damage(damage);
        return damage;
    }

    public void remove(){
        this.getRoom().getCharactersInRoom().remove(this);
        setRoom(null);
    }

    public void loot(GameCharacters other) {
        if(other.getWeapon() != null) {
            if (getWeapon() == null || getWeapon().getModifier() < other.getWeapon().getModifier()) {
                printer.printMessageFormatter("loot_msg", this.getName(), "weapon", other.getWeapon().getName(), other.getName());
                setWeapon(other.getWeapon());
            }
        }
        if(other.getShield() != null) {
            if (getShield() == null || getShield().getModifier() < other.getWeapon().getModifier()) {
                printer.printMessageFormatter("loot_msg", this.getName(), "shield", other.getWeapon().getName(), other.getName());
                setShield(other.getShield());
            }
        }
    }

    @Override
    public boolean isAlive(){
        return getHp() > 0;
    }

    @Override
    public void damage(int damage){
        setHp(getHp() - damage);
    }

    public NPC inConflictWith(){
        for(GameCharacters gameCharacters : getRoom().getCharactersInRoom()){
            if(gameCharacters instanceof NPC && ((NPC)gameCharacters).isHostile())
                return ((NPC)gameCharacters);
        }
        return null;
    }

    @Override
    public boolean isDead(){
        return !isAlive();
    }

    public int getFullHp() {
        return fullHp;
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
                ", fullHp=" + fullHp +
                '}';
    }

    public void resetHp(){
        setHp(getFullHp());
    }
}

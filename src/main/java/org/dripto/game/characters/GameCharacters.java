package org.dripto.game.characters;

import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;
import org.dripto.game.map.Room;

public interface GameCharacters {
    int getTotalAttack();

    int getTotalDefense();

    void setHp(int hp);

    void setWeapon(Weapon weapon);

    void setShield(Shield shield);

    String getName();

    int getHp();

    int getBaseAttack();

    int getBaseDefense();

    Weapon getWeapon();

    Shield getShield();

    int getLuck();

    Room getRoom();

    void setRoom(Room room);
}
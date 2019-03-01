package org.dripto.game.characters;

import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;

public abstract class NPC extends DnDGameCharacters {

    public NPC(String name, int hp, int baseAttack, int baseDefense,
               Weapon weapon, Shield shield, int luck) {
        super(name, hp, baseAttack, baseDefense, weapon, shield, luck);
    }

    public abstract boolean isHostile();

}

package org.dripto.game.characters;

import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;

public class Monster extends NPC {

    private boolean hostile;

    public Monster(String name, int hp, int baseAttack, int baseDefense,
                   Weapon weapon, Shield shield, int luck) {
        super(name, hp, baseAttack, baseDefense, weapon, shield, luck);
        this.hostile = true;
    }

    public boolean isHostile() {
        return this.hostile;
    }
}

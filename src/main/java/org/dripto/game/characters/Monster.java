package org.dripto.game.characters;

import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;

public class Monster extends NPC {

    private boolean hostile;
    private boolean boss;

    public Monster(String name, int hp, int baseAttack, int baseDefense,
                   Weapon weapon, Shield shield, int luck, boolean boss) {
        super(name, hp, baseAttack, baseDefense, weapon, shield, luck);
        this.hostile = true;
        this.boss = boss;
    }

    public boolean isBoss() {
        return this.boss;
    }

    public boolean isHostile() {
        return this.hostile;
    }
}

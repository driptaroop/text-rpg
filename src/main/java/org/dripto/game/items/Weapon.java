package org.dripto.game.items;

import java.io.Serializable;

public enum Weapon implements Items, Serializable {
    BARE_HANDS(0, "Bare Hands"),
    KNIFE(2, "Small Knife"),
    SHORT_SWORD(3, "Short Sword"),
    LONG_SWORD(4, "Long Sword");

    Weapon(int modifier, String name) {
        this.modifier = modifier;
        this.name = name;
    }

    int modifier;
    String name;

    public int getModifier() {
        return this.modifier;
    }

    public String getName() {
        return this.name;
    }
}

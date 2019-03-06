package org.dripto.game.items;

import java.io.Serializable;

public enum Shield implements Items , Serializable {
    BARE_HANDS(0, "Bare Hands"),
    WOODEN_SHIELD(2,"Wooden Shield"),
    IRON_SHIELD(3, "Iron Shield"),
    STEEL_SHIELD(4, "Steel Shield");

    private int modifier;
    private String name;

    Shield(int modifier, String name) {
        this.modifier = modifier;
        this.name = name;
    }

    public int getModifier() {
        return modifier;
    }

    public String getName() {
        return name;
    }
}

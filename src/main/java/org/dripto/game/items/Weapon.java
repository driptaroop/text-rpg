package org.dripto.game.items;

import java.io.Serializable;

public enum Weapon implements Items, Serializable {
    KNIFE{
        private int modifier = 2;
        private String name = "Small Knife";

        public int getModifier() {
            return modifier;
        }

        public String getName() {
            return name;
        }
    }
}

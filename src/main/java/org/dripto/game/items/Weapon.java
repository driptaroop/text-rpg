package org.dripto.game.items;

public enum Weapon implements Items {
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

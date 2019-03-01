package org.dripto.game.items;

public enum Shield implements Items {
    WOODEN_SHIELD{
        private int modifier = 2;
        private String name = "Wooden Shield";

        public int getModifier() {
            return modifier;
        }

        public String getName() {
            return name;
        }
    }
}

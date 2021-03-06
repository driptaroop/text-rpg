package org.dripto.game.characters;

import org.dripto.game.items.Shield;
import org.dripto.game.items.Weapon;

public class Player extends DnDGameCharacters{

    private String background;
    private int experience;

    public Player(String name, int hp, int baseAttack,
                  int baseDefense, Weapon weapon, Shield shield,
                  int luck, String background, int experience) {
        super(name, hp, baseAttack, baseDefense, weapon, shield, luck);
        this.background = background;
        this.experience = experience;
    }

    public void addExperience(int exp) {
        this.experience = this.experience + exp;
    }

    public String getBackground() {
        return background;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return "Player{" +
                "background='" + background + '\'' +
                ", experience=" + experience +
                "} " + super.toString();
    }
}

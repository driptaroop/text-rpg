package org.dripto.game.fight;

import org.dripto.game.characters.DnDGameCharacters;

public class FightStatus {
    public static String getFightStatus(DnDGameCharacters... characters){
        StringBuilder builder = new StringBuilder();
        for (DnDGameCharacters c : characters){
            builder.append(c.getName() + " health is at " + c.getHp() + "/" + c.getFullHp());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}

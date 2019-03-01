package org.dripto.game.util;

import java.util.concurrent.ThreadLocalRandom;

public class Gameutils {
    public static int getRandomWithinRange(int startInclusive, int endInclusive){
        return ThreadLocalRandom.current().nextInt(startInclusive, endInclusive + 1);
    }
}

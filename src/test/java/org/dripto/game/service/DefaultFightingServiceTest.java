package org.dripto.game.service;

import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.fight.FightResult;
import org.dripto.game.game.GameInput;
import org.dripto.game.map.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DefaultFightingServiceTest {

    Player player;
    Monster monster;
    DefaultFightingService fightingService;
    @BeforeEach
    void setUp() {
        player = new Player(
                "test", 5, 10, 0, null, null, 10, "", 0
        );
        monster = new Monster(
                "Orc", 0
                , 3,3
                ,null, null, 1, false
        );
        monster.setRoom(new Room(1,1));
        this.fightingService = new DefaultFightingService();
    }

    @Test
    void initiateFightWon() {
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        FightResult result = fightingService.initiateFight(player, monster);
        assertThat(result, is(FightResult.WON));
    }
    @Test
    void initiateFightLost() {
        player.setHp(1);
        monster.setHp(10);
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        FightResult result = fightingService.initiateFight(player, monster);
        assertThat(result, is(FightResult.DIED));
    }

    @Test
    void initiateFightFLEE() {
        String input = "2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        FightResult result = fightingService.initiateFight(player, monster);
        assertThat(result, is(FightResult.FLEE));
    }

    @Test
    void gainExperience() {
        assertThat(fightingService.gainExperience(player, monster), is(monster.getFullHp()));
    }
}
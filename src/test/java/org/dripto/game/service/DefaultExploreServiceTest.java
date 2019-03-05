package org.dripto.game.service;

import org.dripto.game.characters.Monster;
import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.WrongChoiceException;
import org.dripto.game.game.GameInput;
import org.dripto.game.map.Explore;
import org.dripto.game.util.GameConstants;
import org.dripto.game.util.Gameutils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultExploreServiceTest {

    DefaultConsoleUIService service;
    DefaultExploreService exploreService;

    @BeforeEach
    void setUp() {
        service = new DefaultConsoleUIService();
        exploreService = (DefaultExploreService)service.exploreService;
    }

    @Test
    void exploreExit() {
        String input = "test\nabc\n1\n2\n3\n4\n1\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        service.newGame();
        service.initMap();
        System.out.println(service.player);
        System.out.println(service.dungeon);
        assertThrows(ExitGameException.class, () -> {
            service.exploreService.explore(service.dungeon, service.player, service.monsters);
        });
    }

    @Test
    void checkIfWon() {
        Set<Monster> monsters = new LinkedHashSet<>();
        IntStream.range(0, GameConstants.NUMBER_OF_MONSTERS)
                .forEachOrdered(v -> monsters.add(new Monster(
                        "Orc", -5
                        , Gameutils.getRandomWithinRange(2,4), Gameutils.getRandomWithinRange(1,2)
                        ,null, null, Gameutils.getRandomWithinRange(0,1), false
                )));
        assertThat(exploreService.checkIfWon(monsters), is(true));
        monsters.forEach(monster -> monster.setHp(2));
        assertThat(exploreService.checkIfWon(monsters), is(false));
    }

    @Test
    void showExplorationMenu() throws WrongChoiceException, ExitGameException {
        String input = "w";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        Explore explore = exploreService.showExplorationMenu();
        assertThat(explore, is(Explore.NORTH));
    }
}
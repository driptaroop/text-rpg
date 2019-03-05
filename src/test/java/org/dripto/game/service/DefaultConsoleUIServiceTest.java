package org.dripto.game.service;

import org.dripto.game.exception.ExitGameException;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.StartGameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultConsoleUIServiceTest {

    DefaultConsoleUIService service;

    @BeforeEach
    void setUp() {
        service = new DefaultConsoleUIService();
    }

    @Test
    void showMainMenuNew() throws ExitGameException {
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        StartGameStatus status = service.showMainMenu();
        assertThat(status, is(StartGameStatus.NEW));
    }
    @Test
    void showMainMenuLoad() throws ExitGameException {
        String input = "2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        StartGameStatus status = service.showMainMenu();
        assertThat(status, is(StartGameStatus.RESUME));
    }
    @Test
    void showMainMenuExit() {
        String input = "w";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        assertThrows(ExitGameException.class, () -> service.showMainMenu());
    }

    @Test
    void initMap() {
        String input = "test\nabc\n1\n2\n3\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        service.newGame();
        service.initMap();
        assertNotNull(service.dungeon);
        assertNotNull(service.player.getRoom());
        service.monsters.forEach(monster -> assertNotNull(monster.getRoom()));
    }

    @Test
    void newGame() {
        String input = "test\nabc\n1\n2\n3\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        service.newGame();
        assertNotNull(service.player);
        assertNotNull(service.monsters);
    }
}
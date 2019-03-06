package org.dripto.game.service;

import org.dripto.game.characters.Player;
import org.dripto.game.game.GameInput;
import org.dripto.game.game.SaveGameState;
import org.dripto.game.map.Room;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveGameServiceTest {

    @Test
    void saveGame() throws IOException, ClassNotFoundException {
        DefaultConsoleUIService service = new DefaultConsoleUIService();
        String input = "test\nabc\n1\n2\n3\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.getInstance().setScanner(new Scanner(in));
        service.newGame();
        service.initMap();
        SaveGameState save = new SaveGameState(service.dungeon, service.player, service.monsters);
        SaveGameService.INSTANCE.saveGame(save);
        SaveGameState loadedGame = SaveGameService.INSTANCE.loadGame();
        for (Room[] rooms : loadedGame.getDungeon().getRooms()) {
            for (Room room : rooms) {
                assertEquals(room.getCharactersInRoom().size(),
                        save.getDungeon().getRooms()[room.getX()][room.getY()].getCharactersInRoom().size());
            }
        }
        Player expected = save.getPlayer();
        Player actual = loadedGame.getPlayer();

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getHp(), actual.getHp());
        assertEquals(expected.getBaseAttack(), actual.getBaseAttack());
        assertEquals(expected.getBaseDefense(), actual.getBaseDefense());
        assertEquals(expected.getLuck(), actual.getLuck());
        assertEquals(expected.getBackground(), actual.getBackground());
        assertEquals(expected.getExperience(), actual.getExperience());
        assertEquals(save.getMonster().size(), loadedGame.getMonster().size());
    }
}
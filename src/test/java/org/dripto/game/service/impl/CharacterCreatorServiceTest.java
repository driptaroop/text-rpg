package org.dripto.game.service.impl;


import org.dripto.game.characters.Monster;
import org.dripto.game.characters.Player;
import org.dripto.game.util.GameInput;
import org.dripto.game.service.CharacterCreatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterCreatorServiceTest {

    CharacterCreatorService service;
    @BeforeEach
    void setUp() {
        service = DefaultCharacterCreatorService.INSTANCE;
    }

    @Test
    void createCharacter() {
        String input = "test\nabc\n1\n2\n3\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        GameInput.INSTANCE.setScanner(new Scanner(in));
        Player actual = service.createCharacter();
        Player expected = new Player(
                "test", 1, 2, 3, null, null, 4, "abc", 0
        );
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getHp(), actual.getHp());
        assertEquals(expected.getBaseAttack(), actual.getBaseAttack());
        assertEquals(expected.getBaseDefense(), actual.getBaseDefense());
        assertEquals(expected.getLuck(), actual.getLuck());
        assertEquals(expected.getBackground(), actual.getBackground());
        assertEquals(expected.getExperience(), actual.getExperience());
    }

    @Test
    void getMonsters() {
        Set<Monster> monsters = service.getMonsters();
        monsters.forEach(monster -> {
            assertThat(monster.getName(), is("Orc"));
            assertTrue(monster.isHostile());
        });
    }
}
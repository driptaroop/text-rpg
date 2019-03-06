package org.dripto.game.service;

import org.dripto.game.characters.NPC;
import org.dripto.game.characters.Player;
import org.dripto.game.fight.FightResult;

public interface FightingService {
    FightResult initiateFight(Player player, NPC enemy);

    int gainExperience(Player player, NPC enemy);
}

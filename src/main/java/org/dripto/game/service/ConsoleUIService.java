package org.dripto.game.service;

import org.dripto.game.exception.ExitGameException;
import org.dripto.game.exception.PlayerDiedException;

public interface ConsoleUIService {
    void init() throws ExitGameException, PlayerDiedException;
}

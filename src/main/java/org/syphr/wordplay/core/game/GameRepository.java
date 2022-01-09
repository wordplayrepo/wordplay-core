package org.syphr.wordplay.core.game;

import java.util.UUID;

public interface GameRepository
{
    Game save(Game game) throws GameSaveException;

    Game load(UUID id) throws GameLoadException;
}

package org.syphr.wordplay.core.events;

import org.syphr.wordplay.core.Game;

public class GameEvent
{
    private final Game game;

    public GameEvent(Game game)
    {
        this.game = game;
    }

    public Game getGame()
    {
        return game;
    }
}

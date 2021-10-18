package org.syphr.wordplay.api.events;

import org.syphr.wordplay.api.Game;

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

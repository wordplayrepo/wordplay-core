package org.syphr.wordplay.core.game;

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

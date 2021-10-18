package org.syphr.wordplay.api.events;

import org.syphr.wordplay.api.Game;

public class GameEndEvent extends GameEvent
{
    public GameEndEvent(Game game)
    {
        super(game);
    }
}

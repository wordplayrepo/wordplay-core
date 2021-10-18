package org.syphr.wordplay.core.events;

import org.syphr.wordplay.core.Game;

public class GameEndEvent extends GameEvent
{
    public GameEndEvent(Game game)
    {
        super(game);
    }
}

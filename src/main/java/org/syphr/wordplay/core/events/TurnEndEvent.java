package org.syphr.wordplay.core.events;

import org.syphr.wordplay.core.Game;

public class TurnEndEvent extends GameEvent
{
    public TurnEndEvent(Game game)
    {
        super(game);
    }
}

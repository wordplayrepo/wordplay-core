package org.syphr.wordplay.core.events;

import org.syphr.wordplay.core.Game;

public class TurnStartEvent extends GameEvent
{
    public TurnStartEvent(Game game)
    {
        super(game);
    }
}

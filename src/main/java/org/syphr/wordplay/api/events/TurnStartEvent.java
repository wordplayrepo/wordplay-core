package org.syphr.wordplay.api.events;

import org.syphr.wordplay.api.Game;

public class TurnStartEvent extends GameEvent
{
    public TurnStartEvent(Game game)
    {
        super(game);
    }
}

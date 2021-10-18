package org.syphr.wordplay.api.events;

import org.syphr.wordplay.api.Game;

public class TurnEndEvent extends GameEvent
{
    public TurnEndEvent(Game game)
    {
        super(game);
    }
}

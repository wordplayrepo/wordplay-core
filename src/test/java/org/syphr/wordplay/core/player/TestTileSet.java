package org.syphr.wordplay.core.player;

import org.syphr.wordplay.core.component.AbstractTileSet;
import org.syphr.wordplay.core.config.Configuration;

public class TestTileSet extends AbstractTileSet
{
    private final Configuration config;

    protected TestTileSet(Configuration config)
    {
        this.config = config;
    }

    @Override
    protected Configuration getConfiguration()
    {
        return config;
    }
}

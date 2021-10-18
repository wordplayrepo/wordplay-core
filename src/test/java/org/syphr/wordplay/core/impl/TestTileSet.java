package org.syphr.wordplay.core.impl;

import org.syphr.wordplay.core.Configuration;
import org.syphr.wordplay.core.impl.AbstractTileSet;

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

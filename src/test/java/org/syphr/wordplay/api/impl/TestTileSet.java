package org.syphr.wordplay.api.impl;

import org.syphr.wordplay.api.Configuration;

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

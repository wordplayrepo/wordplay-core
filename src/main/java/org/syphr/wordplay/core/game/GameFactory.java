package org.syphr.wordplay.core.game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.syphr.wordplay.core.config.Configuration;

public interface GameFactory
{
    Game create();

    Game create(File configurationFile) throws IOException;

    Game create(InputStream configurationStream) throws IOException;

    Game create(Configuration configuration);

    Game load(State state);
}

package org.syphr.wordplay.core.game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.syphr.wordplay.core.config.Configuration;

public interface GameFactory
{
    Game create();

    Game create(File configurationFile) throws IOException;

    Game create(InputStream configurationStream) throws IOException;

    Game load(File stateFile) throws IOException;

    Game load(InputStream stateStream) throws IOException;

    void save(Game game, File file) throws IOException;

    void save(Game game, OutputStream out) throws IOException;

    Game create(Configuration configuration);

    Game load(State state);
}

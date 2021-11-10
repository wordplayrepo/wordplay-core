package org.syphr.wordplay.core.game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.config.Configurations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractGameFactory implements GameFactory
{
    @Override
    public Game create()
    {
        return create((Configuration) null);
    }

    @Override
    public Game create(File configurationFile) throws IOException
    {
        log.trace("Creating new game from configuration at \"{}\"", configurationFile.getAbsolutePath());
        return create(Configurations.create(Configurations.read(configurationFile)));
    }

    @Override
    public Game create(InputStream configurationStream) throws IOException
    {
        log.trace("Creating game from configuration stream");
        return create(Configurations.create(Configurations.read(configurationStream)));
    }

    @Override
    public Game load(File stateFile) throws IOException
    {
        log.trace("Loading game from state at \"{}\"", stateFile.getAbsolutePath());
        return load(States.create(States.read(stateFile)));
    }

    @Override
    public Game load(InputStream stateStream) throws IOException
    {
        log.trace("Loading game from state stream");
        return load(States.create(States.read(stateStream)));
    }

    @Override
    public void save(Game game, File file) throws IOException
    {
        log.trace("Saving game state to \"{}\"", file.getAbsolutePath());
        States.write(game.getState(), file);
    }

    @Override
    public void save(Game game, OutputStream out) throws IOException
    {
        log.trace("Saving game state to stream");
        States.write(game.getState(), out);
    }

    @Override
    public abstract Game create(Configuration configuration);

    @Override
    public abstract Game load(State state);
}

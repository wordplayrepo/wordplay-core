package org.syphr.wordplay.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.config.Configurations;

public abstract class GameFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GameFactory.class);

    private static final ServiceLoader<GameFactory> SERVICE_LOADER = ServiceLoader.load(GameFactory.class);

    public synchronized static List<GameFactory> getFactories()
    {
        List<GameFactory> factories = new ArrayList<GameFactory>();

        for (GameFactory factory : SERVICE_LOADER)
        {
            LOGGER.trace("Discovered game factory: {}", factory.getClass().getName());
            factories.add(factory);
        }

        return factories;
    }

    public static GameFactory getFactory()
    {
        List<GameFactory> factories = getFactories();
        if (factories.isEmpty())
        {
            LOGGER.trace("No game factories were found");
            return null;
        }

        return factories.get(0);
    }

    public Game create()
    {
        return create((Configuration)null);
    }

    public Game create(File configurationFile) throws IOException
    {
        LOGGER.trace("Creating new game from configuration at \"{}\"",
                     configurationFile.getAbsolutePath());
        return create(Configurations.create(Configurations.read(configurationFile)));
    }

    public Game create(InputStream configurationStream) throws IOException
    {
        LOGGER.trace("Creating game from configuration stream");
        return create(Configurations.create(Configurations.read(configurationStream)));
    }

    public Game load(File stateFile) throws IOException
    {
        LOGGER.trace("Loading game from state at \"{}\"", stateFile.getAbsolutePath());
        return load(States.create(States.read(stateFile)));
    }

    public Game load(InputStream stateStream) throws IOException
    {
        LOGGER.trace("Loading game from state stream");
        return load(States.create(States.read(stateStream)));
    }

    public void save(Game game, File file) throws IOException
    {
        LOGGER.trace("Saving game state to \"{}\"", file.getAbsolutePath());
        States.write(game.getState(), file);
    }

    public void save(Game game, OutputStream out) throws IOException
    {
        LOGGER.trace("Saving game state to stream");
        States.write(game.getState(), out);
    }

    public abstract Game create(Configuration configuration);

    public abstract Game load(State state);
}

package org.syphr.wordplay.core.game;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameFactories
{
    private static final ServiceLoader<GameFactory> SERVICE_LOADER = ServiceLoader.load(GameFactory.class);

    public synchronized static List<GameFactory> getFactories()
    {
        List<GameFactory> factories = new ArrayList<>();

        for (GameFactory factory : SERVICE_LOADER) {
            log.trace("Discovered game factory: {}", factory.getClass().getName());
            factories.add(factory);
        }

        return factories;
    }

    public static GameFactory getFactory()
    {
        List<GameFactory> factories = getFactories();
        if (factories.isEmpty()) {
            log.trace("No game factories were found");
            return null;
        }

        return factories.get(0);
    }

    private GameFactories()
    {}
}

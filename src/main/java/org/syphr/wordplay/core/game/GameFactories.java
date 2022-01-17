/*
 * Copyright © 2012-2022 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

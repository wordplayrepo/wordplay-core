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
package org.syphr.wordplay.core.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTileSet implements TileSet
{
    private final ConcurrentMap<Location, Tile> tiles = new ConcurrentHashMap<Location, Tile>();

    @Override
    public void clear()
    {
        tiles.clear();
    }

    @Override
    public Tile getTile(Location location)
    {
        Tile tile = tiles.get(location);
        if (tile == null) {
            Tile newTile = createTile(location, getConfiguration());
            tile = tiles.putIfAbsent(location, newTile);
            if (tile == null) {
                tile = newTile;
            }
        }

        return tile;
    }

    @Override
    public Set<Tile> getOccupiedTiles()
    {
        Set<Tile> occupiedTiles = new TreeSet<Tile>();
        for (Tile tile : tiles.values()) {
            if (tile.hasPiece()) {
                occupiedTiles.add(tile);
            }
        }

        return occupiedTiles;
    }

    @Override
    public Map<Location, List<TileAttribute>> getAttributes(Set<Location> locations)
    {
        log.trace("Finding all tile attributes for locations {}", locations);

        Map<Location, List<TileAttribute>> attributes = new TreeMap<Location, List<TileAttribute>>();

        for (Location location : locations) {
            Tile tile = getTile(location);

            Set<TileAttribute> tileAttributes = tile.getAttributes();
            if (tileAttributes.isEmpty()) {
                log.trace("No attributes found at {}", location);
                continue;
            }

            List<TileAttribute> currentList = attributes.get(location);
            if (currentList == null) {
                currentList = new ArrayList<TileAttribute>();
                attributes.put(location, currentList);
            }

            log.trace("{} attributes found at {}", tileAttributes.size(), location);
            currentList.addAll(tileAttributes);
        }

        return attributes;
    }

    protected Tile createTile(Location location, Configuration configuration)
    {
        TileImpl tile = new TileImpl();
        tile.setLocation(location);
        tile.addAttributes(configuration.getTileAttributes(location));

        return tile;
    }

    protected abstract Configuration getConfiguration();
}

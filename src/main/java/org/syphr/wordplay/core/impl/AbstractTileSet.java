package org.syphr.wordplay.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.syphr.wordplay.core.Configuration;
import org.syphr.wordplay.core.Tile;
import org.syphr.wordplay.core.TileAttribute;
import org.syphr.wordplay.core.TileSet;
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

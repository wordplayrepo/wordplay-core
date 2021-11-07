package org.syphr.wordplay.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

/**
 * A tile set is a collection of tiles belonging to a board.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface TileSet
{
    /**
     * Remove all tiles from this set.
     */
    public void clear();

    /**
     * Retrieve the tile at the given location. If no such tile exists, one will be
     * created.
     * 
     * @param location the location of the desired tile
     * 
     * @return the tile at the given location
     */
    public Tile getTile(Location location);

    /**
     * Retrieve the subset of tiles which are occupied by pieces.
     * 
     * @return the occupied tiles
     */
    public Set<Tile> getOccupiedTiles();

    /**
     * Retrieve all tile attributes for the given set of locations.
     * 
     * @param locations the locations for which attributes are requested
     * 
     * @return a map of each given location to the list of tile attributes present
     *         at that location
     */
    public Map<Location, List<TileAttribute>> getAttributes(Set<Location> locations);
}

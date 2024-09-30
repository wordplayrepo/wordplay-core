/*
 * Copyright © 2012-2024 Gregory P. Moyer
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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

import lombok.NonNull;

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
    public Tile getTile(@NonNull Location location);

    /**
     * Retrieve the subset of tiles which are occupied by pieces.
     *
     * @return the occupied tiles
     */
    public SortedSet<Tile> getOccupiedTiles();

    /**
     * Retrieve all tile attributes for the given set of locations.
     *
     * @param locations the locations for which attributes are requested
     *
     * @return a map of each given location to the list of tile attributes present
     *         at that location
     */
    public Map<Location, List<TileAttribute>> getAttributes(@NonNull Set<Location> locations);
}

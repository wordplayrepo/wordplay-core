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
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TileSetImpl implements TileSet
{
    private final ConcurrentMap<Location, Tile> tiles = new ConcurrentHashMap<>();

    @NonNull
    private final TileFactory tileFactory;

    @Override
    public void clear()
    {
        tiles.clear();
    }

    @Override
    public Tile getTile(@NonNull Location location)
    {
        return tiles.computeIfAbsent(location, tileFactory::createTile);
    }

    @Override
    public SortedSet<Tile> getOccupiedTiles()
    {
        return tiles.values().stream().filter(Tile::hasPiece).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public Map<Location, List<TileAttribute>> getAttributes(@NonNull Set<Location> locations)
    {
        // TODO consider whether this should return List<TileAttribute> when
        // Tile::getAttributes() returns an unordered Set
        return locations.stream()
                        .collect(Collectors.toMap(location -> location,
                                                  location -> List.copyOf(getTile(location).getAttributes())));
    }
}

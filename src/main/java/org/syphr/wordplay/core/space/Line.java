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
package org.syphr.wordplay.core.space;

import java.util.NavigableSet;
import java.util.TreeSet;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Defines a path between start and end {@link Location} instances.
 */
@ThreadSafe
@Immutable
@EqualsAndHashCode
@ToString
public final class Line
{
    private final NavigableSet<Location> locations;

    public static Line between(Location start, Location end)
    {
        return new Line(start, end);
    }

    public Line(Location start, Location end)
    {
        locations = findLocations(start, end);
    }

    private NavigableSet<Location> findLocations(Location start, Location end)
    {
        NavigableSet<Location> set = new TreeSet<>();
        set.add(start);

        Distance d = Distance.between(start, end);
        float N = Math.max(d.x(), Math.max(d.y(), d.z()));

        float sx = d.x() / N;
        float sy = d.y() / N;
        float sz = d.z() / N;

        float px = start.x();
        float py = start.y();
        float pz = start.z();
        for (int i = 0; i < N; i++) {
            px += sx;
            py += sy;
            pz += sz;

            set.add(Location.at(Math.round(px), Math.round(py), Math.round(pz)));
        }

        return set;
    }

    public Location start()
    {
        return locations.getFirst();
    }

    public Location end()
    {
        return locations.getLast();
    }

    public boolean contains(Location l)
    {
        return locations.contains(l);
    }
}

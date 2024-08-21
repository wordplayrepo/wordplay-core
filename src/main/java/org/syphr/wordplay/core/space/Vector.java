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

/**
 * Vector defines the distance and direction to go from one {@link Location} to
 * another.
 */
public record Vector(int x, int y, int z) implements Comparable<Vector>
{
    public static Vector of(int x, int y)
    {
        return of(x, y, 0);
    }

    public static Vector of(int x, int y, int z)
    {
        return new Vector(x, y, z);
    }

    public static Vector from(Location start, Location end)
    {
        return of(end.x() - start.x(), end.y() - start.y(), end.z() - start.z());
    }

    @Override
    public int compareTo(Vector o)
    {
        int compare = x - o.x;
        if (compare != 0) {
            return compare;
        }

        compare = y - o.y;
        if (compare != 0) {
            return compare;
        }

        return z - o.z;
    }
}

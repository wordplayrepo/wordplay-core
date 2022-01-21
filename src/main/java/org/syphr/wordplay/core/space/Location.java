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
package org.syphr.wordplay.core.space;

public record Location(int x, int y, int z) implements Comparable<Location>
{
    public static Location at(int x, int y)
    {
        return at(x, y, 0);
    }

    public static Location at(int x, int y, int z)
    {
        return new Location(x, y, z);
    }

    public Location move(Vector vector)
    {
        return Location.at(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }

    public boolean isWithin(Distance distance, Location location)
    {
        int otherX = location.x();
        int otherY = location.y();
        int otherZ = location.z();

        return Math.abs(x - otherX) <= distance.x() && Math.abs(y - otherY) <= distance.y()
               && Math.abs(z - otherZ) <= distance.z();
    }

    @Override
    public int compareTo(Location o)
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

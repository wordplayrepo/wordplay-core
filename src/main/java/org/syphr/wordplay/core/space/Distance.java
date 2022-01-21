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

public record Distance(int x, int y, int z) implements Comparable<Distance>
{
    private static final Distance ZERO = Distance.of(0, 0, 0);
    private static final Distance MAX = Distance.of(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public static Distance zero()
    {
        return ZERO;
    }

    public static Distance max()
    {
        return MAX;
    }

    public static Distance between(Location start, Location end)
    {
        return of(end.x() - start.x(), end.y() - start.y(), end.z() - start.z());
    }

    public static Distance of(int x, int y)
    {
        return of(x, y, 0);
    }

    public static Distance of(int x, int y, int z)
    {
        return new Distance(x, y, z);
    }

    public Distance(int x, int y, int z)
    {
        this.x = Math.abs(x);
        this.y = Math.abs(y);
        this.z = Math.abs(z);
    }

    public boolean isWithin(Distance distance)
    {
        return x <= distance.x() && y <= distance.y() && z <= distance.z();
    }

    @Override
    public int compareTo(Distance o)
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

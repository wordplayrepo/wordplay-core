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

public record Dimension(int width, int height, int depth) implements Comparable<Dimension>
{
    public static Dimension of(int width, int height)
    {
        return of(width, height, 1);
    }

    public static Dimension of(int width, int height, int depth)
    {
        return new Dimension(width, height, depth);
    }

    public boolean contains(Location location)
    {
        int x = location.getX();
        int y = location.getY();
        int z = location.getZ();

        return x >= 0 && x < width && y >= 0 && y < height && z >= 0 && z < depth;
    }

    @Override
    public int compareTo(Dimension o)
    {
        int compare = width - o.width;
        if (compare != 0) {
            return compare;
        }

        compare = height - o.height;
        if (compare != 0) {
            return compare;
        }

        return depth - o.depth;
    }
}

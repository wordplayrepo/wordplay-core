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

/* default */ final class ZOrientation implements Orientation
{
    private static final ZOrientation INSTANCE = new ZOrientation();

    public static ZOrientation instance()
    {
        return INSTANCE;
    }

    private ZOrientation()
    {
        /* Singleton */
    }

    @Override
    public Location move(Location location, int amount)
    {
        return location.move(Vector.of(0, 0, amount));
    }

    @Override
    public boolean contains(Distance distance)
    {
        return distance.x() == 0 && distance.y() == 0;
    }

    @Override
    public String toString()
    {
        return "Z-Axis";
    }
}

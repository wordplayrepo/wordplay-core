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

/* default */ final class XOrientation implements Orientation
{
    private static final XOrientation INSTANCE = new XOrientation();

    public static XOrientation instance()
    {
        return INSTANCE;
    }

    private XOrientation()
    {
        /* Singleton */
    }

    @Override
    public Location move(Location location, int amount)
    {
        return location.move(Vector.of(amount, 0, 0));
    }

    @Override
    public boolean contains(Distance distance)
    {
        return distance.y() == 0 && distance.z() == 0;
    }

    @Override
    public String toString()
    {
        return "X-Axis";
    }
}

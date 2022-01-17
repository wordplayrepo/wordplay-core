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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

public class Orientations
{
    private static final Orientation X = new XOrientation();

    private static final Orientation Y = new YOrientation();

    private static final Orientation Z = new ZOrientation();

    private static final Set<Orientation> XY = Collections.unmodifiableSet(Sets.newHashSet(X, Y));

    private static final Set<Orientation> XYZ = Collections.unmodifiableSet(Sets.newHashSet(X,
                                                                                            Y,
                                                                                            Z));

    public static Orientation x()
    {
        return X;
    }

    public static Orientation y()
    {
        return Y;
    }

    public static Orientation z()
    {
        return Z;
    }

    public static Set<Orientation> xy()
    {
        return XY;
    }

    public static Set<Orientation> xyz()
    {
        return XYZ;
    }

    public static Set<Orientation> setOf(Orientation... orientations)
    {
        Set<Orientation> set = new HashSet<Orientation>();

        for (Orientation orientation : orientations)
        {
            set.add(orientation);
        }

        return set;
    }

    private Orientations()
    {
        /*
         * Factory pattern
         */
    }
}

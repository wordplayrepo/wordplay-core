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

import java.util.LinkedHashSet;
import java.util.Set;

public class Orientations
{
    public static Orientation x()
    {
        return XOrientation.instance();
    }

    public static Orientation y()
    {
        return YOrientation.instance();
    }

    public static Orientation z()
    {
        return ZOrientation.instance();
    }

    public static Set<Orientation> xy()
    {
        LinkedHashSet<Orientation> set = new LinkedHashSet<>();
        set.add(x());
        set.add(y());

        return set;
    }

    public static Set<Orientation> xyz()
    {
        LinkedHashSet<Orientation> set = new LinkedHashSet<>();
        set.add(x());
        set.add(y());
        set.add(z());

        return set;
    }

    private Orientations()
    {
        /*
         * Factory pattern
         */
    }
}

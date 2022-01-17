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
package org.syphr.wordplay.core.component;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Distance;

public class MultiplierAttribute implements TileAttribute
{
    private final Distance maxDistance;
    private final double multiplier;
    private final boolean containingWordOnly;
    private final boolean visible;

    public MultiplierAttribute(Distance maxDistance,
                               double multiplier,
                               boolean containingWordOnly,
                               boolean visible)
    {
        this.maxDistance = maxDistance;
        this.multiplier = multiplier;
        this.containingWordOnly = containingWordOnly;
        this.visible = visible;
    }

    @Override
    public int modifyValue(int value, Distance distance, boolean sameWord)
    {
        if ((isContainingWordOnly() && !sameWord) || !distance.isWithin(maxDistance))
        {
            return value;
        }

        return (int)Math.round(value * multiplier);
    }

    public boolean isContainingWordOnly()
    {
        return containingWordOnly;
    }

    @Override
    public boolean isVisible()
    {
        return visible;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MultiplierAttribute [maxDistance=");
        builder.append(maxDistance);
        builder.append(", multiplier=");
        builder.append(multiplier);
        builder.append(", containingWordOnly=");
        builder.append(containingWordOnly);
        builder.append(", visible=");
        builder.append(visible);
        builder.append("]");
        return builder.toString();
    }
}

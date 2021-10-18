package org.syphr.wordplay.api.impl;

import org.syphr.wordplay.api.Distance;
import org.syphr.wordplay.api.TileAttribute;

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

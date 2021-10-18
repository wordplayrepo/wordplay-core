package org.syphr.wordplay.core.impl;

import org.syphr.wordplay.core.Distance;
import org.syphr.wordplay.core.Location;
import org.syphr.wordplay.core.Orientation;
import org.syphr.wordplay.core.Vector;

public class XOrientation implements Orientation
{
    @Override
    public Location move(Location location, int amount)
    {
        return location.move(Vector.of(amount, 0, 0));
    }

    @Override
    public boolean contains(Distance distance)
    {
        return distance.getY() == 0 && distance.getZ() == 0;
    }

    @Override
    public String toString()
    {
        return "X-Axis";
    }
}

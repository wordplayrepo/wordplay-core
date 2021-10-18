package org.syphr.wordplay.api.impl;

import org.syphr.wordplay.api.Distance;
import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Orientation;
import org.syphr.wordplay.api.Vector;

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

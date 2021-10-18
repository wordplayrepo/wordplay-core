package org.syphr.wordplay.api.impl;

import org.syphr.wordplay.api.Distance;
import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Orientation;
import org.syphr.wordplay.api.Vector;

public class ZOrientation implements Orientation
{
    @Override
    public Location move(Location location, int amount)
    {
        return location.move(Vector.of(0, 0, amount));
    }

    @Override
    public boolean contains(Distance distance)
    {
        return distance.getX() == 0 && distance.getY() == 0;
    }

    @Override
    public String toString()
    {
        return "Z-Axis";
    }
}

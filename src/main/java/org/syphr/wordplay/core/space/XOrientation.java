package org.syphr.wordplay.core.space;

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

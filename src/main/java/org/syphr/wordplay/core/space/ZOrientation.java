package org.syphr.wordplay.core.space;

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

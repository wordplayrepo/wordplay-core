package org.syphr.wordplay.core.space;

public class YOrientation implements Orientation
{
    @Override
    public Location move(Location location, int amount)
    {
        return location.move(Vector.of(0, amount, 0));
    }

    @Override
    public boolean contains(Distance distance)
    {
        return distance.getX() == 0 && distance.getZ() == 0;
    }

    @Override
    public String toString()
    {
        return "Y-Axis";
    }
}

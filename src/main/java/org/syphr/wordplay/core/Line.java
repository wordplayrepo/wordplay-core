package org.syphr.wordplay.core;

import java.util.NavigableSet;
import java.util.TreeSet;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.space.Distance;
import org.syphr.wordplay.core.space.Location;

@ThreadSafe
@Immutable
public class Line
{
    private final NavigableSet<Location> locations;

    public static Line from(Location l1, Location l2)
    {
        return new Line(l1, l2);
    }

    protected Line(Location l1, Location l2)
    {
        locations = findLocations(l1, l2);
    }

    protected NavigableSet<Location> findLocations(Location l1, Location l2)
    {
        NavigableSet<Location> set = new TreeSet<Location>();
        set.add(l1);

        Distance d = Distance.between(l1, l2);
        float N = Math.max(d.getX(), Math.max(d.getY(), d.getZ()));

        float sx = d.getX() / N;
        float sy = d.getY() / N;
        float sz = d.getZ() / N;

        float px = l1.getX();
        float py = l1.getY();
        float pz = l1.getZ();
        for (int i = 0; i < N; i++)
        {
            px += sx;
            py += sy;
            pz += sz;

            set.add(Location.at(Math.round(px), Math.round(py), Math.round(pz)));
        }

        return set;
    }

    public Location getL1()
    {
        return locations.first();
    }

    public Location getL2()
    {
        return locations.last();
    }

    public boolean contains(Location l)
    {
        return locations.contains(l);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Line [l1=");
        builder.append(getL1());
        builder.append(", l2=");
        builder.append(getL2());
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((locations == null) ? 0 : locations.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Line other = (Line)obj;
        if (locations == null)
        {
            if (other.locations != null)
            {
                return false;
            }
        }
        else if (!locations.equals(other.locations))
        {
            return false;
        }
        return true;
    }
}

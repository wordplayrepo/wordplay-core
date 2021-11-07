package org.syphr.wordplay.core.space;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Immutable
public class Location implements Comparable<Location>
{
    private final int x;
    private final int y;
    private final int z;

    public static Location at(int x, int y)
    {
        return at(x, y, 0);
    }

    public static Location at(int x, int y, int z)
    {
        return new Location(x, y, z);
    }

    protected Location(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public Location move(Vector vector)
    {
        return Location.at(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }

    public boolean isWithin(Distance distance, Location location)
    {
        int otherX = location.getX();
        int otherY = location.getY();
        int otherZ = location.getZ();

        return Math.abs(x - otherX) <= distance.getX()
                && Math.abs(y - otherY) <= distance.getY()
                && Math.abs(z - otherZ) <= distance.getZ();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Location [x=");
        builder.append(x);
        builder.append(", y=");
        builder.append(y);
        builder.append(", z=");
        builder.append(z);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
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
        Location other = (Location)obj;
        if (x != other.x)
        {
            return false;
        }
        if (y != other.y)
        {
            return false;
        }
        if (z != other.z)
        {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Location o)
    {
        int compare = x - o.x;
        if (compare != 0)
        {
            return compare;
        }

        compare = y - o.y;
        if (compare != 0)
        {
            return compare;
        }

        return z - o.z;
    }
}

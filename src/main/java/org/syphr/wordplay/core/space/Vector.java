package org.syphr.wordplay.core.space;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Immutable
public class Vector implements Comparable<Vector>
{
    private final int x;
    private final int y;
    private final int z;

    public static Vector of(int x, int y)
    {
        return of(x, y, 0);
    }

    public static Vector of(int x, int y, int z)
    {
        return new Vector(x, y, z);
    }

    public static Vector from(Location start, Location end)
    {
        return of(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ());
    }

    protected Vector(int x, int y, int z)
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

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Vector [x=");
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
        Vector other = (Vector)obj;
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
    public int compareTo(Vector o)
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

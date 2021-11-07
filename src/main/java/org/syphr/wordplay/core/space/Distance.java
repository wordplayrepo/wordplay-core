package org.syphr.wordplay.core.space;

public class Distance implements Comparable<Distance>
{
    private final int x;
    private final int y;
    private final int z;

    public static Distance of(int x, int y)
    {
        return of(x, y, 0);
    }

    public static Distance of(int x, int y, int z)
    {
        return new Distance(x, y, z);
    }

    public static Distance between(Location start, Location end)
    {
        return of(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ());
    }

    protected Distance(int x, int y, int z)
    {
        this.x = Math.abs(x);
        this.y = Math.abs(y);
        this.z = Math.abs(z);
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

    public boolean isWithin(Distance distance)
    {
        return x <= distance.getX() && y <= distance.getY() && z <= distance.getZ();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Distance [x=");
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
        Distance other = (Distance)obj;
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
    public int compareTo(Distance o)
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

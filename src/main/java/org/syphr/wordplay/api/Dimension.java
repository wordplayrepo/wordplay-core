package org.syphr.wordplay.api;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Immutable
public class Dimension implements Comparable<Dimension>
{
    private final int width;
    private final int height;
    private final int depth;

    public static Dimension of(int width, int height)
    {
        return of(width, height, 1);
    }

    public static Dimension of(int width, int height, int depth)
    {
        return new Dimension(width, height, depth);
    }

    protected Dimension(int width, int height, int depth)
    {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getDepth()
    {
        return depth;
    }

    public boolean contains(Location location)
    {
        int x = location.getX();
        int y = location.getY();
        int z = location.getZ();

        return x >= 0 && x < width && y >= 0 && y < height && z >= 0 && z < depth;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Dimension [width=");
        builder.append(width);
        builder.append(", height=");
        builder.append(height);
        builder.append(", depth=");
        builder.append(depth);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + depth;
        result = prime * result + height;
        result = prime * result + width;
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
        Dimension other = (Dimension)obj;
        if (depth != other.depth)
        {
            return false;
        }
        if (height != other.height)
        {
            return false;
        }
        if (width != other.width)
        {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Dimension o)
    {
        int compare = width - o.width;
        if (compare != 0)
        {
            return compare;
        }

        compare = height - o.height;
        if (compare != 0)
        {
            return compare;
        }

        return depth - o.depth;
    }
}

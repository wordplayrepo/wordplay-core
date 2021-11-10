package org.syphr.wordplay.core.component;

import java.util.Collections;
import java.util.SortedSet;

import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

public class WordImpl implements Word
{
    private final String text;
    private final Location startLocation;
    private final Location endLocation;
    private final Orientation orientation;
    private final SortedSet<Tile> tiles;

    public WordImpl(String text,
                    Location startLocation,
                    Location endLocation,
                    Orientation orientation,
                    SortedSet<Tile> tiles)
    {
        this.text = text;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.orientation = orientation;
        this.tiles = tiles;
    }

    @Override
    public Location getStartLocation()
    {
        return startLocation;
    }

    @Override
    public Location getEndLocation()
    {
        return endLocation;
    }

    @Override
    public Orientation getOrientation()
    {
        return orientation;
    }

    @Override
    public SortedSet<Tile> getTiles()
    {
        return Collections.unmodifiableSortedSet(tiles);
    }

    @Override
    public String getText()
    {
        return text;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("WordImpl [text=");
        builder.append(text);
        builder.append(", startLocation=");
        builder.append(startLocation);
        builder.append(", orientation=");
        builder.append(orientation);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
        result = prime * result + ((startLocation == null) ? 0 : startLocation.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        WordImpl other = (WordImpl)obj;
        if (orientation == null)
        {
            if (other.orientation != null)
            {
                return false;
            }
        }
        else if (!orientation.equals(other.orientation))
        {
            return false;
        }
        if (startLocation == null)
        {
            if (other.startLocation != null)
            {
                return false;
            }
        }
        else if (!startLocation.equals(other.startLocation))
        {
            return false;
        }
        if (text == null)
        {
            if (other.text != null)
            {
                return false;
            }
        }
        else if (!text.equals(other.text))
        {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Word o)
    {
        int compare = startLocation.compareTo(o.getStartLocation());
        if (compare != 0)
        {
            return compare;
        }

        /*
         * FIXME
         * 
         * This is not very good practice, but it is unlikely that two different
         * orientations will have the same toString.
         */
        compare = orientation.toString().compareTo(o.getOrientation().toString());
        if (compare != 0)
        {
            return compare;
        }

        return text.compareTo(o.getText());
    }
}

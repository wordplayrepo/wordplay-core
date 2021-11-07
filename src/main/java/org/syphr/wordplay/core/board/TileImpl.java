package org.syphr.wordplay.core.board;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

public class TileImpl implements Tile
{
    private Location location;

    private Piece piece;

    private final Set<TileAttribute> attributes = new HashSet<TileAttribute>();

    public TileImpl()
    {
        super();
    }

    protected TileImpl(Location location, Piece piece)
    {
        this.location = location;
        this.piece = piece;
    }

    protected void setLocation(Location location)
    {
        this.location = location;
    }

    @Override
    public Location getLocation()
    {
        return location;
    }

    @Override
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    @Override
    public Piece getPiece()
    {
        return piece;
    }

    @Override
    public boolean hasPiece()
    {
        return piece != null;
    }

    @Override
    public int getBaseValue()
    {
        if (piece == null)
        {
            return 0;
        }

        return piece.getValue();
    }

    @Override
    public void addAttribute(TileAttribute attribute)
    {
        attributes.add(attribute);
    }

    protected void addAttributes(Collection<TileAttribute> attributes)
    {
        attributes.addAll(attributes);
    }

    @Override
    public void removeAttribute(TileAttribute attribute)
    {
        attributes.remove(attribute);
    }

    @Override
    public Set<TileAttribute> getAttributes()
    {
        return Collections.unmodifiableSet(attributes);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AbstractTile [location=");
        builder.append(location);
        builder.append(", piece=");
        builder.append(piece);
        builder.append(", attributes=");
        builder.append(attributes);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
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
        TileImpl other = (TileImpl)obj;
        if (location == null)
        {
            if (other.location != null)
            {
                return false;
            }
        }
        else if (!location.equals(other.location))
        {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Tile o)
    {
        return location.compareTo(o.getLocation());
    }
}

package org.syphr.wordplay.core.board;

import org.syphr.wordplay.core.space.Location;

public class SinglePlacementImpl implements SinglePlacement
{
    private final Location location;
    private final Piece piece;

    protected SinglePlacementImpl(Location location, Piece piece)
    {
        this.location = location;
        this.piece = piece;
    }

    @Override
    public Location getLocation()
    {
        return location;
    }

    @Override
    public Piece getPiece()
    {
        return piece;
    }
}

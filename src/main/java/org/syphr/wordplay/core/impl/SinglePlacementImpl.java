package org.syphr.wordplay.core.impl;

import org.syphr.wordplay.core.Piece;
import org.syphr.wordplay.core.SinglePlacement;
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

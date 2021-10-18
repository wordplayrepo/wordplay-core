package org.syphr.wordplay.api.impl;

import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Piece;
import org.syphr.wordplay.api.SinglePlacement;

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

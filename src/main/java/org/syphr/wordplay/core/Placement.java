package org.syphr.wordplay.core;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A placement is a specific grouping of pieces with a location and orientation.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Placement
{
    /**
     * Retrieve the starting location of this placement.
     * 
     * @return the stating location
     */
    public Location getStartLocation();

    /**
     * Retrieve the spatial orientation of this placement (i.e. along the
     * x-axis).
     * 
     * @return the orientation
     */
    public Orientation getOrientation();

    /**
     * Retrieve the pieces contained within this placement.
     * 
     * @return the pieces
     */
    public List<Piece> getPieces();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);
}

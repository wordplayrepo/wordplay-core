package org.syphr.wordplay.core;

import java.util.SortedSet;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A word is set of adjacent tiles that are occupied by pieces.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
@Immutable
public interface Word extends Comparable<Word>
{
    /**
     * Retrieve the location of the first tile in this word.
     * 
     * @return the start location
     */
    public Location getStartLocation();

    /**
     * Retrieve the location of the last tile in this word.
     * 
     * @return the end location
     */
    public Location getEndLocation();

    /**
     * Retrieve the spatial orientation of this word (i.e. along the x-axis).
     * 
     * @return the orientation
     */
    public Orientation getOrientation();

    /**
     * Retrieve the string formed by walking this word from start to end and
     * concatenating the letters found on the pieces.
     * 
     * @return the word text
     */
    public String getText();

    /**
     * Retrieve the set of tiles that make up this word in proper order.
     * 
     * @return the set of tiles
     */
    public SortedSet<Tile> getTiles();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}

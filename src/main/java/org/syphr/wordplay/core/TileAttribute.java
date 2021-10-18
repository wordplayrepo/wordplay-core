package org.syphr.wordplay.core;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A tile attribute represents a modifier that is applied to the value of a
 * piece placed on a tile or nearby tiles to increase or decrease the final
 * point score or affect gameplay.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface TileAttribute
{
    /**
     * Modify the given value based on the rules of this attribute.
     * 
     * @param value
     *            the value to modify
     * @param distance
     *            the distance from the tile to which this attribute belongs the
     *            given value was found (for example, if this value comes from
     *            an adjacent tile, the distance would be 1 in at least one
     *            direction and no more than 1 in any direction)
     * @param sameWord
     *            <code>true</code> if the value was found on a tile in the same
     *            word as the tile having this attribute; <code>false</code>
     *            otherwise
     * @return the modified value
     */
    public int modifyValue(int value, Distance distance, boolean sameWord);

    /**
     * Determine whether or not this attribute should be visible to the player
     * before a piece is placed on the tile.
     * 
     * @return <code>true</code> if the attribute is visible; <code>false</code>
     *         otherwise
     */
    public boolean isVisible();
}

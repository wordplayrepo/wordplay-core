package org.syphr.wordplay.core.board;

import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

/**
 * A tile represents a location on the game board that can be occupied by a
 * piece.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Tile extends Comparable<Tile>
{
    /**
     * Retrieve this tile's location.
     * 
     * @return the location
     */
    public Location getLocation();

    /**
     * Set the piece that occupies this tile.
     * 
     * @param piece
     *            the piece
     */
    public void setPiece(Piece piece);

    /**
     * Get the piece that occupies this tile.
     * 
     * @return the piece or <code>null</code> if no piece is set
     */
    public Piece getPiece();

    /**
     * Determine whether or not a piece has been set on this tile.
     * 
     * @return <code>true</code> if a piece occupies this tile;
     *         <code>false</code> otherwise
     */
    public boolean hasPiece();

    /**
     * Retrieve the value of this tile taking into account only the value of a
     * piece on the tile. Attributes are not considered.
     * 
     * @return the value
     */
    public int getBaseValue();

    /**
     * Add the given attribute to this tile that may affect the score or
     * gameplay.
     * 
     * @param attribute
     *            the attribute to add
     */
    public void addAttribute(TileAttribute attribute);

    /**
     * Remove the given attribute from this tile that may affect the score or
     * gameplay.
     * 
     * @param attribute
     *            the attribute to remove
     */
    public void removeAttribute(TileAttribute attribute);

    /**
     * Retrieve the set of attributes associated with this tile.
     * 
     * @return the attributes
     */
    public Set<TileAttribute> getAttributes();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}

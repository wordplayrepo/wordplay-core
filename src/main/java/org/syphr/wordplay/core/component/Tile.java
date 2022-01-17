/*
 * Copyright © 2012-2022 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.wordplay.core.component;

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

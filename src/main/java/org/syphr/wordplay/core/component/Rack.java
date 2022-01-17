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

import java.util.Collection;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A rack represents a player's current set of playable pieces.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Rack
{
    /**
     * Fill this rack by drawing random pieces from the bag until it is
     * {@link #getMaxPieces() full} or the bag runs out of pieces.
     * 
     * @param bag the bag containing pieces to draw
     */
    public void fill(Bag bag);

    /**
     * Exchange the pieces on this rack with an equal number of randomly selected
     * pieces from the bag. New pieces are selected prior to placing the old pieces
     * back in the bag so it is guaranteed that all exchanged pieces will be new.
     * 
     * @param bag the bag into which this rack's pieces will be placed and from
     *            which new pieces will be drawn
     * 
     * @throws NoSuchPieceException     if any of the given pieces are not on this
     *                                  rack
     * @throws NotEnoughPiecesException if the bag does not have enough pieces to
     *                                  complete the exchange
     */
    public void exchange(Collection<Piece> pieces, Bag bag) throws NoSuchPieceException, NotEnoughPiecesException;

    /**
     * Exchange as many pieces as possible from this rack with an equal number of
     * randomly selected pieces from the bag. If the bag does not contain enough
     * pieces to exchange the entire rack, the maximum number of pieces available
     * will be exchanged. New pieces are selected prior to placing the old pieces
     * back in the bag so it is guaranteed that all exchanged pieces will be new.
     * 
     * @param bag the bag into which this rack's pieces will be placed and from
     *            which new pieces will be drawn
     */
    public void exchange(Bag bag);

    /**
     * Clear this rack by dumping its pieces into the given bag.
     * 
     * @param bag the bag into which this rack's pieces will be placed
     */
    public void clear(Bag bag);

    /**
     * Determine whether or not this rack has no pieces.
     * 
     * @return <code>true</code> if this rack has no pieces; <code>false</code>
     *         otherwise
     */
    public boolean isEmpty();

    /**
     * Retrieve the pieces on this rack.
     * 
     * @return the pieces
     */
    public List<Piece> getPieces();

    /**
     * Remove the given piece from this rack.
     * 
     * @param piece the piece to remove
     * 
     * @throws NoSuchPieceException if the given piece is not on this rack
     */
    public void remove(Piece piece) throws NoSuchPieceException;

    /**
     * Remove the given pieces from this rack.
     * 
     * @param pieces the pieces to remove
     * 
     * @throws NoSuchPieceException if any of the given pieces are not on this rack
     */
    public void remove(Collection<Piece> pieces) throws NoSuchPieceException;

    /**
     * Add the given piece to this rack.
     * 
     * @param piece the piece to add
     */
    public void add(Piece piece);

    /**
     * Add the given pieces to this rack.
     * 
     * @param pieces the pieces to add
     */
    public void add(Collection<Piece> pieces);

    /**
     * Retrieve the maximum number of pieces this rack will hold.
     * 
     * @return the maximum number of pieces
     */
    public int getMaxPieces();
}

package org.syphr.wordplay.core.component;

import java.util.Collection;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.lang.Letter;

/**
 * A bag represents the collection of pieces that are neither on the board nor
 * in a player's rack.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Bag
{
    /**
     * Determine whether or not this bag is empty.
     * 
     * @return <code>true</code> if this bag has no pieces; <code>false</code>
     *         otherwise
     */
    public boolean isEmpty();

    /**
     * Retrieve the current count of pieces in this bag.
     * 
     * @return the count
     */
    public int getCount();

    /**
     * Retrieve a random piece from this bag.
     * 
     * @return a random piece
     * 
     * @throws NotEnoughPiecesException if this bag is empty
     */
    public Piece getPiece() throws NotEnoughPiecesException;

    /**
     * Retrieve a piece from this bag that contains the given letter.
     * 
     * @param letter the letter to match
     * 
     * @return the matching piece
     * 
     * @throws NoSuchPieceException if no matching piece exists in this this bag
     */
    public Piece getPiece(Letter letter) throws NoSuchPieceException;

    /**
     * Add the given collection of pieces to this bag and select a number of random
     * pieces equal to the count of the pieces deposited.
     * 
     * @param pieces the pieces to put in the bag
     * 
     * @throws NotEnoughPiecesException if more pieces are given than exist in the
     *                                  bag
     * 
     * @return the same number of pieces that were given randomly selected from this
     *         bag
     */
    public Collection<Piece> exchange(Collection<Piece> pieces) throws NotEnoughPiecesException;

    /**
     * Return the given piece to this bag.
     * 
     * @param piece the piece to return
     */
    public void returnPiece(Piece piece);

    /**
     * Return the given pieces to this bag.
     * 
     * @param pieces the pieces to return
     */
    public void returnPieces(Collection<Piece> pieces);
}

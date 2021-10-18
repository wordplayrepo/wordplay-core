package org.syphr.wordplay.core;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A piece represents a game token that is represented by a {@link Letter} and
 * has attributes such as a value and a wildcard status.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Piece
{
    /**
     * Set the letter that represents this piece.
     * 
     * @param letter the letter
     */
    public void setLetter(Letter letter);

    /**
     * Retrieve the letter that represents this piece.
     * 
     * @return the letter
     */
    public Letter getLetter();

    /**
     * Retrieve the base value of this piece when used in a placement.
     * 
     * @return the base value
     */
    public int getValue();

    /**
     * Determine whether or not this piece represents a wildcard (no specific letter
     * until one is chosen).
     * 
     * @return <code>true</code> if this piece is wild; <code>false</code> otherwise
     */
    public boolean isWild();

    /**
     * Create a copy of this piece.
     * 
     * @return a new piece identical to the original
     */
    public Piece copy();

    /**
     * Two pieces are equal if they are both wild or they both represent the same
     * letter.
     * 
     * @param obj the object to compare against this piece
     * 
     * @return <code>true</code> if the given object is equal to this piece;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}

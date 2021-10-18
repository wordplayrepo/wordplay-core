package org.syphr.wordplay.api;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Generator of {@link Piece pieces}.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface PieceFactory
{
	/**
	 * Create a new piece representing the given letter.
	 * 
	 * @param letter the letter to represent
	 * @return the new piece
	 */
	public Piece createPiece(Letter letter);
}

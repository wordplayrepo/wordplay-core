package org.syphr.wordplay.core.component;

import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.lang.Letter;

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

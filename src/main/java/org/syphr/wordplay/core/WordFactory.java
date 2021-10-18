package org.syphr.wordplay.core;

import java.util.Set;
import java.util.SortedMap;

/**
 * Generator of {@link Word words}.
 * 
 * @author Gregory P. Moyer
 */
public interface WordFactory
{
    /**
     * Create all words from the board relevant to the collection of pieces
     * presented in the given orientation.
     * 
     * @param pieces      the relevant pieces and their locations
     * @param orientation the orientation of the pieces
     * @param board       the current state of the board
     * 
     * @return all relevant words
     */
    public Set<Word> getWords(SortedMap<Location, Piece> pieces, Orientation orientation, Board board);
}

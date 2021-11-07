package org.syphr.wordplay.core;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

/**
 * The calculator determines the score for a given placement of pieces on tiles.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface ScoreCalculator
{
    /**
     * Calculate the score earned by placing the given pieces. Context is provided
     * in the words that are formed by this play and the attributes of the tiles
     * involved.
     * 
     * @param pieces     the pieces involved in the play to be scored
     * @param words      the words formed by the placement of pieces
     * @param attributes the attributes of the tiles involved in the play
     * 
     * @return the score
     */
    public int getScore(SortedMap<Location, Piece> pieces,
                        Set<Word> words,
                        Map<Location, List<TileAttribute>> attributes);
}

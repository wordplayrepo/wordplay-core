package org.syphr.wordplay.core.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.syphr.wordplay.core.Line;
import org.syphr.wordplay.core.Piece;
import org.syphr.wordplay.core.ScoreCalculator;
import org.syphr.wordplay.core.Tile;
import org.syphr.wordplay.core.TileAttribute;
import org.syphr.wordplay.core.Word;
import org.syphr.wordplay.core.space.Distance;
import org.syphr.wordplay.core.space.Location;

import java.util.Set;
import java.util.SortedMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ScoreCalculatorImpl implements ScoreCalculator
{
    private final int rackSize;
    private final int rackBonus;

    @Override
    public int getScore(SortedMap<Location, Piece> pieces, Set<Word> words, Map<Location, List<TileAttribute>> attributes)
    {
        log.trace("Calculating points for location => piece map {}; words {}", pieces, words);

        int points = getBonusPoints(pieces, words);
        log.trace("Bonus points earned {}", points);

        for (Word word : words) {
            log.trace("Calculating score for word {}", word);

            Line wordLine = Line.from(word.getStartLocation(), word.getEndLocation());

            for (Tile tile : word.getTiles()) {
                Location tileLocation = tile.getLocation();

                int tilePoints = tile.hasPiece() ? tile.getBaseValue() : pieces.get(tileLocation).getValue();
                log.trace("Base points scored at location {} = {}", tileLocation, tilePoints);

                for (Entry<Location, List<TileAttribute>> entry : attributes.entrySet()) {
                    Location attributeLocation = entry.getKey();
                    Distance distance = Distance.between(tileLocation, attributeLocation);
                    boolean sameWord = wordLine.contains(attributeLocation);

                    for (TileAttribute attribute : entry.getValue()) {
                        log.trace("Applying tile attribute modification {}", attribute);
                        tilePoints = attribute.modifyValue(tilePoints, distance, sameWord);
                        log.trace("Tile points after modification = {}", tilePoints);
                    }
                }

                points += tilePoints;
            }
        }

        log.trace("Total points scored = {}", points);
        return points;
    }

    protected int getBonusPoints(SortedMap<Location, Piece> pieces, Set<Word> words)
    {
        if (pieces.size() == rackSize) {
            return rackBonus;
        }

        return 0;
    }
}

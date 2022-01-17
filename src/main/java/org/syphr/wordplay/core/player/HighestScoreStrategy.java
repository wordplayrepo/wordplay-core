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
package org.syphr.wordplay.core.player;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.component.Piece;
import org.syphr.wordplay.core.component.ValuedPlacement;

public class HighestScoreStrategy implements RobotStrategy
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HighestScoreStrategy.class);

    private static final Comparator<ValuedPlacement> PLACEMENT_COMPARATOR = new Comparator<ValuedPlacement>()
    {
        @Override
        public int compare(ValuedPlacement p1, ValuedPlacement p2)
        {
            int compare = p2.getPoints() - p1.getPoints();
            if (compare != 0)
            {
                return compare;
            }

            Iterator<Piece> pieceIter1 = p1.getPieces().iterator();
            Iterator<Piece> pieceIter2 = p2.getPieces().iterator();
            while (true)
            {
                boolean firstHasNext = pieceIter1.hasNext();
                boolean secondHasNext = pieceIter2.hasNext();

                if (!firstHasNext && !secondHasNext)
                {
                    return 0;
                }

                if (!firstHasNext)
                {
                    return -1;
                }

                if (!secondHasNext)
                {
                    return 1;
                }

                compare = pieceIter1.next().getLetter().toString().compareTo(pieceIter2.next().getLetter().toString());
                if (compare != 0)
                {
                    return compare;
                }
            }
        }
    };

    private final SortedSet<ValuedPlacement> placements = new TreeSet<ValuedPlacement>(PLACEMENT_COMPARATOR);

    @Override
    public Collection<ValuedPlacement> getDataStructure()
    {
        return placements;
    }

    @Override
    public ValuedPlacement selectPlacement()
    {
        if (placements.isEmpty())
        {
            LOGGER.trace("No placements available");
            return null;
        }

        return placements.first();
    }

    @Override
    public String toString()
    {
        return "Highest Score";
    }
}

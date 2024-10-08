/*
 * Copyright © 2012-2024 Gregory P. Moyer
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.component.Piece;
import org.syphr.wordplay.core.component.Placement;
import org.syphr.wordplay.core.component.ValuedPlacement;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.lang.LetterFactory;

import com.google.common.collect.Iterators;

public class SaveEnablingLettersStrategy extends HighestScoreStrategy
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveEnablingLettersStrategy.class);

    private static final Comparator<Entry<Character, Integer>> COMPARATOR = new Comparator<>()
    {
        @Override
        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2)
        {
            return -1 * o1.getValue().compareTo(o2.getValue());
        }
    };

    private final double maxPointSacrificePercent;

    private final int minPointThreshold;

    private final Set<Letter> enablingLetters;

    public SaveEnablingLettersStrategy(double maxPointSacrificePercent,
                                       int minPointThreshold,
                                       Set<Letter> enablingLetters)
    {
        this.maxPointSacrificePercent = maxPointSacrificePercent;
        this.minPointThreshold = minPointThreshold;
        this.enablingLetters = new HashSet<>(enablingLetters);
    }

    public SaveEnablingLettersStrategy(int letterCount,
                                       double maxPointSacrificePercent,
                                       int minPointThreshold,
                                       Configuration configuration)
    {
        this.maxPointSacrificePercent = maxPointSacrificePercent;
        this.minPointThreshold = minPointThreshold;
        this.enablingLetters = defineEnablingLetters(letterCount, configuration);
    }

    protected Set<Letter> defineEnablingLetters(int letterCount, Configuration configuration)
    {
        Map<Character, Integer> startFreqMap = new HashMap<>();
        Map<Character, Integer> endFreqMap = new HashMap<>();

        for (String word : configuration.getDictionary().getWords()) {
            char firstChar = word.charAt(0);
            char lastChar = word.charAt(word.length() - 1);

            startFreqMap.put(firstChar, increment(startFreqMap.get(firstChar)));
            endFreqMap.put(lastChar, increment(endFreqMap.get(lastChar)));
        }

        List<Entry<Character, Integer>> startFreq = new ArrayList<>(startFreqMap.entrySet());
        Collections.sort(startFreq, COMPARATOR);
        List<Entry<Character, Integer>> endFreq = new ArrayList<>(endFreqMap.entrySet());
        Collections.sort(endFreq, COMPARATOR);

        Iterable<Entry<Character, Integer>> alternator = new AlternatingIterable<>(startFreq, endFreq);

        LetterFactory factory = configuration.getLetterFactory();
        Set<Letter> set = new HashSet<>();
        for (Entry<Character, Integer> entry : alternator) {
            set.add(factory.toLetter(entry.getKey()));

            if (set.size() == letterCount) {
                break;
            }
        }

        return set;
    }

    protected Integer increment(Integer value)
    {
        if (value == null) {
            return 0;
        }

        return ++value;
    }

    public double getMaxPointSacrificePercent()
    {
        return maxPointSacrificePercent;
    }

    public int getMinPointThreshold()
    {
        return minPointThreshold;
    }

    public Set<Letter> getEnablingLetters()
    {
        return Collections.unmodifiableSet(enablingLetters);
    }

    @Override
    public ValuedPlacement selectPlacement()
    {
        Collection<ValuedPlacement> placements = getDataStructure();
        if (placements.isEmpty()) {
            LOGGER.trace("No placements available");
            return null;
        }

        Iterator<ValuedPlacement> placementIter = placements.iterator();
        ValuedPlacement highScorePlacement = placementIter.next();

        if (!hasEnablingLetters(highScorePlacement)) {
            LOGGER.trace("Selecting highest score placement since it has no enabling letters");
            return highScorePlacement;
        }

        int maxPoints = highScorePlacement.getPoints();
        LOGGER.trace("Highest score placement yields {} points", maxPoints);

        /*
         * If the maximum possible points this round is less than the minimum for
         * playing an enabling letter, set the minimum for an alternate placement to 1
         * so that any placement without enabling letters will be played.
         */
        int minPoints = maxPoints < minPointThreshold ? 1
                : (int) Math.round(maxPoints * (1 - maxPointSacrificePercent));
        LOGGER.trace("Looking for a placement without enabling letters that provides at least {} point(s)", minPoints);

        while (placementIter.hasNext()) {
            ValuedPlacement placement = placementIter.next();
            if (placement.getPoints() < minPoints) {
                LOGGER.trace("No placements available with no enabling letters above the minimum score");
                break;
            }

            if (!hasEnablingLetters(placement)) {
                LOGGER.trace("Found a placement with no enabling letters yielding {} point(s)", placement.getPoints());
                return placement;
            }
        }

        LOGGER.trace("Selecting the highest score placement");
        return highScorePlacement;
    }

    protected boolean hasEnablingLetters(Placement placement)
    {
        Set<Letter> letters = toLetters(placement);
        letters.retainAll(enablingLetters);

        return !letters.isEmpty();
    }

    protected Set<Letter> toLetters(Placement placement)
    {
        return placement.getPieces()
                        .stream()
                        .map(Piece::getLetter)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Save Enabling Letters (");

        for (Letter letter : enablingLetters) {
            builder.append(letter).append(", ");
        }

        builder.setLength(builder.length() - 2);
        builder.append(')');

        return builder.toString();
    }

    protected static class AlternatingIterable<T> implements Iterable<T>
    {
        private final Iterable<T>[] iterables;

        @SafeVarargs
        public AlternatingIterable(Iterable<T>... iterables)
        {
            this.iterables = iterables;
        }

        @Override
        public Iterator<T> iterator()
        {
            @SuppressWarnings("unchecked")
            Iterator<T>[] iterators = new Iterator[iterables.length];

            for (int i = 0; i < iterables.length; i++) {
                iterators[i] = iterables[i].iterator();
            }

            return new AlternatingIterator<>(iterators);
        }
    }

    protected static class AlternatingIterator<T> implements Iterator<T>
    {
        private final Iterator<Iterator<T>> iterators;

        private Iterator<T> current;

        @SafeVarargs
        public AlternatingIterator(Iterator<T>... iterators)
        {
            this.iterators = Iterators.cycle(iterators);
        }

        @Override
        public boolean hasNext()
        {
            return iterators.hasNext();
        }

        @Override
        public T next()
        {
            current = iterators.next();

            T value = current.next();

            if (!current.hasNext()) {
                iterators.remove();
            }

            return value;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}

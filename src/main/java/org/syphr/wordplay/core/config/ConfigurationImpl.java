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
package org.syphr.wordplay.core.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.syphr.wordplay.core.lang.Dictionary;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.lang.LetterFactory;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

// TODO @Immutable
public class ConfigurationImpl implements Configuration
{
    private final Dimension boardDimension;
    private final Location boardStart;
    private final Set<Orientation> orientations;
    private final Map<Location, Set<TileAttribute>> tileAttributes;

    private final int rackSize;
    private final int rackBonus;

    private final LetterFactory letterFactory;
    private final Dictionary dictionary;

    private final Map<Letter, Integer> letterCounts;
    private final Map<Letter, Integer> letterValues;

    private final Map<String, Object> extensions;

    protected ConfigurationImpl(Dimension boardDimension,
                                Location boardStart,
                                Set<Orientation> orientations,
                                Map<Location, Set<TileAttribute>> tileAttributes,
                                int rackSize,
                                int rackBonus,
                                LetterFactory letterFactory,
                                Dictionary dictionary,
                                Map<Letter, Integer> letterCounts,
                                Map<Letter, Integer> letterValues,
                                Map<String, Object> extensions)
    {
        this.boardDimension = boardDimension;
        this.boardStart = boardStart;
        this.orientations = Collections.unmodifiableSet(new HashSet<>(orientations));
        this.tileAttributes = Collections.unmodifiableMap(new HashMap<>(tileAttributes));
        this.rackSize = rackSize;
        this.rackBonus = rackBonus;
        this.letterFactory = letterFactory;
        this.dictionary = dictionary;
        this.letterCounts = Collections.unmodifiableMap(new HashMap<>(letterCounts));
        this.letterValues = Collections.unmodifiableMap(new HashMap<>(letterValues));
        this.extensions = Collections.unmodifiableMap(new HashMap<>(extensions));
    }

    @Override
    public Dimension getBoardDimension()
    {
        return boardDimension;
    }

    @Override
    public Location getBoardStart()
    {
        return boardStart;
    }

    @Override
    public Set<Orientation> getOrientations()
    {
        return orientations;
    }

    @Override
    public Set<TileAttribute> getTileAttributes(Location location)
    {
        Set<TileAttribute> attributes = tileAttributes.get(location);
        return attributes != null
                ? Collections.unmodifiableSet(attributes)
                : Collections.<TileAttribute>emptySet();
    }

    @Override
    public int getRackSize()
    {
        return rackSize;
    }

    @Override
    public int getRackBonus()
    {
        return rackBonus;
    }

    @Override
    public LetterFactory getLetterFactory()
    {
        return letterFactory;
    }

    @Override
    public Dictionary getDictionary()
    {
        return dictionary;
    }

    @Override
    public int getLetterCount(Letter letter)
    {
        Integer count = letterCounts.get(letter);
        return count == null ? 0 : count;
    }

    @Override
    public int getLetterValue(Letter letter)
    {
        Integer value = letterValues.get(letter);
        return value == null ? 0 : value;
    }

    @Override
    public Object getExtension(String id)
    {
        return getExtensions().get(id);
    }

    @Override
    public Map<String, Object> getExtensions()
    {
        return extensions;
    }
}

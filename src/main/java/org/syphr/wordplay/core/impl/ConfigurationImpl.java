package org.syphr.wordplay.core.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.syphr.wordplay.core.Configuration;
import org.syphr.wordplay.core.Dictionary;
import org.syphr.wordplay.core.Dimension;
import org.syphr.wordplay.core.Letter;
import org.syphr.wordplay.core.LetterFactory;
import org.syphr.wordplay.core.Location;
import org.syphr.wordplay.core.Orientation;
import org.syphr.wordplay.core.TileAttribute;

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

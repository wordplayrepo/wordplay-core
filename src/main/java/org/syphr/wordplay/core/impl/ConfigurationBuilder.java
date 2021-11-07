package org.syphr.wordplay.core.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.syphr.wordplay.core.Configuration;
import org.syphr.wordplay.core.Dictionary;
import org.syphr.wordplay.core.Letter;
import org.syphr.wordplay.core.LetterFactory;
import org.syphr.wordplay.core.TileAttribute;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;
import org.syphr.wordplay.core.space.Orientations;

public class ConfigurationBuilder
{
    private Dimension boardDimension = Dimension.of(9, 9);
    private Location boardStart = Location.at(4, 4);
    private Set<Orientation> orientations = Orientations.setOf(Orientations.x(), Orientations.y());
    private Map<Location, Set<TileAttribute>> tileAttributes = new HashMap<>();

    private int rackSize = 5;
    private int rackBonus = 0;

    private LetterFactory letterFactory;
    private Dictionary dictionary;

    private Map<Letter, Integer> letterCounts;
    private Map<Letter, Integer> letterValues;

    private Map<String, Object> extensions = new HashMap<>();

    public Configuration build()
    {
        Configuration config = new ConfigurationImpl(boardDimension,
                                                     boardStart,
                                                     orientations,
                                                     tileAttributes,
                                                     rackSize,
                                                     rackBonus,
                                                     letterFactory,
                                                     dictionary,
                                                     letterCounts,
                                                     letterValues,
                                                     extensions);
        return config;
    }

    public ConfigurationBuilder boardDimension(Dimension dimension)
    {
        boardDimension = dimension;
        return this;
    }

    public ConfigurationBuilder boardStart(Location location)
    {
        boardStart = location;
        return this;
    }

    public ConfigurationBuilder orientations(Orientation... orientations)
    {
        this.orientations = Orientations.setOf(orientations);
        return this;
    }

    public ConfigurationBuilder tileAttribute(Location location, TileAttribute attribute)
    {
        Set<TileAttribute> attributes = tileAttributes.get(location);
        if (attributes == null) {
            attributes = new HashSet<>();
            tileAttributes.put(location, attributes);
        }

        attributes.add(attribute);
        return this;
    }

    public ConfigurationBuilder rackSize(int rackSize)
    {
        this.rackSize = rackSize;
        return this;
    }

    public ConfigurationBuilder rackBonus(int rackBonus)
    {
        this.rackBonus = rackBonus;
        return this;
    }

    public ConfigurationBuilder letterFactory(LetterFactory letterFactory)
    {
        this.letterFactory = letterFactory;
        letterCounts = new HashMap<>();
        letterValues = new HashMap<>();
        return this;
    }

    public ConfigurationBuilder dictionary(Dictionary dictionary)
    {
        this.dictionary = dictionary;
        return this;
    }

    public ConfigurationBuilder letterCount(Letter letter, int count)
    {
        letterCounts.put(letter, count);
        return this;
    }

    public ConfigurationBuilder allLetterCount(int count)
    {
        letterCounts = letterFactory.getLetters().stream().collect(Collectors.toMap(Function.identity(), l -> count));
        return this;
    }

    public ConfigurationBuilder letterValue(Letter letter, int value)
    {
        letterValues.put(letter, value);
        return this;
    }

    public ConfigurationBuilder allLetterValue(int value)
    {
        letterValues = letterFactory.getLetters().stream().collect(Collectors.toMap(Function.identity(), l -> value));
        return this;
    }

    public ConfigurationBuilder extension(String id, Object value)
    {
        extensions.put(id, value);
        return this;
    }
}

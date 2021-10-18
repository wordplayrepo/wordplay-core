package org.syphr.wordplay.api.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.syphr.wordplay.api.Configuration;
import org.syphr.wordplay.api.Dictionary;
import org.syphr.wordplay.api.Dimension;
import org.syphr.wordplay.api.Letter;
import org.syphr.wordplay.api.LetterFactory;
import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Orientation;
import org.syphr.wordplay.api.Orientations;
import org.syphr.wordplay.api.TileAttribute;

public class ConfigurationBuilder
{
    private Dimension boardDimension;
    private Location boardStart;
    private Set<Orientation> orientations;
    private Map<Location, Set<TileAttribute>> tileAttributes;

    private int rackSize;
    private int rackBonus;

    private LetterFactory letterFactory;
    private Dictionary dictionary;

    private Map<Letter, Integer> letterCounts;
    private Map<Letter, Integer> letterValues;

    private Map<String, Object> extensions;

    public ConfigurationBuilder()
    {
        super();
        reset();
    }

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
        reset();
        return config;
    }

    private void reset()
    {
        boardDimension = Dimension.of(9, 9);
        boardStart = Location.at(4, 4);
        orientations = Orientations.setOf(Orientations.x(), Orientations.y());
        tileAttributes = new HashMap<>();

        rackSize = 5;
        rackBonus = 0;

        letterFactory = new EnglishLetterFactory();
        dictionary = new EnableDictionary();

        defaultLetterCountsAndValues();

        extensions = new HashMap<>();
    }

    private void defaultLetterCountsAndValues()
    {
        allLetterCount(5);
        allLetterValue(1);
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
        if (attributes == null)
        {
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
        letterCounts = letterFactory.getLetters().stream().collect(Collectors.toMap(Function.identity(),
                                                                                    l -> count));
        return this;
    }

    public ConfigurationBuilder letterValue(Letter letter, int value)
    {
        letterValues.put(letter, value);
        return this;
    }

    public ConfigurationBuilder allLetterValue(int value)
    {
        letterValues = letterFactory.getLetters().stream().collect(Collectors.toMap(Function.identity(),
                                                                                    l -> value));
        return this;
    }

    public ConfigurationBuilder extension(String id, Object value)
    {
        extensions.put(id, value);
        return this;
    }
}

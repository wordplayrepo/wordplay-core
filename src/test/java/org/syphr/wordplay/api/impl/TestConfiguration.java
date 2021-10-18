package org.syphr.wordplay.api.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.syphr.wordplay.api.Configuration;
import org.syphr.wordplay.api.Dictionary;
import org.syphr.wordplay.api.Dimension;
import org.syphr.wordplay.api.Letter;
import org.syphr.wordplay.api.LetterFactory;
import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Orientation;
import org.syphr.wordplay.api.TileAttribute;

public class TestConfiguration implements Configuration
{
    private Dimension boardDimension;
    private Location boardStart;
    private Set<Orientation> orientations = new HashSet<>();
    private Map<Location, Set<TileAttribute>> tileAttributes = new HashMap<>();

    private int rackSize;
    private int rackBonus;

    private LetterFactory letterFactory = new EnglishLetterFactory();
    private Dictionary dictionary;

    private Map<Letter, Integer> letterCounts = new HashMap<>();
    private Map<Letter, Integer> letterValues = new HashMap<>();

    private Map<String, Object> extensions = new HashMap<>();

    @Override
    public Dimension getBoardDimension()
    {
        return boardDimension;
    }

    public void setBoardDimension(Dimension boardDimension)
    {
        this.boardDimension = boardDimension;
    }

    @Override
    public Location getBoardStart()
    {
        return boardStart;
    }

    public void setBoardStart(Location boardStart)
    {
        this.boardStart = boardStart;
    }

    @Override
    public Set<Orientation> getOrientations()
    {
        return orientations;
    }

    public void setOrientations(Set<Orientation> orientations)
    {
        this.orientations = orientations;
    }

    public Map<Location, Set<TileAttribute>> getTileAttributes()
    {
        return tileAttributes;
    }

    public void setTileAttributes(Map<Location, Set<TileAttribute>> tileAttributes)
    {
        this.tileAttributes = tileAttributes;
    }

    @Override
    public Set<TileAttribute> getTileAttributes(Location location)
    {
        Set<TileAttribute> attributes = tileAttributes.get(location);
        return attributes != null ? attributes : Collections.<TileAttribute>emptySet();
    }

    @Override
    public int getRackSize()
    {
        return rackSize;
    }

    public void setRackSize(int rackSize)
    {
        this.rackSize = rackSize;
    }

    @Override
    public int getRackBonus()
    {
        return rackBonus;
    }

    public void setRackBonus(int rackBonus)
    {
        this.rackBonus = rackBonus;
    }

    @Override
    public LetterFactory getLetterFactory()
    {
        return letterFactory;
    }

    public void setLetterFactory(LetterFactory letterFactory)
    {
        this.letterFactory = letterFactory;
    }

    @Override
    public Dictionary getDictionary()
    {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary)
    {
        this.dictionary = dictionary;
    }

    public Map<Letter, Integer> getLetterCounts()
    {
        return letterCounts;
    }

    public void setLetterCounts(Map<Letter, Integer> letterCounts)
    {
        this.letterCounts = letterCounts;
    }

    @Override
    public int getLetterCount(Letter letter)
    {
        Integer count = letterCounts.get(letter);
        return count == null ? 0 : count;
    }

    public Map<Letter, Integer> getLetterValues()
    {
        return letterValues;
    }

    public void setLetterValues(Map<Letter, Integer> letterValues)
    {
        this.letterValues = letterValues;
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
        return extensions.get(id);
    }

    @Override
    public Map<String, Object> getExtensions()
    {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions)
    {
        this.extensions = extensions;
    }
}

package org.syphr.wordplay.api.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.syphr.wordplay.api.Dictionary;
import org.syphr.wordplay.api.Dimension;
import org.syphr.wordplay.api.Configuration;
import org.syphr.wordplay.api.Letter;
import org.syphr.wordplay.api.LetterFactory;
import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Orientation;
import org.syphr.wordplay.api.TileAttribute;
import org.syphr.wordplay.xsd.v1.ClassType;
import org.syphr.wordplay.xsd.v1.LetterMetaType;
import org.syphr.wordplay.xsd.v1.TileType;
import org.syphr.wordplay.xsd.v1.TilesType;

public class XmlV1Configuration implements Configuration
{
    private final org.syphr.wordplay.xsd.v1.Configuration externalConfig;

    private Dimension boardDimension;
    private Location boardStart;
    private Set<Orientation> orientations;
    private Map<Location, Set<TileAttribute>> tileAttributes;

    private LetterFactory letterFactory;
    private Dictionary dictionary;

    private Map<Letter, Integer> letterCounts;
    private Map<Letter, Integer> letterValues;

    private Map<String, Object> extensions;

    public XmlV1Configuration(org.syphr.wordplay.xsd.v1.Configuration externalConfig)
    {
        this.externalConfig = externalConfig;
    }

    public org.syphr.wordplay.xsd.v1.Configuration getExternalConfig()
    {
        return externalConfig;
    }

    @Override
    public Dimension getBoardDimension()
    {
        if (boardDimension == null)
        {
            boardDimension = JaxbUtils.getDimension(externalConfig.getBoard().getDimension());
        }

        return boardDimension;
    }

    @Override
    public Location getBoardStart()
    {
        if (boardStart == null)
        {
            boardStart = JaxbUtils.getLocation(externalConfig.getBoard().getStart());
        }

        return boardStart;
    }

    @Override
    public Set<Orientation> getOrientations()
    {
        if (orientations == null)
        {
            orientations = new LinkedHashSet<Orientation>();

            for (ClassType classType : externalConfig.getBoard().getOrientations().getOrientations())
            {
                Orientation orientation = JaxbUtils.getInstance(classType);
                orientations.add(orientation);
            }
        }

        return orientations;
    }

    @Override
    public Set<TileAttribute> getTileAttributes(Location location)
    {
        if (tileAttributes == null)
        {
            tileAttributes = new HashMap<Location, Set<TileAttribute>>();

            TilesType tiles = externalConfig.getBoard().getTiles();
            if (tiles != null)
            {
                for (TileType tile : tiles.getTiles())
                {
                    Location tileLocation = JaxbUtils.getLocation(tile.getLocation());

                    Set<TileAttribute> attributes = new HashSet<TileAttribute>();
                    for (ClassType classType : tile.getAttributes().getAttributes())
                    {
                        TileAttribute attribute = JaxbUtils.getInstance(classType);
                        attributes.add(attribute);
                    }

                    tileAttributes.put(tileLocation, attributes);
                }
            }
        }

        Set<TileAttribute> attributes = tileAttributes.get(location);
        return attributes != null
                ? Collections.unmodifiableSet(attributes)
                : Collections.<TileAttribute>emptySet();
    }

    @Override
    public int getRackSize()
    {
        return externalConfig.getRack().getSize();
    }

    @Override
    public int getRackBonus()
    {
        return externalConfig.getRack().getBonus();
    }

    @Override
    public LetterFactory getLetterFactory()
    {
        if (letterFactory == null)
        {
            letterFactory = JaxbUtils.getInstance(externalConfig.getLetters().getFactory());
        }

        return letterFactory;
    }

    @Override
    public Dictionary getDictionary()
    {
        if (dictionary == null)
        {
            dictionary = JaxbUtils.getInstance(externalConfig.getLetters().getDictionary());
        }

        return dictionary;
    }

    @Override
    public int getLetterCount(Letter letter)
    {
        if (letterCounts == null)
        {
            letterCounts = new HashMap<Letter, Integer>();

            LetterFactory letters = getLetterFactory();
            for (LetterMetaType metaType : externalConfig.getLetters().getCounts().getCounts())
            {
                String letterStr = metaType.getLetter();
                Letter newLetter = letterStr == null ? null : letters.toLetter(letterStr);
                letterCounts.put(newLetter, metaType.getValue());
            }
        }

        Integer count = letterCounts.get(letter);
        return count == null ? 0 : count;
    }

    @Override
    public int getLetterValue(Letter letter)
    {
        if (letterValues == null)
        {
            letterValues = new HashMap<Letter, Integer>();

            LetterFactory letters = getLetterFactory();
            for (LetterMetaType metaType : externalConfig.getLetters().getValues().getValues())
            {
                String letterStr = metaType.getLetter();
                Letter newLetter = letterStr == null ? null : letters.toLetter(letterStr);
                letterValues.put(newLetter, metaType.getValue());
            }
        }

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
        if (extensions == null)
        {
            extensions = JaxbUtils.getExtensions(externalConfig.getExtensions());
        }

        return Collections.unmodifiableMap(extensions);
    }
}

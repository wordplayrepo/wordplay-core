package org.syphr.wordplay.core;

import java.util.Map;
import java.util.Set;

import org.syphr.wordplay.core.impl.ConfigurationBuilder;

public interface Configuration
{
    public Dimension getBoardDimension();

    public Location getBoardStart();

    public Set<Orientation> getOrientations();

    public Set<TileAttribute> getTileAttributes(Location location);

    public int getRackSize();

    public int getRackBonus();

    public LetterFactory getLetterFactory();

    public Dictionary getDictionary();

    public int getLetterCount(Letter letter);

    public int getLetterValue(Letter letter);

    public Object getExtension(String id);

    public Map<String, Object> getExtensions();

    public static ConfigurationBuilder builder()
    {
        return new ConfigurationBuilder();
    }
}
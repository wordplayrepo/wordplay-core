package org.syphr.wordplay.core.config;

import java.util.Map;
import java.util.Set;

import org.syphr.wordplay.core.lang.Dictionary;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.lang.LetterFactory;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

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
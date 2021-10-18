package org.syphr.wordplay.core;

import java.util.HashMap;
import java.util.Map;

import org.syphr.wordplay.core.impl.MultiplierAttribute;

public class TileAttributes
{
    private static final Map<Integer, TileAttribute> LETTER_MULTIPLIERS = new HashMap<Integer, TileAttribute>();

    private static final Map<Integer, TileAttribute> WORD_MULTIPLIERS = new HashMap<Integer, TileAttribute>();

    public static TileAttribute letterMultiplier(int multiplier)
    {
        TileAttribute attribute = LETTER_MULTIPLIERS.get(multiplier);
        if (attribute == null)
        {
            attribute = new MultiplierAttribute(Distances.zero(), multiplier, true, true);
            LETTER_MULTIPLIERS.put(multiplier, attribute);
        }

        return attribute;
    }

    public static TileAttribute wordMultiplier(int multiplier)
    {
        TileAttribute attribute = WORD_MULTIPLIERS.get(multiplier);
        if (attribute == null)
        {
            attribute = new MultiplierAttribute(Distances.max(), multiplier, true, true);
            WORD_MULTIPLIERS.put(multiplier, attribute);
        }

        return attribute;
    }

    private TileAttributes()
    {
        /*
         * Factory pattern
         */
    }
}

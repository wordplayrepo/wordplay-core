package org.syphr.wordplay.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.syphr.wordplay.api.RobotStrategy;
import org.syphr.wordplay.api.ValuedPlacement;

public class RandomSelectionStrategy implements RobotStrategy
{
    private final Random rng = new Random();

    private final List<ValuedPlacement> placements = new ArrayList<ValuedPlacement>();

    @Override
    public Collection<ValuedPlacement> getDataStructure()
    {
        return placements;
    }

    @Override
    public ValuedPlacement selectPlacement()
    {
        if (placements.isEmpty())
        {
            return null;
        }

        return placements.get(rng.nextInt(placements.size()));
    }

    @Override
    public String toString()
    {
        return "Random Selection";
    }
}

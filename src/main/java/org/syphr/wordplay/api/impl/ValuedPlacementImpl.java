package org.syphr.wordplay.api.impl;

import org.syphr.wordplay.api.ValuedPlacement;

public class ValuedPlacementImpl extends PlacementImpl implements ValuedPlacement
{
    private int points;

    @Override
    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }
}

package org.syphr.wordplay.core;

import org.junit.Assert;
import org.junit.Test;
import org.syphr.wordplay.core.Line;
import org.syphr.wordplay.core.Location;

public class LineTest
{
    @Test
    public void testHorizontalContains()
    {
        Line line = Line.from(Location.at(1, 1), Location.at(4, 1));

        Assert.assertTrue(line.contains(Location.at(2, 1)));
        Assert.assertFalse(line.contains(Location.at(0, 1)));
        Assert.assertFalse(line.contains(Location.at(5, 1)));
        Assert.assertFalse(line.contains(Location.at(2, 2)));
        Assert.assertFalse(line.contains(Location.at(2, 0)));
    }

    @Test
    public void testVerticalContains()
    {
        Line line = Line.from(Location.at(1, 1), Location.at(1, 4));

        Assert.assertTrue(line.contains(Location.at(1, 2)));
        Assert.assertFalse(line.contains(Location.at(1, 0)));
        Assert.assertFalse(line.contains(Location.at(1, 5)));
        Assert.assertFalse(line.contains(Location.at(2, 2)));
        Assert.assertFalse(line.contains(Location.at(0, 2)));
    }

    @Test
    public void testDiagonalContains()
    {
        Line line = Line.from(Location.at(1, 1), Location.at(4, 4));

        Assert.assertTrue(line.contains(Location.at(2, 2)));
        Assert.assertFalse(line.contains(Location.at(0, 0)));
        Assert.assertFalse(line.contains(Location.at(5, 5)));
        Assert.assertFalse(line.contains(Location.at(2, 1)));
        Assert.assertFalse(line.contains(Location.at(2, 3)));
    }
}

package org.syphr.wordplay.core.space;

public class Distances
{
    private static final Distance ZERO_DISTANCE = Distance.of(0, 0, 0);

    private static final Distance MAX_DISTANCE = Distance.of(Integer.MAX_VALUE,
                                                             Integer.MAX_VALUE,
                                                             Integer.MAX_VALUE);

    public static Distance zero()
    {
        return ZERO_DISTANCE;
    }

    public static Distance max()
    {
        return MAX_DISTANCE;
    }

    private Distances()
    {
        /*
         * Factory pattern
         */
    }
}

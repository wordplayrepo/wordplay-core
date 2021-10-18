package org.syphr.wordplay.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.syphr.wordplay.core.impl.XOrientation;
import org.syphr.wordplay.core.impl.YOrientation;
import org.syphr.wordplay.core.impl.ZOrientation;

import com.google.common.collect.Sets;

public class Orientations
{
    private static final Orientation X = new XOrientation();

    private static final Orientation Y = new YOrientation();

    private static final Orientation Z = new ZOrientation();

    private static final Set<Orientation> XY = Collections.unmodifiableSet(Sets.newHashSet(X, Y));

    private static final Set<Orientation> XYZ = Collections.unmodifiableSet(Sets.newHashSet(X,
                                                                                            Y,
                                                                                            Z));

    public static Orientation x()
    {
        return X;
    }

    public static Orientation y()
    {
        return Y;
    }

    public static Orientation z()
    {
        return Z;
    }

    public static Set<Orientation> xy()
    {
        return XY;
    }

    public static Set<Orientation> xyz()
    {
        return XYZ;
    }

    public static Set<Orientation> setOf(Orientation... orientations)
    {
        Set<Orientation> set = new HashSet<Orientation>();

        for (Orientation orientation : orientations)
        {
            set.add(orientation);
        }

        return set;
    }

    private Orientations()
    {
        /*
         * Factory pattern
         */
    }
}

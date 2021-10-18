package org.syphr.wordplay.api;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

// TODO deal with hashCode/equals - placements need to test the orientation
@ThreadSafe
@Immutable
public interface Orientation
{
    public Location move(Location location, int amount);

    public boolean contains(Distance distance);
}

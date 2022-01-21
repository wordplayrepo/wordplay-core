/*
 * Copyright © 2012-2022 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.wordplay.core.space;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * This interface represents a spatial orientation that defines an infinite
 * length line along which any number of {@link Location locations} can exist.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
@Immutable
public interface Orientation
{
    /**
     * Move from the given location by the provided amount along the line defined by
     * this orientation.
     * 
     * @param location the starting location
     * @param amount   length of the move (can be negative or positive)
     * 
     * @return the ending location after the move
     */
    Location move(Location location, int amount);

    /**
     * Determine if the given distance exists entirely within the line defined by
     * this orientation.
     * 
     * @param distance the distance to check
     * 
     * @return <code>true</code> if the components of the given distance are within
     *         the bounds of this orientation; <code>false</code> otherwise
     */
    boolean contains(Distance distance);

    /**
     * Compares the specified object with this orientation for equality.
     * <p>
     * While the this interface adds no stipulations to the general contract for the
     * {@code Object.equals}, all implementations of this interface are expected to
     * have appropriate semantics for equality and hash codes.
     *
     * @param o object to be compared for equality with this orientation
     * 
     * @return <code>true</code> if the specified object is equal to this
     *         orientation; <code>false</code> otherwise
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns the hash code value for this orientation.
     * <p>
     * While this interface adds no stipulations to the general contract for the
     * {@code Object.hashCode} method, all implementations of this interface are
     * expected to have appropriate semantics for equality and hash codes.
     *
     * @return the hash code value for this orientation
     */
    @Override
    int hashCode();
}

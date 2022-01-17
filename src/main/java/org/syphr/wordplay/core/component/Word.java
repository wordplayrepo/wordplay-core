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
package org.syphr.wordplay.core.component;

import java.util.SortedSet;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

/**
 * A word is set of adjacent tiles that are occupied by pieces.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
@Immutable
public interface Word extends Comparable<Word>
{
    /**
     * Retrieve the location of the first tile in this word.
     * 
     * @return the start location
     */
    public Location getStartLocation();

    /**
     * Retrieve the location of the last tile in this word.
     * 
     * @return the end location
     */
    public Location getEndLocation();

    /**
     * Retrieve the spatial orientation of this word (i.e. along the x-axis).
     * 
     * @return the orientation
     */
    public Orientation getOrientation();

    /**
     * Retrieve the string formed by walking this word from start to end and
     * concatenating the letters found on the pieces.
     * 
     * @return the word text
     */
    public String getText();

    /**
     * Retrieve the set of tiles that make up this word in proper order.
     * 
     * @return the set of tiles
     */
    public SortedSet<Tile> getTiles();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}

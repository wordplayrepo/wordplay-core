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

import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

/**
 * A board represents the playing area for a game. It consists of a set of tiles
 * on which placements of pieces can be made. These tiles can also have other
 * attributes that affect the score or gameplay when a piece is played on them.
 *
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Board
{
    /**
     * Retrieve the sizing of this board.
     *
     * @return the size in as many dimensions as are supported by this board (i.e.
     *         2D or 3D)
     */
    public Dimension getDimension();

    /**
     * Determine whether or not the given placement is valid on this board given the
     * current state of other placements (if any exist).
     *
     * @param placement the placement to validate
     * 
     * @return <code>true</code> if the placement is valid; <code>false</code>
     *         otherwise
     */
    public boolean isValid(Placement placement);

    /**
     * Calculate the score that the given placement would receive.
     *
     * @param placement the placement to calculate
     * 
     * @return the score that would be earned by the given placement
     */
    public int calculatePoints(Placement placement);

    /**
     * Commit the given placement to this board.
     *
     * @param placement the placement to commit
     * 
     * @return the score earned by the placement
     * 
     * @throws PlacementException if the given placement is not valid on this board
     */
    public int place(Placement placement) throws PlacementException;

    /**
     * Retrieve the set of tiles that make up this board.
     *
     * @return the tile set
     */
    public TileSet getTiles();

    /**
     * Retrieve the starting location for this board.
     *
     * @return the starting location
     */
    public Location getStart();

    /**
     * Retrieve the allow orientations for the board.
     * 
     * @return allowed orientations
     */
    public Set<Orientation> getOrientations();
}

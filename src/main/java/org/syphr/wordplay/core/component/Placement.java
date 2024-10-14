/*
 * Copyright © 2012-2024 Gregory P. Moyer
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

import java.util.List;

import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

/**
 * A placement is a specific grouping of pieces with a location and orientation.
 *
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface Placement
{
    /**
     * Retrieve the starting location of this placement.
     *
     * @return the stating location
     */
    Location startLocation();

    /**
     * Retrieve the spatial orientation of this placement (e.g. along the x-axis).
     *
     * @return the orientation
     */
    Orientation orientation();

    /**
     * Retrieve the pieces contained within this placement.
     *
     * @return the pieces
     */
    List<Piece> pieces();

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);
}

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

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.concurrent.Immutable;

import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Immutable
public class WordImpl implements Word
{
    @EqualsAndHashCode.Include
    @ToString.Include
    private final String text;
    @EqualsAndHashCode.Include
    @ToString.Include
    private final Location startLocation;
    private final Location endLocation;
    @EqualsAndHashCode.Include
    @ToString.Include
    private final Orientation orientation;
    private final SortedSet<Tile> tiles;

    public WordImpl(String text,
                    Location startLocation,
                    Location endLocation,
                    Orientation orientation,
                    SortedSet<Tile> tiles)
    {
        this.text = text;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.orientation = orientation;
        this.tiles = new TreeSet<>(tiles);
    }

    @Override
    public SortedSet<Tile> getTiles()
    {
        return Collections.unmodifiableSortedSet(tiles);
    }
}

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
import java.util.SortedMap;

import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

/**
 * Generator of {@link Word words}.
 * 
 * @author Gregory P. Moyer
 */
public interface WordFactory
{
    /**
     * Create all words from the board relevant to the collection of pieces
     * presented in the given orientation.
     * 
     * @param pieces      the relevant pieces and their locations
     * @param orientation the orientation of the pieces
     * @param board       the current state of the board
     * 
     * @return all relevant words
     */
    public Set<Word> getWords(SortedMap<Location, Piece> pieces, Orientation orientation, Board board);
}

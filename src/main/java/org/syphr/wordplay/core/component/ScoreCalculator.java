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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.annotation.concurrent.ThreadSafe;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

/**
 * The calculator determines the score for a given placement of pieces on tiles.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface ScoreCalculator
{
    /**
     * Calculate the score earned by placing the given pieces. Context is provided
     * in the words that are formed by this play and the attributes of the tiles
     * involved.
     * 
     * @param pieces     the pieces involved in the play to be scored
     * @param words      the words formed by the placement of pieces
     * @param attributes the attributes of the tiles involved in the play
     * 
     * @return the score
     */
    public int getScore(SortedMap<Location, Piece> pieces,
                        Set<Word> words,
                        Map<Location, List<TileAttribute>> attributes);
}

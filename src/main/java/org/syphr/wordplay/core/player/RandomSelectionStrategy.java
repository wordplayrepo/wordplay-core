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
package org.syphr.wordplay.core.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.syphr.wordplay.core.component.ValuedPlacement;

public class RandomSelectionStrategy implements RobotStrategy
{
    private final RandomGenerator rng = new Random();

    private final List<ValuedPlacement> placements = new ArrayList<ValuedPlacement>();

    @Override
    public Collection<ValuedPlacement> getDataStructure()
    {
        return placements;
    }

    @Override
    public ValuedPlacement selectPlacement()
    {
        if (placements.isEmpty()) {
            return null;
        }

        return placements.get(rng.nextInt(placements.size()));
    }

    @Override
    public String toString()
    {
        return "Random Selection";
    }
}

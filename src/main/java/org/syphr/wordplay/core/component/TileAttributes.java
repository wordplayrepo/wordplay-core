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

import java.util.HashMap;
import java.util.Map;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Distance;

public final class TileAttributes
{
    private static final Map<Integer, TileAttribute> LETTER_MULTIPLIERS = new HashMap<>();

    private static final Map<Integer, TileAttribute> WORD_MULTIPLIERS = new HashMap<>();

    public static TileAttribute letterMultiplier(int multiplier)
    {
        TileAttribute attribute = LETTER_MULTIPLIERS.get(multiplier);
        if (attribute == null) {
            attribute = new MultiplierAttribute(Distance.zero(), multiplier, true, true);
            LETTER_MULTIPLIERS.put(multiplier, attribute);
        }

        return attribute;
    }

    public static TileAttribute wordMultiplier(int multiplier)
    {
        TileAttribute attribute = WORD_MULTIPLIERS.get(multiplier);
        if (attribute == null) {
            attribute = new MultiplierAttribute(Distance.max(), multiplier, true, true);
            WORD_MULTIPLIERS.put(multiplier, attribute);
        }

        return attribute;
    }

    private TileAttributes()
    {
        /*
         * Factory pattern
         */
    }
}

/*
 * Copyright © 2012-2023 Gregory P. Moyer
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class LocationTest implements WithAssertions
{
    @Test
    public void at_XY()
    {
        assertThat(Location.at(1, 2)).isEqualTo(new Location(1, 2, 0));
    }

    @Test
    public void at_XYZ()
    {
        assertThat(Location.at(1, 2, 3)).isEqualTo(new Location(1, 2, 3));
    }

    @Test
    public void move_Positive()
    {
        assertThat(Location.at(1, 1, 1).move(Vector.of(1, 1, 1))).isEqualTo(Location.at(2, 2, 2));
    }

    @Test
    public void move_Negative()
    {
        assertThat(Location.at(2, 2, 2).move(Vector.of(-1, -1, -1))).isEqualTo(Location.at(1, 1, 1));
    }

    @Test
    public void isWithin()
    {
        assertAll(() -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(3, 3, 3))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(2, 1, 1), Location.at(4, 3, 3))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 2, 1), Location.at(3, 4, 3))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 2), Location.at(3, 3, 4))),
                  () -> assertTrue(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(1, 1, 1))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(4, 4, 4))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(4, 3, 3))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(3, 4, 3))),
                  () -> assertFalse(Location.at(2, 2, 2).isWithin(Distance.of(1, 1, 1), Location.at(3, 3, 4))));
    }

    @Test
    public void compareTo()
    {
        assertAll(() -> assertThat(Location.at(1, 1, 1)).isEqualByComparingTo(Location.at(1, 1, 1)),
                  () -> assertThat(Location.at(1, 1, 1)).isLessThan(Location.at(2, 1, 1)),
                  () -> assertThat(Location.at(1, 1, 1)).isLessThan(Location.at(1, 2, 1)),
                  () -> assertThat(Location.at(1, 1, 1)).isLessThan(Location.at(1, 1, 2)),
                  () -> assertThat(Location.at(2, 2, 2)).isLessThan(Location.at(3, 1, 1)));
    }
}

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DistanceTest implements WithAssertions
{
    @Test
    public void zero()
    {
        assertThat(Distance.zero()).isEqualTo(Distance.of(0, 0, 0));
    }

    @Test
    public void max()
    {
        assertThat(Distance.max()).isEqualTo(Distance.of(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void between()
    {
        assertAll(() -> assertThat(Distance.between(Location.at(1, 1, 1),
                                                    Location.at(2, 2, 2))).isEqualTo(Distance.of(1, 1, 1)),
                  () -> assertThat(Distance.between(Location.at(2, 2, 2),
                                                    Location.at(1, 1, 1))).isEqualTo(Distance.of(1, 1, 1)),
                  () -> assertThat(Distance.between(Location.at(0, 0, 0),
                                                    Location.at(1, 0, 0))).isEqualTo(Distance.of(1, 0, 0)),
                  () -> assertThat(Distance.between(Location.at(0, 0, 0),
                                                    Location.at(0, 1, 0))).isEqualTo(Distance.of(0, 1, 0)),
                  () -> assertThat(Distance.between(Location.at(0, 0, 0),
                                                    Location.at(0, 0, 1))).isEqualTo(Distance.of(0, 0, 1)));
    }

    @Test
    public void of_XY()
    {
        assertThat(Distance.of(1, 2)).isEqualTo(new Distance(1, 2, 0));
    }

    @Test
    public void of_XYZ()
    {
        assertThat(Distance.of(1, 2, 3)).isEqualTo(new Distance(1, 2, 3));
    }

    @Test
    public void construct_NegativeValues()
    {
        assertAll(() -> assertThat(Distance.of(-1, 0, 0)).isEqualTo(Distance.of(1, 0, 0)),
                  () -> assertThat(Distance.of(0, -1, 0)).isEqualTo(Distance.of(0, 1, 0)),
                  () -> assertThat(Distance.of(0, 0, -1)).isEqualTo(Distance.of(0, 0, 1)));
    }

    @ParameterizedTest
    @CsvSource({ "0,0,0", "1,0,0", "0,1,0", "0,0,1", "0,1,1", "1,0,1", "1,1,0", "1,1,1" })
    public void isWithin_True(int x, int y, int z)
    {
        assertTrue(Distance.of(x, y, z).isWithin(Distance.of(1, 1, 1)));
    }

    @ParameterizedTest
    @CsvSource({ "2,0,0", "0,2,0", "0,0,2" })
    public void contains_False(int x, int y, int z)
    {
        assertFalse(Distance.of(x, y, z).isWithin(Distance.of(1, 1, 1)));
    }

    @Test
    public void compareTo()
    {
        assertAll(() -> assertThat(Distance.of(1, 1, 1)).isEqualByComparingTo(Distance.of(1, 1, 1)),
                  () -> assertThat(Distance.of(1, 1, 1)).isLessThan(Distance.of(2, 1, 1)),
                  () -> assertThat(Distance.of(1, 1, 1)).isLessThan(Distance.of(1, 2, 1)),
                  () -> assertThat(Distance.of(1, 1, 1)).isLessThan(Distance.of(1, 1, 2)),
                  () -> assertThat(Distance.of(2, 2, 2)).isLessThan(Distance.of(3, 1, 1)));
    }
}

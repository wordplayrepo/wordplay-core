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

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VectorTest implements WithAssertions
{
    @Test
    void of_XY()
    {
        // given
        var x = 1;
        var y = 2;

        // when
        var result = Vector.of(x, y);

        // then
        assertThat(result).isEqualTo(new Vector(x, y, 0));
    }

    @Test
    void of_XYZ()
    {
        // given
        var x = 1;
        var y = 2;
        var z = 3;

        // when
        var result = Vector.of(x, y, z);

        // then
        assertThat(result).isEqualTo(new Vector(x, y, z));
    }

    @ParameterizedTest
    @CsvSource({ "1,1,1,2,1,1",
                 "1,1,1,1,2,1",
                 "1,1,1,1,1,2",
                 "2,1,1,1,1,1",
                 "1,2,1,1,1,1",
                 "1,1,2,1,1,1",
                 "-1,-1,-1,1,1,1" })
    void from(int startX, int startY, int startZ, int endX, int endY, int endZ)
    {
        // when
        var result = Vector.from(Location.at(startX, startY, startZ), Location.at(endX, endY, endZ));

        // then
        assertThat(result).isEqualTo(new Vector(endX - startX, endY - startY, endZ - startZ));
    }

    @Test
    public void compareTo()
    {
        assertAll(() -> assertThat(Vector.of(1, 1, 1)).isEqualByComparingTo(Vector.of(1, 1, 1)),
                  () -> assertThat(Vector.of(1, 1, 1)).isLessThan(Vector.of(2, 1, 1)),
                  () -> assertThat(Vector.of(1, 1, 1)).isLessThan(Vector.of(1, 2, 1)),
                  () -> assertThat(Vector.of(1, 1, 1)).isLessThan(Vector.of(1, 1, 2)),
                  () -> assertThat(Vector.of(2, 2, 2)).isLessThan(Vector.of(3, 1, 1)));
    }
}

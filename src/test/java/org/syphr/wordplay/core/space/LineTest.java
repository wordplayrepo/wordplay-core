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

public class LineTest implements WithAssertions
{
    @Test
    public void between()
    {
        assertThat(Line.between(Location.at(1, 1, 1), Location.at(2, 2, 2))).isEqualTo(new Line(Location.at(1, 1, 1),
                                                                                                Location.at(2, 2, 2)));
    }

    @Test
    public void start()
    {
        assertThat(Line.between(Location.at(1, 2, 3), Location.at(4, 5, 6)).start()).isEqualTo(Location.at(1, 2, 3));
    }

    @Test
    public void end()
    {
        assertThat(Line.between(Location.at(1, 2, 3), Location.at(4, 5, 6)).end()).isEqualTo(Location.at(4, 5, 6));
    }

    @Test
    public void contains_XAxis()
    {
        Line line = Line.between(Location.at(1, 1, 1), Location.at(4, 1, 1));

        assertAll(() -> assertFalse(line.contains(Location.at(0, 1, 1))),
                  () -> assertFalse(line.contains(Location.at(1, 0, 1))),
                  () -> assertFalse(line.contains(Location.at(1, 1, 0))),
                  () -> assertTrue(line.contains(Location.at(1, 1, 1))),
                  () -> assertTrue(line.contains(Location.at(2, 1, 1))),
                  () -> assertTrue(line.contains(Location.at(3, 1, 1))),
                  () -> assertTrue(line.contains(Location.at(4, 1, 1))),
                  () -> assertFalse(line.contains(Location.at(5, 1, 1))),
                  () -> assertFalse(line.contains(Location.at(2, 2, 1))),
                  () -> assertFalse(line.contains(Location.at(2, 0, 1))));
    }

    @Test
    public void contains_YAxis()
    {
        Line line = Line.between(Location.at(1, 1, 1), Location.at(1, 4, 1));

        assertAll(() -> assertFalse(line.contains(Location.at(0, 1, 1))),
                  () -> assertFalse(line.contains(Location.at(1, 0, 1))),
                  () -> assertFalse(line.contains(Location.at(1, 1, 0))),
                  () -> assertTrue(line.contains(Location.at(1, 1, 1))),
                  () -> assertTrue(line.contains(Location.at(1, 2, 1))),
                  () -> assertTrue(line.contains(Location.at(1, 3, 1))),
                  () -> assertTrue(line.contains(Location.at(1, 4, 1))),
                  () -> assertFalse(line.contains(Location.at(1, 5, 1))),
                  () -> assertFalse(line.contains(Location.at(2, 2, 1))),
                  () -> assertFalse(line.contains(Location.at(0, 2, 1))));
    }

    @Test
    public void contains_ZAxis()
    {
        Line line = Line.between(Location.at(1, 1, 1), Location.at(1, 1, 4));

        assertAll(() -> assertFalse(line.contains(Location.at(0, 1, 1))),
                  () -> assertFalse(line.contains(Location.at(1, 0, 1))),
                  () -> assertFalse(line.contains(Location.at(1, 1, 0))),
                  () -> assertTrue(line.contains(Location.at(1, 1, 1))),
                  () -> assertTrue(line.contains(Location.at(1, 1, 2))),
                  () -> assertTrue(line.contains(Location.at(1, 1, 3))),
                  () -> assertTrue(line.contains(Location.at(1, 1, 4))),
                  () -> assertFalse(line.contains(Location.at(1, 1, 5))),
                  () -> assertFalse(line.contains(Location.at(1, 2, 2))),
                  () -> assertFalse(line.contains(Location.at(1, 2, 0))));
    }

    @Test
    public void contains_Diagonal()
    {
        Line line = Line.between(Location.at(1, 1, 1), Location.at(4, 4, 4));

        assertAll(() -> assertFalse(line.contains(Location.at(0, 0, 0))),
                  () -> assertTrue(line.contains(Location.at(1, 1, 1))),
                  () -> assertTrue(line.contains(Location.at(2, 2, 2))),
                  () -> assertTrue(line.contains(Location.at(3, 3, 3))),
                  () -> assertTrue(line.contains(Location.at(4, 4, 4))),
                  () -> assertFalse(line.contains(Location.at(5, 5, 5))),
                  () -> assertFalse(line.contains(Location.at(2, 1, 2))),
                  () -> assertFalse(line.contains(Location.at(2, 3, 2))));
    }
}

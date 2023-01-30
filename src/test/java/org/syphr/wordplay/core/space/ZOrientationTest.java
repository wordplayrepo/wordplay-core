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

class ZOrientationTest implements WithAssertions
{
    @Test
    void move()
    {
        assertAll(() -> assertThat(ZOrientation.instance().move(Location.at(1, 1, 1), -1)).isEqualTo(Location.at(1,
                                                                                                                 1,
                                                                                                                 0)),
                  () -> assertThat(ZOrientation.instance().move(Location.at(1, 1, 1), 1)).isEqualTo(Location.at(1,
                                                                                                                1,
                                                                                                                2)));
    }

    @Test
    void contains()
    {
        assertAll(() -> assertFalse(ZOrientation.instance().contains(Distance.of(1, 0, 0))),
                  () -> assertFalse(ZOrientation.instance().contains(Distance.of(0, 1, 0))),
                  () -> assertTrue(ZOrientation.instance().contains(Distance.of(0, 0, 1))));
    }
}

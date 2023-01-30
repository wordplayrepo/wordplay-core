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

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class OrientationsTest implements WithAssertions
{
    @Test
    void x()
    {
        assertThat(Orientations.x()).isEqualTo(XOrientation.instance());
    }

    @Test
    void y()
    {
        assertThat(Orientations.y()).isEqualTo(YOrientation.instance());
    }

    @Test
    void z()
    {
        assertThat(Orientations.z()).isEqualTo(ZOrientation.instance());
    }

    @Test
    void xy()
    {
        assertThat(Orientations.xy()).containsExactly(XOrientation.instance(), YOrientation.instance());
    }

    @Test
    void xyz()
    {
        assertThat(Orientations.xyz()).containsExactly(XOrientation.instance(),
                                                       YOrientation.instance(),
                                                       ZOrientation.instance());
    }
}

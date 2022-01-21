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
package org.syphr.wordplay.core.space;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class OrientationsTest
{
    @Test
    void x()
    {
        assertThat(Orientations.x(), equalTo(XOrientation.instance()));
    }

    @Test
    void y()
    {
        assertThat(Orientations.y(), equalTo(YOrientation.instance()));
    }

    @Test
    void z()
    {
        assertThat(Orientations.z(), equalTo(ZOrientation.instance()));
    }

    @Test
    void xy()
    {
        assertThat(Orientations.xy(), contains(XOrientation.instance(), YOrientation.instance()));
    }

    @Test
    void xyz()
    {
        assertThat(Orientations.xyz(),
                   contains(XOrientation.instance(), YOrientation.instance(), ZOrientation.instance()));
    }
}

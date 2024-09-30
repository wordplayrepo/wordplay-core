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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.syphr.wordplay.core.space.Location;

class TileImplTest
{
    @Test
    void construct_NullLocation()
    {
        // when
        var result = catchThrowable(() -> new TileImpl(null));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void construct_NullLocationNonNullPiece()
    {
        // when
        var result = catchThrowable(() -> new TileImpl(null, mock(Piece.class)));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void construct_NonNullLocationNullPiece()
    {
        // given
        var location = Location.at(1, 2, 3);

        // when
        var result = catchThrowable(() -> new TileImpl(location, null));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void construct_NullLocationNullPiece()
    {
        // when
        var result = catchThrowable(() -> new TileImpl(null, null));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void getLocation()
    {
        // given
        var location = Location.at(1, 2, 3);

        // when
        var result = new TileImpl(location).getLocation();

        // then
        assertThat(result).isEqualTo(location);
    }
}

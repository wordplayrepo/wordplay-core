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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

class TileFactoryImplTest
{
    @Test
    void construct_NullConfig()
    {
        // when
        var result = catchThrowable(() -> new TileFactoryImpl(null));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void createTile_WithNoAttributes()
    {
        // given
        var location = Location.at(1, 2, 3);

        var config = mock(Configuration.class);
        when(config.getTileAttributes(any())).thenReturn(Set.of());

        // when
        var result = new TileFactoryImpl(config).createTile(location);

        // then
        verify(config).getTileAttributes(location);

        assertAll(() -> assertThat(result.getLocation()).isEqualTo(location),
                  () -> assertThat(result.getAttributes()).isEmpty());
    }

    @Test
    void createTile_WithOneAttribute()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileAttribute = mock(TileAttribute.class);

        var config = mock(Configuration.class);
        when(config.getTileAttributes(any())).thenReturn(Set.of(tileAttribute));

        // when
        var result = new TileFactoryImpl(config).createTile(location);

        // then
        verify(config).getTileAttributes(location);

        assertAll(() -> assertThat(result.getLocation()).isEqualTo(location),
                  () -> assertThat(result.getAttributes()).containsExactly(tileAttribute));
    }

    @Test
    void createTile_WithMultipleAttributes()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileAttributes = Set.of(mock(TileAttribute.class), mock(TileAttribute.class));

        var config = mock(Configuration.class);
        when(config.getTileAttributes(any())).thenReturn(tileAttributes);

        // when
        var result = new TileFactoryImpl(config).createTile(location);

        // then
        verify(config).getTileAttributes(location);

        assertAll(() -> assertThat(result.getLocation()).isEqualTo(location),
                  () -> assertThat(result.getAttributes()).containsExactlyInAnyOrderElementsOf(tileAttributes));
    }
}

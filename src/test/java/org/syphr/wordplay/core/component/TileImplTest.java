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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.syphr.wordplay.core.config.TileAttribute;
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
        var result = new TileImpl(location, null);

        // then
        assertThat(result).isNotNull();
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
    void setPiece()
    {
        // given
        var tile = new TileImpl(Location.at(1, 2, 3));
        var piece = mock(Piece.class);

        // when
        tile.setPiece(piece);

        // then
        assertThat(tile.getPiece()).contains(piece);
    }

    @Test
    void getPiece_Empty()
    {
        // given
        var tile = new TileImpl(Location.at(1, 2, 3));

        // when
        Optional<Piece> result = tile.getPiece();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void getPiece_NotEmpty()
    {
        // given
        var location = Location.at(1, 2, 3);
        var piece = mock(Piece.class);

        // when
        Optional<Piece> result = new TileImpl(location, piece).getPiece();

        // then
        assertThat(result).contains(piece);
    }

    @Test
    void hasPiece_False()
    {
        // given
        var tile = new TileImpl(Location.at(1, 2, 3));

        // when
        boolean result = tile.hasPiece();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void hasPiece_True()
    {
        // given
        var location = Location.at(1, 2, 3);
        var piece = mock(Piece.class);

        // when
        boolean result = new TileImpl(location, piece).hasPiece();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void getBaseValue_NoPiece()
    {
        // given
        var tile = new TileImpl(Location.at(1, 2, 3));

        // when
        int result = tile.getBaseValue();

        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void getBaseValue_WithPiece()
    {
        // given
        var location = Location.at(1, 2, 3);
        var piece = mock(Piece.class);

        when(piece.getValue()).thenReturn(1);

        // when
        int result = new TileImpl(location, piece).getBaseValue();

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void addAttribute()
    {
        // given
        var tile = new TileImpl(Location.at(1, 2, 3));
        var attribute = mock(TileAttribute.class);

        // when
        tile.addAttribute(attribute);

        // then
        assertThat(tile.getAttributes()).containsExactly(attribute);
    }

    @Test
    void addAttributes()
    {
        // given
        var tile = new TileImpl(Location.at(1, 2, 3));
        var attributes = Set.of(mock(TileAttribute.class), mock(TileAttribute.class));

        // when
        tile.addAttributes(attributes);

        // then
        assertThat(tile.getAttributes()).containsExactlyInAnyOrderElementsOf(attributes);
    }

    @Test
    void removeAttribute()
    {
        // given
        var tile = new TileImpl(Location.at(1, 2, 3));

        var attribute1 = mock(TileAttribute.class);
        var attribute2 = mock(TileAttribute.class);
        tile.addAttributes(Set.of(attribute1, attribute2));

        // when
        tile.removeAttribute(attribute1);

        // then
        assertThat(tile.getAttributes()).containsExactly(attribute2);
    }

    @Test
    void compareTo()
    {
        // given
        var tile1 = new TileImpl(Location.at(1, 1, 1));
        var tile2 = new TileImpl(Location.at(2, 2, 2));
        var tile3 = new TileImpl(Location.at(2, 2, 2), mock(Piece.class));
        tile3.addAttribute(mock(TileAttribute.class));

        // then
        assertAll(() -> assertThat(tile1).isLessThan(tile2),
                  () -> assertThat(tile2).isGreaterThan(tile1),
                  () -> assertThat(tile2).isEqualByComparingTo(tile3));
    }

    @Test
    void equals()
    {
        // given
        var tile1 = new TileImpl(Location.at(1, 1, 1));
        var tile2 = new TileImpl(Location.at(2, 2, 2));
        var tile3 = new TileImpl(Location.at(2, 2, 2), mock(Piece.class));
        tile3.addAttribute(mock(TileAttribute.class));

        // then
        assertAll(() -> assertThat(tile1).isNotEqualTo(tile2), () -> assertThat(tile2).isEqualTo(tile3));
    }
}

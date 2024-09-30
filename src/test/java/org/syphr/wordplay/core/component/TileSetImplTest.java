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
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

class TileSetImplTest
{
    @Test
    void construct_NullTileFactory()
    {
        // when
        var result = catchThrowable(() -> new TileSetImpl(null));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void clear()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class));

        tileSet.getTile(location);

        // when
        tileSet.clear();

        // then
        assertThat(tileSet).extracting("tiles")
                           .asInstanceOf(InstanceOfAssertFactories.map(Location.class, Tile.class))
                           .isEmpty();
    }

    @Test
    void getTile_NullLocation()
    {
        // given
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        // when
        var result = catchThrowable(() -> tileSet.getTile(null));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void getTile_NewTile()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class));

        // when
        Tile result = tileSet.getTile(location);

        // then
        assertAll(() -> assertThat(result).isNotNull(),
                  () -> assertThat(tileSet).extracting("tiles")
                                           .asInstanceOf(InstanceOfAssertFactories.map(Location.class, Tile.class))
                                           .containsExactlyEntriesOf(Map.of(location, result)));
    }

    @Test
    void getTile_ExistingTile()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class));

        // when
        Tile result1 = tileSet.getTile(location);
        Tile result2 = tileSet.getTile(location);

        // then
        assertAll(() -> assertThat(result1).isSameAs(result2),
                  () -> assertThat(tileSet).extracting("tiles")
                                           .asInstanceOf(InstanceOfAssertFactories.map(Location.class, Tile.class))
                                           .containsExactlyEntriesOf(Map.of(location, result1)));
    }

    @Test
    void getOccupiedTiles_None()
    {
        // given
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class));

        tileSet.getTile(Location.at(1, 2, 3));
        tileSet.getTile(Location.at(4, 5, 6));

        // when
        Set<Tile> result = tileSet.getOccupiedTiles();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void getOccupiedTiles_OneOccupied()
    {
        // given
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class));

        Tile tile1 = tileSet.getTile(Location.at(1, 2, 3));
        tileSet.getTile(Location.at(4, 5, 6));

        when(tile1.getPiece()).thenReturn(mock(Piece.class));
        when(tile1.hasPiece()).thenReturn(true);

        // when
        Set<Tile> result = tileSet.getOccupiedTiles();

        // then
        assertThat(result).containsExactly(tile1);
    }

    @Test
    void getOccupiedTiles_MultipleOccupied()
    {
        // given
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class)).thenReturn(mock(Tile.class));

        Tile tile1 = tileSet.getTile(Location.at(1, 2, 3));
        Tile tile2 = tileSet.getTile(Location.at(4, 5, 6));

        when(tile1.getPiece()).thenReturn(mock(Piece.class));
        when(tile1.hasPiece()).thenReturn(true);
        when(tile1.compareTo(any())).thenReturn(-1);

        when(tile2.getPiece()).thenReturn(mock(Piece.class));
        when(tile2.hasPiece()).thenReturn(true);
        when(tile2.compareTo(any())).thenReturn(1);

        // when
        Set<Tile> result = tileSet.getOccupiedTiles();

        // then
        assertThat(result).containsExactlyInAnyOrder(tile1, tile2);
    }

    @Test
    void getAttributes_NullLocations()
    {
        // given
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        // when
        var result = catchThrowable(() -> tileSet.getAttributes(null));

        // then
        assertThat(result).isInstanceOf(NullPointerException.class);
    }

    @Test
    void getAttributes_NoLocations()
    {
        // given
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        // when
        Map<Location, List<TileAttribute>> result = tileSet.getAttributes(Set.of());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void getAttributes_OneLocation_NoAttributes()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class));

        // when
        Map<Location, List<TileAttribute>> result = tileSet.getAttributes(Set.of(location));

        // then
        assertAll(() -> assertThat(result).hasSize(1), () -> assertThat(result.get(location)).isEmpty());
    }

    @Test
    void getAttributes_OneLocation_OneAttribute()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        var tile = mock(Tile.class);
        var tileAttribute = mock(TileAttribute.class);
        when(tileFactory.createTile(any())).thenReturn(tile);
        when(tile.getAttributes()).thenReturn(Set.of(tileAttribute));

        // when
        Map<Location, List<TileAttribute>> result = tileSet.getAttributes(Set.of(location));

        // then
        assertThat(result.get(location)).containsExactly(tileAttribute);
    }

    @Test
    void getAttributes_OneLocation_MultipleAttributes()
    {
        // given
        var location = Location.at(1, 2, 3);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        var tile = mock(Tile.class);
        var tileAttribute1 = mock(TileAttribute.class);
        var tileAttribute2 = mock(TileAttribute.class);
        when(tileFactory.createTile(any())).thenReturn(tile);
        when(tile.getAttributes()).thenReturn(Set.of(tileAttribute1, tileAttribute2));

        // when
        Map<Location, List<TileAttribute>> result = tileSet.getAttributes(Set.of(location));

        // then
        assertThat(result.get(location)).containsExactlyInAnyOrder(tileAttribute1, tileAttribute2);
    }

    @Test
    void getAttributes_MultipleLocations_NoAttributes()
    {
        // given
        var location1 = Location.at(1, 2, 3);
        var location2 = Location.at(4, 5, 6);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        when(tileFactory.createTile(any())).thenReturn(mock(Tile.class)).thenReturn(mock(Tile.class));

        // when
        Map<Location, List<TileAttribute>> result = tileSet.getAttributes(Set.of(location1, location2));

        // then
        assertAll(() -> assertThat(result).hasSize(2),
                  () -> assertThat(result.get(location1)).isEmpty(),
                  () -> assertThat(result.get(location2)).isEmpty());
    }

    @Test
    void getAttributes_MulipleLocations_OneAttribute()
    {
        // given
        var location1 = Location.at(1, 2, 3);
        var location2 = Location.at(4, 5, 6);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        var tile1 = mock(Tile.class);
        var tile2 = mock(Tile.class);
        var tileAttribute1 = mock(TileAttribute.class);
        var tileAttribute2 = mock(TileAttribute.class);
        when(tileFactory.createTile(location1)).thenReturn(tile1);
        when(tileFactory.createTile(location2)).thenReturn(tile2);
        when(tile1.getAttributes()).thenReturn(Set.of(tileAttribute1));
        when(tile2.getAttributes()).thenReturn(Set.of(tileAttribute2));

        // when
        Map<Location, List<TileAttribute>> result = tileSet.getAttributes(Set.of(location1, location2));

        // then
        assertAll(() -> assertThat(result.get(location1)).containsExactly(tileAttribute1),
                  () -> assertThat(result.get(location2)).containsExactly(tileAttribute2));
    }

    @Test
    void getAttributes_MulipleLocations_MultipleAttributes()
    {
        // given
        var location1 = Location.at(1, 2, 3);
        var location2 = Location.at(4, 5, 6);
        var tileFactory = mock(TileFactory.class);
        var tileSet = new TileSetImpl(tileFactory);

        var tile1 = mock(Tile.class);
        var tile2 = mock(Tile.class);
        var tileAttribute1 = mock(TileAttribute.class);
        var tileAttribute2 = mock(TileAttribute.class);
        var tileAttribute3 = mock(TileAttribute.class);
        var tileAttribute4 = mock(TileAttribute.class);
        when(tileFactory.createTile(location1)).thenReturn(tile1);
        when(tileFactory.createTile(location2)).thenReturn(tile2);
        when(tile1.getAttributes()).thenReturn(Set.of(tileAttribute1, tileAttribute2));
        when(tile2.getAttributes()).thenReturn(Set.of(tileAttribute3, tileAttribute4));

        // when
        Map<Location, List<TileAttribute>> result = tileSet.getAttributes(Set.of(location1, location2));

        // then
        assertAll(() -> assertThat(result.get(location1)).containsExactlyInAnyOrder(tileAttribute1, tileAttribute2),
                  () -> assertThat(result.get(location2)).containsExactlyInAnyOrder(tileAttribute3, tileAttribute4));
    }
}

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
package org.syphr.wordplay.core.component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;

public class RackImplTest
{
    @Test
    public void fill_BagHasEnoughPieces() throws NotEnoughPiecesException
    {
        Bag bag = mock(Bag.class);
        when(bag.isEmpty()).thenReturn(false);
        when(bag.getPiece()).thenReturn(mock(Piece.class));

        RackImpl rack = rack(2);
        rack.fill(bag);

        assertThat(rack.getPieces(), iterableWithSize(2));
    }

    @Test
    public void fill_BagDoesNotHaveEnoughPieces()
    {
        Bag bag = mock(Bag.class);
        when(bag.isEmpty()).thenReturn(true);

        RackImpl rack = rack(2);
        rack.fill(bag);

        assertThat(rack.getPieces(), iterableWithSize(0));
    }

    @Test
    public void exchange_AllPieces_BagHasEnough() throws NotEnoughPiecesException
    {
        Piece bagPiece1 = mock(Piece.class);
        Piece bagPiece2 = mock(Piece.class);

        Bag bag = mock(Bag.class);
        when(bag.getCount()).thenReturn(2);
        when(bag.exchange(any())).thenReturn(List.of(bagPiece1, bagPiece2));

        Piece rackPiece1 = mock(Piece.class);
        Piece rackPiece2 = mock(Piece.class);

        RackImpl rack = rack(2);
        rack.add(rackPiece1);
        rack.add(rackPiece2);

        rack.exchange(bag);

        assertThat(rack.getPieces(), containsInAnyOrder(bagPiece1, bagPiece2));
    }

    @Test
    public void exchange_AllPieces_BagDoesNotHaveEnough() throws NotEnoughPiecesException
    {
        Piece bagPiece1 = mock(Piece.class);

        Bag bag = mock(Bag.class);
        when(bag.getCount()).thenReturn(1);
        when(bag.exchange(any())).thenReturn(List.of(bagPiece1));

        Piece rackPiece1 = mock(Piece.class);
        Piece rackPiece2 = mock(Piece.class);

        RackImpl rack = rack(2);
        rack.add(rackPiece1);
        rack.add(rackPiece2);

        rack.exchange(bag);

        assertThat(rack.getPieces(), containsInAnyOrder(rackPiece2, bagPiece1));
    }

    @Test
    public void exchange_SomePieces_PieceNotFoundInRack() throws NoSuchPieceException, NotEnoughPiecesException
    {
        // given
        Piece piece = mock(Piece.class);
        Bag bag = mock(Bag.class);
        RackImpl rack = rack(2);

        // when
        Executable exec = () -> rack.exchange(List.of(piece), bag);

        // then
        assertThrows(NoSuchPieceException.class, exec);
    }

    @Test
    public void exchange_SomePieces_AllPiecesFound() throws NoSuchPieceException, NotEnoughPiecesException
    {
        // given
        Piece bagPiece1 = mock(Piece.class);

        Bag bag = mock(Bag.class);
        when(bag.exchange(any())).thenReturn(List.of(bagPiece1));

        Piece rackPiece1 = mock(Piece.class);
        Piece rackPiece2 = mock(Piece.class);

        RackImpl rack = rack(2);
        rack.add(rackPiece1);
        rack.add(rackPiece2);

        // when
        rack.exchange(List.of(rackPiece1), bag);

        // then
        assertThat(rack.getPieces(), containsInAnyOrder(rackPiece2, bagPiece1));
    }

    @Test
    public void clear()
    {
        // given
        Bag bag = mock(Bag.class);

        Piece rackPiece1 = mock(Piece.class);
        Piece rackPiece2 = mock(Piece.class);

        RackImpl rack = rack(2);
        rack.add(rackPiece1);
        rack.add(rackPiece2);

        // when
        rack.clear(bag);

        // then
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Piece>> pieces = ArgumentCaptor.forClass(List.class);
        verify(bag).returnPieces(pieces.capture());
        assertAll(() -> assertThat(pieces.getValue(), contains(rackPiece1, rackPiece2)),
                  () -> assertThat(rack.getPieces(), empty()));
    }

    private RackImpl rack(int size)
    {
        return new RackImpl(size);
    }
}

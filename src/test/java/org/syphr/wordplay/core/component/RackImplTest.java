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
package org.syphr.wordplay.core.component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
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
        rack.add(rackPiece1);;
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
        rack.add(rackPiece1);;
        rack.add(rackPiece2);

        rack.exchange(bag);

        assertThat(rack.getPieces(), containsInAnyOrder(rackPiece2, bagPiece1));
    }

    private RackImpl rack(int size)
    {
        return new RackImpl(size);
    }
}

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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.syphr.wordplay.core.lang.Letter;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

@ExtendWith(MockitoExtension.class)
public class BagImplTest
{
    @Mock
    PieceFactory pieceFactory;

    @Mock
    RandomGenerator random;

    @BeforeEach
    public void setup()
    {
        lenient().when(pieceFactory.createPiece(any())).thenAnswer(new Answer<Piece>()
        {
            @Override
            public Piece answer(InvocationOnMock invocation) throws Throwable
            {
                return piece(invocation.getArgument(0));
            }
        });
    }

    @Test
    public void isEmpty_NoLetters()
    {
        assertTrue(bag().isEmpty());
    }

    @Test
    public void isEmpty_WithLetters()
    {
        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(mock(Letter.class));

        assertFalse(bag(letters).isEmpty());
    }

    @Test
    public void getCount_NoLetters()
    {
        assertThat(bag().getCount(), is(0));
    }

    @Test
    public void getCount_SingleLetter_SingleOccurrence()
    {
        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(mock(Letter.class), 1);

        assertThat(bag(letters).getCount(), is(1));
    }

    @Test
    public void getCount_MultiLetter_SingleOccurrence()
    {
        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(mock(Letter.class), 1);
        letters.add(mock(Letter.class), 1);

        assertThat(bag(letters).getCount(), is(2));
    }

    @Test
    public void getCount_MultiLetter_MultiOccurrence()
    {
        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(mock(Letter.class), 2);
        letters.add(mock(Letter.class), 3);

        assertThat(bag(letters).getCount(), is(5));
    }

    @Test
    public void getPiece_Empty() throws NotEnoughPiecesException
    {
        // when
        Executable exec = () -> bag().getPiece();

        // then
        assertThrows(NotEnoughPiecesException.class, exec);
    }

    @Test
    public void getPiece_NotEmpty() throws NotEnoughPiecesException
    {
        Letter letter = mock(Letter.class);

        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(letter, 1);

        assertSame(letter, bag(letters).getPiece().getLetter());
        verify(random).nextInt(anyInt());
    }

    @Test
    public void getPiece_Letter_Undefined() throws NoSuchPieceException
    {
        // when
        Executable exec = () -> bag().getPiece(mock(Letter.class));

        // then
        assertThrows(NoSuchPieceException.class, exec);
    }

    @Test
    public void getPiece_Letter_Exhausted() throws NoSuchPieceException
    {
        // given
        Letter letter = mock(Letter.class);

        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(letter, 0);

        // when
        Executable exec = () -> bag(letters).getPiece(letter);

        // then
        assertThrows(NoSuchPieceException.class, exec);
    }

    @Test
    public void getPiece_Letter_Success() throws NoSuchPieceException
    {
        Letter letter = mock(Letter.class);

        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(letter, 1);

        BagImpl bag = bag(letters);
        Piece piece = bag.getPiece(letter);

        assertSame(letter, piece.getLetter());
        assertThat(bag.getCount(), is(0));
    }

    @Test
    public void exchange_Success() throws NotEnoughPiecesException
    {
        Letter letter1 = mock(Letter.class);
        Letter letter2 = mock(Letter.class);
        Letter letter3 = mock(Letter.class);
        Letter letter4 = mock(Letter.class);

        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(letter1, 1);
        letters.add(letter2, 1);

        List<Piece> input = List.of(piece(letter3), piece(letter4));
        List<Piece> output = bag(letters).exchange(input);

        assertThat(output.stream().map(p -> p.getLetter()).collect(Collectors.toList()),
                   containsInAnyOrder(letter1, letter2));
    }

    @Test
    public void exchange_NotEnoughPieces() throws NotEnoughPiecesException
    {
        // given
        Letter letter1 = mock(Letter.class);
        Letter letter2 = mock(Letter.class);
        Letter letter3 = mock(Letter.class);

        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(letter1, 1);

        List<Piece> input = List.of(piece(letter2), piece(letter3));

        // when
        Executable exec = () -> bag(letters).exchange(input);

        // then
        assertThrows(NotEnoughPiecesException.class, exec);
    }

    @Test
    public void returnPiece()
    {
        BagImpl bag = bag();
        bag.returnPiece(piece(mock(Letter.class)));

        assertFalse(bag.isEmpty());
    }

    @Test
    public void returnPieces()
    {
        BagImpl bag = bag();
        bag.returnPieces(List.of(piece(mock(Letter.class)), piece(mock(Letter.class))));

        assertThat(bag.getCount(), is(2));
    }

    private BagImpl bag()
    {
        return bag(HashMultiset.create());
    }

    private BagImpl bag(Multiset<Letter> letters)
    {
        return new BagImpl(letters, pieceFactory, random);
    }

    private Piece piece(Letter letter)
    {
        Piece piece = mock(Piece.class);
        lenient().when(piece.getLetter()).thenReturn(letter);

        return piece;
    }
}

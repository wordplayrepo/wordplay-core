package org.syphr.wordplay.core.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.syphr.wordplay.core.Letter;
import org.syphr.wordplay.core.NoSuchPieceException;
import org.syphr.wordplay.core.NotEnoughPiecesException;
import org.syphr.wordplay.core.Piece;
import org.syphr.wordplay.core.PieceFactory;
import org.syphr.wordplay.core.impl.BagImpl;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

@RunWith(MockitoJUnitRunner.class)
public class BagImplTest
{
    @Mock
    PieceFactory pieceFactory;

    @Mock
    Random random;

    @Before
    public void setup()
    {
        when(pieceFactory.createPiece(any())).thenAnswer(new Answer<Piece>()
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

    @Test(expected = NotEnoughPiecesException.class)
    public void getPiece_Empty() throws NotEnoughPiecesException
    {
        bag().getPiece();
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

    @Test(expected = NoSuchPieceException.class)
    public void getPiece_Letter_Undefined() throws NoSuchPieceException
    {
        bag().getPiece(mock(Letter.class));
    }

    @Test(expected = NoSuchPieceException.class)
    public void getPiece_Letter_Exhausted() throws NoSuchPieceException
    {
        Letter letter = mock(Letter.class);

        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(letter, 0);

        bag(letters).getPiece(letter);
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

    @Test(expected = NotEnoughPiecesException.class)
    public void exchange_NotEnoughPieces() throws NotEnoughPiecesException
    {
        Letter letter1 = mock(Letter.class);
        Letter letter2 = mock(Letter.class);
        Letter letter3 = mock(Letter.class);

        HashMultiset<Letter> letters = HashMultiset.create();
        letters.add(letter1, 1);

        List<Piece> input = List.of(piece(letter2), piece(letter3));
        bag(letters).exchange(input);
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
        when(piece.getLetter()).thenReturn(letter);

        return piece;
    }
}

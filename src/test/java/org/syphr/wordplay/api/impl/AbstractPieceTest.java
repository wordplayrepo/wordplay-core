package org.syphr.wordplay.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.syphr.wordplay.api.Letter;
import org.syphr.wordplay.api.Piece;

@RunWith(MockitoJUnitRunner.class)
public class AbstractPieceTest
{
    @Test
    public void copyTo_NotWild()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece = piece();
        piece.setLetter(letter);
        piece.setWild(false);

        AbstractPiece copy = piece();
        piece.copyTo(copy);

        assertEquals(piece.getLetter(), copy.getLetter());
        assertFalse(copy.isWild());
    }

    @Test
    public void copyTo_Wild()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece = piece();
        piece.setLetter(letter);
        piece.setWild(true);

        AbstractPiece copy = piece();
        piece.copyTo(copy);

        assertEquals(piece.getLetter(), copy.getLetter());
        assertTrue(copy.isWild());
    }

    @Test
    public void hashCode_Same_NotWild()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece1 = piece();
        piece1.setLetter(letter);
        piece1.setWild(false);

        AbstractPiece piece2 = piece();
        piece2.setLetter(letter);
        piece2.setWild(false);

        assertEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_Different_NotWild()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(mock(Letter.class));
        piece1.setWild(false);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(false);

        assertNotEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_Same_Wild()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(mock(Letter.class));
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(true);

        assertEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void equals_Same_NotWild()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece1 = piece();
        piece1.setLetter(letter);
        piece1.setWild(false);

        AbstractPiece piece2 = piece();
        piece2.setLetter(letter);
        piece2.setWild(false);

        assertTrue(piece1.equals(piece2));
    }

    @Test
    public void equals_Different_NotWild()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(mock(Letter.class));
        piece1.setWild(false);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(false);

        assertFalse(piece1.equals(piece2));
    }

    @Test
    public void equals_Same_Wild()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(mock(Letter.class));
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(true);

        assertTrue(piece1.equals(piece2));
    }

    private AbstractPiece piece()
    {
        return new AbstractPiece()
        {
            @Override
            public int getValue()
            {
                return 0;
            }

            @Override
            public Piece copy()
            {
                return null;
            }
        };
    }
}

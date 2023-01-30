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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.syphr.wordplay.core.lang.Letter;

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

        assertAll(() -> assertEquals(piece.getLetter(), copy.getLetter()), () -> assertFalse(copy.isWild()));
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

        assertAll(() -> assertEquals(piece.getLetter(), copy.getLetter()), () -> assertTrue(copy.isWild()));
    }

    @Test
    public void hashCode_NotWild_SameLetter()
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
    public void hashCode_NotWild_NoLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(false);

        AbstractPiece piece2 = piece();
        piece2.setLetter(null);
        piece2.setWild(false);

        assertEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_NotWild_DifferentLetters()
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
    public void hashCode_Wild_DifferentLetters()
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
    public void hashCode_Wild_NoLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(null);
        piece2.setWild(true);

        assertEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_Wild_MissingAndPresentLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(true);

        assertEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_Wild_SameLetter()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece1 = piece();
        piece1.setLetter(letter);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(letter);
        piece2.setWild(true);

        assertEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_DifferentWilds_SameLetters()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece1 = piece();
        piece1.setLetter(letter);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(letter);
        piece2.setWild(false);

        assertNotEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_DifferentWilds_DifferentLetters()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(mock(Letter.class));
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(false);

        assertNotEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_DifferentWilds_NoLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(null);
        piece2.setWild(false);

        assertNotEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void hashCode_DifferentWilds_MissingAndPresentLetters()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(false);

        assertNotEquals(piece1.hashCode(), piece2.hashCode());
    }

    @Test
    public void equals_NotWild_SameLetter()
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
    public void equals_NotWild_NoLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(false);

        AbstractPiece piece2 = piece();
        piece2.setLetter(null);
        piece2.setWild(false);

        assertTrue(piece1.equals(piece2));
    }

    @Test
    public void equals_NotWild_MissingAndPresentLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(false);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(false);

        assertFalse(piece1.equals(piece2));
    }

    @Test
    public void equals_NotWild_DifferentLetters()
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
    public void equals_Wild_DifferentLetters()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(mock(Letter.class));
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(true);

        assertTrue(piece1.equals(piece2));
    }

    @Test
    public void equals_Wild_NoLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(null);
        piece2.setWild(true);

        assertTrue(piece1.equals(piece2));
    }

    @Test
    public void equals_Wild_MissingAndPresentLetters()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(true);

        assertTrue(piece1.equals(piece2));
    }

    @Test
    public void equals_Wild_SameLetters()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece1 = piece();
        piece1.setLetter(letter);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(letter);
        piece2.setWild(true);

        assertTrue(piece1.equals(piece2));
    }

    @Test
    public void equals_DifferentWilds_DifferentLetters()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(mock(Letter.class));
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(false);

        assertFalse(piece1.equals(piece2));
    }

    @Test
    public void equals_DifferentWilds_NoLetter()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(null);
        piece2.setWild(false);

        assertFalse(piece1.equals(piece2));
    }

    @Test
    public void equals_DifferentWilds_MissingAndPresentLetters()
    {
        AbstractPiece piece1 = piece();
        piece1.setLetter(null);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(mock(Letter.class));
        piece2.setWild(false);

        assertFalse(piece1.equals(piece2));
    }

    @Test
    public void equals_DifferentWilds_SameLetters()
    {
        Letter letter = mock(Letter.class);

        AbstractPiece piece1 = piece();
        piece1.setLetter(letter);
        piece1.setWild(true);

        AbstractPiece piece2 = piece();
        piece2.setLetter(letter);
        piece2.setWild(false);

        assertFalse(piece1.equals(piece2));
    }

    @Test
    public void equals_SameObject()
    {
        // given
        AbstractPiece piece = piece();

        // then
        assertTrue(piece.equals(piece));
    }

    @Test
    public void equals_NullObject()
    {
        // given
        AbstractPiece piece = piece();

        // then
        assertFalse(piece.equals(null));
    }

    @Test
    public void equals_WrongType()
    {
        // given
        AbstractPiece piece1 = new AbstractPiece()
        {

            @Override
            public int getValue()
            {
                return 1;
            }

            @Override
            public Piece copy()
            {
                return null;
            }
        };

        AbstractPiece piece2 = new AbstractPiece()
        {

            @Override
            public int getValue()
            {
                return 2;
            }

            @Override
            public Piece copy()
            {
                return null;
            }
        };

        // then
        assertFalse(piece1.equals(piece2));
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

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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

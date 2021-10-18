package org.syphr.wordplay.core.impl;

import org.syphr.wordplay.core.Letter;
import org.syphr.wordplay.core.Piece;

import lombok.Data;

@Data
public abstract class AbstractPiece implements Piece
{
    private Letter letter;
    private boolean wild;

    protected AbstractPiece copyTo(AbstractPiece piece)
    {
        piece.setLetter(getLetter());
        piece.setWild(isWild());

        return piece;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (wild ? 1231 : 1237);

        /*
         * If the piece is wild, the letter should have no impact.
         */
        if (!wild) {
            result = prime * result + ((letter == null) ? 0 : letter.hashCode());
        }

        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        AbstractPiece other = (AbstractPiece) obj;
        if (wild != other.wild) {
            return false;
        }

        /*
         * If both pieces are wild, they are the same. The letter should not be compared
         * in this case.
         */
        if (wild) {
            return true;
        }

        if (letter == null) {
            if (other.letter != null) {
                return false;
            }
        } else if (!letter.equals(other.letter)) {
            return false;
        }

        return true;
    }
}

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

import org.syphr.wordplay.core.lang.Letter;

import lombok.Data;

@Data
public abstract class AbstractPiece implements Piece
{
    private Letter letter;
    private boolean wild;

    protected <T extends AbstractPiece> T copyTo(T piece)
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
            result = prime * result + (letter == null ? 0 : letter.hashCode());
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

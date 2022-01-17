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

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.lang.Letter;

/**
 * A piece represents a game token that is represented by a {@link Letter} and
 * has attributes such as a value and a wildcard status.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Piece
{
    /**
     * Set the letter that represents this piece.
     * 
     * @param letter the letter
     */
    public void setLetter(Letter letter);

    /**
     * Retrieve the letter that represents this piece.
     * 
     * @return the letter
     */
    public Letter getLetter();

    /**
     * Retrieve the base value of this piece when used in a placement.
     * 
     * @return the base value
     */
    public int getValue();

    /**
     * Determine whether or not this piece represents a wildcard (no specific letter
     * until one is chosen).
     * 
     * @return <code>true</code> if this piece is wild; <code>false</code> otherwise
     */
    public boolean isWild();

    /**
     * Create a copy of this piece.
     * 
     * @return a new piece identical to the original
     */
    public Piece copy();

    /**
     * Two pieces are equal if they are both wild or they both represent the same
     * letter.
     * 
     * @param obj the object to compare against this piece
     * 
     * @return <code>true</code> if the given object is equal to this piece;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}

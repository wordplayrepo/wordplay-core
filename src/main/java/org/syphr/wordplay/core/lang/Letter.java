package org.syphr.wordplay.core.lang;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A letter represents a single character that, when put together with other
 * letters, creates a word that can be used in a placement.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Letter
{
    /**
     * Retrieve the character that represents this letter.
     * 
     * @return the character
     */
    public char getCharacter();

    @Override
    public String toString();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);
}

package org.syphr.wordplay.core.lang;

import java.util.Set;

/**
 * A dictionary represents the set of valid words that can be created from
 * individual letters represented by pieces.
 * 
 * @author Gregory P. Moyer
 */
public interface Dictionary
{
    /**
     * Determine whether or not the given word is valid.
     * 
     * @param word
     *            the word to validate
     * @return <code>true</code> if the word is valid and therefore playable;
     *         <code>false</code> otherwise
     */
    public boolean isValid(String word);

    /**
     * Retrieve the complete set of words contained within this dictionary.
     * 
     * @return the words in this dictionary
     */
    public Set<String> getWords();
}

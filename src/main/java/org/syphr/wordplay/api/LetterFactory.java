package org.syphr.wordplay.api;

import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A letter factory generates letters from characters or words. It also holds an
 * authoritative set of all possible letters it can generate.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface LetterFactory
{
    /**
     * Convert a character into a letter.
     * 
     * @param character
     *            the character to convert
     * @return the letter represented by the given character
     */
    public Letter toLetter(char character);

    /**
     * Convert a single-character string into a letter.
     * 
     * @param character
     *            the single-character string to convert
     * @return the letter represented by the given character or
     *         <code>null</code> if the given string is <code>null</code> or
     *         empty
     */
    public Letter toLetter(String character);

    /**
     * Convert a group of characters (a word) to a list of letters.
     * 
     * @param word
     *            the word to convert
     * @return the list of letters represented by the given word
     */
    public List<Letter> toLetters(String word);

    /**
     * Retrieve the authoritative set of possible letters that can be created by
     * this factory.
     * 
     * @return the set of possible letters
     */
    public Set<Letter> getLetters();
}

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
package org.syphr.wordplay.core.lang;

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

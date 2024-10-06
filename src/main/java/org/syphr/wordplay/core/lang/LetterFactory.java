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
package org.syphr.wordplay.core.lang;

import java.util.List;
import java.util.SequencedSet;

import javax.annotation.concurrent.ThreadSafe;

/**
 * A letter factory generates letters from characters or words. It also holds an
 * authoritative set of all possible letters it can generate.
 *
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface LetterFactory
{
    /**
     * Convert a character into a letter.
     *
     * @param character the character to convert
     *
     * @return the letter represented by the given character
     *
     * @throws InvalidCharacterException if the given character cannot be converted
     *                                   to a letter
     */
    public Letter toLetter(char character) throws InvalidCharacterException;

    /**
     * Convert a single-character string into a letter.
     *
     * @param character the single-character string to convert
     *
     * @return the letter represented by the given character
     *
     * @throws InvalidCharacterException if the given character cannot be converted
     *                                   to a letter
     * @throws IllegalArgumentException  if the given string is <code>null</code>,
     *                                   empty, or contains more than one character
     */
    public Letter toLetter(String character) throws InvalidCharacterException, IllegalArgumentException;

    /**
     * Convert a group of characters (a word) to a list of letters.
     *
     * @param word the word to convert
     *
     * @return the list of letters represented by the given word
     *
     * @throws InvalidCharacterException if any characters in the given string
     *                                   cannot be converted to a letter
     */
    public List<Letter> toLetters(String word) throws InvalidCharacterException;

    /**
     * Retrieve the authoritative set of possible letters that can be created by
     * this factory.
     *
     * @return the set of possible letters
     */
    public SequencedSet<Letter> getLetters();
}

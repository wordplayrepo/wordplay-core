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

import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

/**
 * A dictionary represents the set of valid words that can be created from
 * individual letters represented by pieces.
 *
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface Dictionary
{
    /**
     * Determine whether or not the given word is valid.
     *
     * @param word the word to validate
     *
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

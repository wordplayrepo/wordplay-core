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

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

public class InvalidWordException extends PlacementException
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public InvalidWordException()
    {
        super();
    }

    public InvalidWordException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidWordException(String message)
    {
        super(message);
    }

    public InvalidWordException(Throwable cause)
    {
        super(cause);
    }
}

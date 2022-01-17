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
package org.syphr.wordplay.core.space;

public class Distances
{
    private static final Distance ZERO_DISTANCE = Distance.of(0, 0, 0);

    private static final Distance MAX_DISTANCE = Distance.of(Integer.MAX_VALUE,
                                                             Integer.MAX_VALUE,
                                                             Integer.MAX_VALUE);

    public static Distance zero()
    {
        return ZERO_DISTANCE;
    }

    public static Distance max()
    {
        return MAX_DISTANCE;
    }

    private Distances()
    {
        /*
         * Factory pattern
         */
    }
}

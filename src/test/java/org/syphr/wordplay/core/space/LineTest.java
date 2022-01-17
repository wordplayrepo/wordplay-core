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

import org.junit.Assert;
import org.junit.Test;

public class LineTest
{
    @Test
    public void testHorizontalContains()
    {
        Line line = Line.from(Location.at(1, 1), Location.at(4, 1));

        Assert.assertTrue(line.contains(Location.at(2, 1)));
        Assert.assertFalse(line.contains(Location.at(0, 1)));
        Assert.assertFalse(line.contains(Location.at(5, 1)));
        Assert.assertFalse(line.contains(Location.at(2, 2)));
        Assert.assertFalse(line.contains(Location.at(2, 0)));
    }

    @Test
    public void testVerticalContains()
    {
        Line line = Line.from(Location.at(1, 1), Location.at(1, 4));

        Assert.assertTrue(line.contains(Location.at(1, 2)));
        Assert.assertFalse(line.contains(Location.at(1, 0)));
        Assert.assertFalse(line.contains(Location.at(1, 5)));
        Assert.assertFalse(line.contains(Location.at(2, 2)));
        Assert.assertFalse(line.contains(Location.at(0, 2)));
    }

    @Test
    public void testDiagonalContains()
    {
        Line line = Line.from(Location.at(1, 1), Location.at(4, 4));

        Assert.assertTrue(line.contains(Location.at(2, 2)));
        Assert.assertFalse(line.contains(Location.at(0, 0)));
        Assert.assertFalse(line.contains(Location.at(5, 5)));
        Assert.assertFalse(line.contains(Location.at(2, 1)));
        Assert.assertFalse(line.contains(Location.at(2, 3)));
    }
}
